package com.kafkastatechecker.config;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GlobalRuntimeConfig {
    private Map<String, String> configMap = new HashMap<>();

    public static String KAFKA = "KAFKA";
    public static String KAFKA_USER = "KAFKA_USER";
    public static String KAFKA_PASSWORD = "KAFKA_PASSWORD";

    public static String SCHEMA_REGISTRY = "SCHEMA_REGISTRY";
    public static String SCHEMA_REGISTRY_USER = "SCHEMA_REGISTRY_USER";
    public static String SCHEMA_REGISTRY_PASSWORD = "SCHEMA_REGISTRY_PASSWORD";

    public static String KAFKA_CONNECT = "KAFKA_CONNECT";
    public static String KAFKA_CONNECT_USER = "KAFKA_CONNECT_USER";
    public static String KAFKA_CONNECT_PASSWORD = "KAFKA_CONNECT_PASSWORD";

    public void put(String prop, String value) {
        this.configMap.put(prop, value);
    }

    public String get(String prop) {
        return this.configMap.get(prop);
    }
}
