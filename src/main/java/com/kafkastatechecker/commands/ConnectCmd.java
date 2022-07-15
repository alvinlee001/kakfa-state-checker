package com.kafkastatechecker.commands;

import com.kafkastatechecker.config.GlobalRuntimeConfig;
import com.kafkastatechecker.service.KafkaBrokerStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@Slf4j
@ShellComponent
public class ConnectCmd extends AbstractCmd {

    @Autowired
    private KafkaBrokerStateService kafkaBrokerStateService;

    @Autowired
    private GlobalRuntimeConfig globalRuntimeConfig;

    @ShellMethod("connect to kafka with params")
    public void connect(
            @ShellOption(value = "--kafka-connect",defaultValue = "") String kafkaConnect,
            @ShellOption(value = "--kafka-connect-username",defaultValue = "") String kafkaConnectUsername,
            @ShellOption(value = "--kafka-connect-password",defaultValue = "") String kafkaConnectPassword
    ) {
        if (notNull(kafkaConnect)) {
            globalRuntimeConfig.put(GlobalRuntimeConfig.KAFKA_CONNECT, kafkaConnect );
        }
        if (notNull(kafkaConnectUsername)) {
            globalRuntimeConfig.put(GlobalRuntimeConfig.KAFKA_CONNECT_USER,kafkaConnectUsername );
        }
        if (notNull(kafkaConnectPassword)) {
            globalRuntimeConfig.put(GlobalRuntimeConfig.KAFKA_CONNECT_PASSWORD, kafkaConnectPassword);
        }
    }


    private void executeBroker() {
        // TODO: implement kafka connect diff
    }

}
