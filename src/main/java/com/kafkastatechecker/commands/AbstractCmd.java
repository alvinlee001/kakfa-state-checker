package com.kafkastatechecker.commands;

import static org.springframework.shell.standard.ShellOption.NULL;

public abstract class AbstractCmd {
    boolean notNull(String param) {
        return param != null && !NULL.equals(param);
    }
}
