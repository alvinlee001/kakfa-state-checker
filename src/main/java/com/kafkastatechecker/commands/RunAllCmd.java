package com.kafkastatechecker.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@Slf4j
public class RunAllCmd {

    @Autowired
    private BrokerCmd brokerCmd;
    @Autowired
    private RegistryCmd registryCmd;
    @Autowired
    private ConnectCmd connectCmd;

    @ShellMethod("connect() and run ")
    public void run(
            @ShellOption(value = "--kafka",defaultValue = "localhost:9092") String kafka,
            @ShellOption(value = "--kafka-username",defaultValue = "admin") String kafkaUser,
            @ShellOption(value = "--kafka-password",defaultValue = "admin-secret") String kafkaPassword,
            @ShellOption(value = "--schema-registry",defaultValue = "") String schemaRegistry,
            @ShellOption(value = "--schema-registry-username",defaultValue = "") String schemaRegistryUsername,
            @ShellOption(value = "--schema-registry-password",defaultValue = "") String schemaRegistryPassword,
            @ShellOption(value = "--kafka-connect",defaultValue = "") String kafkaConnect,
            @ShellOption(value = "--kafka-connect-username",defaultValue = "") String kafkaConnectUsername,
            @ShellOption(value = "--kafka-connect-password",defaultValue = "") String kafkaConnectPassword
    ) {
        brokerCmd.broker(kafka, kafkaUser, kafkaPassword);
        registryCmd.registry(schemaRegistry, schemaRegistryUsername, schemaRegistryPassword);
        connectCmd.connect(kafkaConnect, kafkaConnectUsername, kafkaConnectPassword);
    }
}
