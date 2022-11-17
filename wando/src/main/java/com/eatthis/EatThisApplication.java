package com.eatthis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EatThisApplication {

	public static void main(String[] args) {
		SpringApplication.run(EatThisApplication.class, args);
	}

}
