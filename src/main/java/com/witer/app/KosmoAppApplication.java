package com.witer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KosmoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(KosmoAppApplication.class, args);
	}

}
