package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class RedirectApplication {

	@Autowired private RestTemplate restTemplate;
	@Autowired private Environment environment;

	@RequestMapping
	public String redirect(
			@RequestParam(value="redirect.scheme", defaultValue = "http") String redirectScheme,
			@RequestParam(value="redirect.host", required = false) String redirectHost,
			@RequestParam(value="redirect.port", defaultValue = "80") String redirectPort
	) {
		redirectHost = redirectHost == null ? environment.getRequiredProperty("redirect.host") : redirectHost;
		ResponseEntity<String> entity = restTemplate.getForEntity(String.format("%s://%s:%s", redirectScheme, redirectHost, Integer.valueOf(redirectPort)), String.class);
		return entity.getBody();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(RedirectApplication.class, args);
	}
}
