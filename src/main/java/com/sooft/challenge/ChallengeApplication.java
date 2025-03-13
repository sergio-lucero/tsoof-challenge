package com.sooft.challenge;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;


@SpringBootApplication(scanBasePackages = "com.sooft.challenge")
@EntityScan(basePackages = "com.sooft.challenge")
@ServletComponentScan(basePackages = "com.sooft.challenge")
public class ChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${project.version}") String appVersion) {
		return new OpenAPI().components(new Components())
				.info(new Info().title("Sofft challage API").version(appVersion));
	}
}
