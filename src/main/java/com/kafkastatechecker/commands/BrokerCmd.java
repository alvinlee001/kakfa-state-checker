package com.kafkastatechecker.commands;

import com.kafkastatechecker.config.GlobalRuntimeConfig;
import com.kafkastatechecker.helper.KafkaTopicHelper;
import com.kafkastatechecker.service.KafkaBrokerStateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.TopicListing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Set;

import static org.springframework.shell.standard.ShellOption.NULL;


@ShellComponent
@Slf4j
public class BrokerCmd extends AbstractCmd {

    @Autowired
    private KafkaBrokerStateService kafkaBrokerStateService;

    @Autowired
    private GlobalRuntimeConfig globalRuntimeConfig;

    @Autowired
    private ConnectCmd connectCmd;

    @ShellMethod("connect() and run ")
    public void broker(
            @ShellOption(value = "--kafka",defaultValue = NULL) String kafka,
            @ShellOption(value = "--kafka-username",defaultValue = NULL) String kafkaUser,
            @ShellOption(value = "--kafka-password",defaultValue = NULL) String kafkaPassword
    ) {
        if (notNull(kafka)) {
            globalRuntimeConfig.put(GlobalRuntimeConfig.KAFKA, kafka);
        }
        if (notNull(kafkaUser)) {
            globalRuntimeConfig.put(GlobalRuntimeConfig.KAFKA_USER, kafkaUser);
        }
        if (notNull(kafkaPassword)) {
            globalRuntimeConfig.put(GlobalRuntimeConfig.KAFKA_PASSWORD, kafkaPassword);
        }
        executeBroker();
    }


    private void executeBroker() {
        kafkaBrokerStateService.test();
    }

}
