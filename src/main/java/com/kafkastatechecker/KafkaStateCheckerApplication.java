package com.kafkastatechecker;

import com.kafkastatechecker.helper.KafkaBrokerHelper;
import com.kafkastatechecker.helper.KafkaTopicHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class KafkaStateCheckerApplication implements CommandLineRunner {

	@Autowired
	private KafkaBrokerHelper kafkaBrokerHelper;

	@Autowired
	private KafkaTopicHelper kafkaTopicHelper;

	public static void main(String[] args) {
		SpringApplication.run(KafkaStateCheckerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("EXITING");

		if ( log.isDebugEnabled() ) {
			log.debug("used params");
			for (int i = 0; i < args.length; ++i) {
				log.debug("args[{}]: {}", i, args[i]);
			}
		}
	}
}
