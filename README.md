the objective of this project is to build a command-line tool in Java or Scala that detects differences between two runs for the 3 main kafka ecosystem components.

Kafka Broker - (uses https://kafka.apache.org/32/javadoc/org/apache/kafka/clients/admin/Admin.html)
* Broker configuration: addition, deletion, modification
* Topic: addition, deletion, configuration modification, change of partition
* Quota: addition, deletion, modification
* ACL: addition, deletion, modification


Schema Registry (WIP) - (uses https://docs.confluent.io/platform/current/schema-registry/develop/api.html)
* schema: addition, new versions
* subject: addition, new versions
* mode: modification
* config: modification

Kafka connect (WIP)- (uses https://docs.confluent.io/platform/current/connect/references/restapi.html)
* connectors: addition, deletion, modification
* connector plugins: addition, deletion

#### RUN ALL (WIP)
```
$ java your-tool.jar run
--kafka xx:yy --kafka-username xx --kafka-password xx
--schema-registry https://xx:yy --schema-registry-username xx --schema-registry-password xx
--kafka-connect https://xx:yy --kafka-connect-user xx --kafka-connect-password xx
```

#### ONLY KAFKA BROKER (WIP)
```
$ java kafkaStateChecker.jar broker --kafka xx:yy --kafka-username xx --kafka-password xx
```

#### ONLY SCHEMA REGISTRY (WIP, not available)
```
$ java kafkaStateChecker.jar registry --schema-registry https://xx:yy --schema-registry-username xx --schema-registry-password xx
```

#### ONLY KAFKA CONNECT (WIP, not available)
```
$ java kafkaStateChecker.jar connect --kafka-connect https://xx:yy --kafka-connect-username xx --kafka-connect-password xx
```

This command needs to
* save the current state in json
* compare the current state with the previous state and output in json the list of changes

Please use https://github.com/confluentinc/cp-demo/ as the target for this exercise.
```
$ git clone https://github.com/confluentinc/cp-demo/ && cd cp-demo && ./script/start.sh
```

then, in `/etc/hosts` add
```
127.0.0.1 kafka1
127.0.0.1 kafka2
```
You're good to go!

This project will be done in 4 milestones
* Milestone 1: build the broker diff (broker & topic)
* Milestone 2: build the broker diff (acl & quota)
* Milestone 3: build the schema registry diff
* Milestone 4: build the kafka connect diff
