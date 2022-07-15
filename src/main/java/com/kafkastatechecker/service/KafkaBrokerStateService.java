package com.kafkastatechecker.service;

import com.kafkastatechecker.helper.KafkaAdminClientFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaBrokerStateService {

    @Autowired
    KafkaAdminClientFacade facade;

    public void generateDiffJson() {

    }

    public void test() {
        log.info("test() entry");
        facade.test();
    }


}
