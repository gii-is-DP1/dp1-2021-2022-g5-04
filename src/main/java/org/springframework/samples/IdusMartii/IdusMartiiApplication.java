package org.springframework.samples.IdusMartii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication()
@EnableJpaAuditing
public class IdusMartiiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdusMartiiApplication.class, args);
		log.info("Aplicación iniciada");
	}

}
