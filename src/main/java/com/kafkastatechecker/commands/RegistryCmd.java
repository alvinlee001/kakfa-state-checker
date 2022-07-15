package com.kafkastatechecker.commands;

import com.kafkastatechecker.config.GlobalRuntimeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@Slf4j
public class RegistryCmd extends AbstractCmd{

    @Autowired
    private GlobalRuntimeConfig globalRuntimeConfig;

    @Autowired
    private ConnectCmd connectCmd;

    @ShellMethod("connect() and run ")
    public void registry(
            @ShellOption(value = "--schema-registry",defaultValue = "") String schemaRegistry,
            @ShellOption(value = "--schema-registry-username",defaultValue = "") String schemaRegistryUsername,
            @ShellOption(value = "--schema-registry-password",defaultValue = "") String schemaRegistryPassword
    ) {
        if (notNull(schemaRegistry)) {
            globalRuntimeConfig.put(GlobalRuntimeConfig.KAFKA, schemaRegistry);
        }
        if (notNull(schemaRegistryUsername)) {
            globalRuntimeConfig.put(GlobalRuntimeConfig.KAFKA_USER, schemaRegistryUsername);
        }
        if (notNull(schemaRegistryPassword)) {
            globalRuntimeConfig.put(GlobalRuntimeConfig.KAFKA_PASSWORD, schemaRegistryPassword);
        }
        executeRegistry();
    }


    private void executeRegistry() {
        // TODO: implement schema registry diff
    }

}
