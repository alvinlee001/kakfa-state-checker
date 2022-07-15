#!/bin/sh
mvn clean install -DskipTests && java -jar ./target/kafkaStateChecker.jar connect --kafka localhost:9092 --kafka-username admin --kafka-password admin-secret
