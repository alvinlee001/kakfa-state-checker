package com.kafkastatechecker.helper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.kafkastatechecker.config.GlobalRuntimeConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.acl.AclOperation;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE;
import static org.apache.kafka.common.security.auth.SecurityProtocol.SASL_PLAINTEXT;

@Component
@Slf4j
public class KafkaAdminClientFacade {

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    GlobalRuntimeConfig globalRuntimeConfig;

    private AdminClient kafkaAdminClient;

    public void connect () {
        if (kafkaAdminClient == null) {
            try{
                Properties config = new Properties();
                config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, globalRuntimeConfig.get(GlobalRuntimeConfig.KAFKA));
                config.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, SASL_PLAINTEXT.name);
                config.put("sasl.mechanism", "PLAIN");
                config.put("sasl.kerberos.service.name", "kafka");
                config.put("sasl.jaas.config", String.format("org.apache.kafka.common.security.plain.PlainLoginModule required serviceName=\"kafka\" username=\"%s\" password=\"%s\";", globalRuntimeConfig.get(GlobalRuntimeConfig.KAFKA_USER),  globalRuntimeConfig.get(GlobalRuntimeConfig.KAFKA_PASSWORD)));

                this.kafkaAdminClient = AdminClient.create(config);
            } catch (Exception e){
                log.error("fail to connect to kafka listener", e);
            }
        }
    }

    public ListTopicsResult listTopics () {
        connect();
        return kafkaAdminClient.listTopics();
    }

    public void test () {
        connect();
        try {
            ListTopicsResult result = listTopics();
            DescribeTopicsResult tr = kafkaAdminClient.describeTopics(result.names().get());
            Map<String, TopicDescription> topics= tr.allTopicNames().get();
            Map<ConfigResource, Config> configTopicAll = new HashMap<>();

            String topicJson = jsonWriter().writeValueAsString(topics);
            String clusterInfoJson = jsonWriter().writeValueAsString(getClusterInfo());
            String configTopicAllJson = "";

            log.info("topics: {}", topicJson);
            for(String topicName :topics.keySet()) {
                DescribeConfigsResult configTopicResult = kafkaAdminClient.describeConfigs(Arrays.asList(new ConfigResource(ConfigResource.Type.TOPIC, topicName)));
                configTopicAll = configTopicResult.all().get();
                configTopicAllJson = jsonWriter().writeValueAsString(configTopicAll);
            }
            log.info("clusterInfoJson: {}", clusterInfoJson);
            log.info("partition info: {}", topics.get("connect-configs").authorizedOperations());

            WriteToDiskHelper.renamePreviousFiles(new File("./"));

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            WriteToDiskHelper.writeToDisk(String.format("topics-%s-%s-latest.json", df.format(timestamp), timestamp.getTime()), topicJson);
            WriteToDiskHelper.writeToDisk(String.format("topics-%s-%s-config-latest.json", df.format(timestamp), timestamp.getTime()), configTopicAllJson);
            WriteToDiskHelper.writeToDisk(String.format("broker-%s-%s-latest.json", df.format(timestamp), timestamp.getTime()), clusterInfoJson);
        } catch (Exception e) {
            log.error("fail to describe topics", e);
        }
    }

    private ObjectWriter jsonWriter() {
        ObjectMapper objMapper = new ObjectMapper()
                .enable(READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE) ;
        objMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return objMapper.writer();
    }

    public Map<String, Object> getClusterInfo() {
        Map<String, Object> result = new HashMap<>();
        DescribeClusterResult cr = kafkaAdminClient.describeCluster();
        ;
        try {
            Set<AclOperation> acl = cr.authorizedOperations().get();
            Collection<Node> nodes =  cr.nodes().get();
            String clusterId = cr.clusterId().get();
            Node controller = cr.controller().get();
            result.put("acl",acl);
            result.put("nodes", nodes);
            result.put("clusterId", clusterId);
            result.put("controller", controller);
            for (Integer nodeId: nodes.stream().mapToInt(Node::id).boxed().collect(Collectors.toList())) {
                DescribeConfigsResult cnfresult = kafkaAdminClient.describeConfigs(Arrays.asList(new ConfigResource(ConfigResource.Type.BROKER, nodeId.toString())));
                Map<ConfigResource, Config> configResourceConfigMap = cnfresult.all().get();
                result.put("broker-config-"+nodeId, configResourceConfigMap.values());
            }

        } catch (Exception e) {
            log.error("getClusterInfo has an error", e);
        }
        return result;
    }
}
