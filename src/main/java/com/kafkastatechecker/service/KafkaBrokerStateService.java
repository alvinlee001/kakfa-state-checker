package com.kafkastatechecker.service;

import com.kafkastatechecker.helper.DiffChecker;
import com.kafkastatechecker.helper.KafkaAdminClientFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.regex.Pattern;

@Service
@Slf4j
public class KafkaBrokerStateService {

    @Autowired
    KafkaAdminClientFacade facade;

    public void test() {
        log.info("test() entry");
        facade.test();

        for (String filetype: Arrays.asList("broker", "topics-main", "topics-config") ) {
           File locatedLatestFile = DiffChecker.locateFileWithPattern(new File("./"), Pattern.compile(filetype+".*-latest.json"));
           if(locatedLatestFile != null) {
               File locatedPrevFile = DiffChecker.locateFileWithPattern(new File("./"), Pattern.compile(filetype+".*-prev.json"));
               if (locatedPrevFile != null) {
                   log.info("##### diffing filetype: {} ######", filetype);
                   try {
                       String latest = DiffChecker.readFile(locatedLatestFile, Charset.defaultCharset());
                       String prev = DiffChecker.readFile(locatedPrevFile, Charset.defaultCharset());
                       DiffChecker.compareJSON(latest, prev);
                   } catch (Exception e) {
                       log.error("Error diffing file", e);
                   }
               }
           } else {
               log.info("Previous not found for filetype: {}", filetype );
           }
        }
    }


}
