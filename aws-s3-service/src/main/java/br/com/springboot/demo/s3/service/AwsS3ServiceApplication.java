package br.com.springboot.demo.s3.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import br.com.springboot.demo.s3.service.property.ApplicationProperties;

@EnableConfigurationProperties(ApplicationProperties.class)
@EnableEurekaClient
@SpringBootApplication
public class AwsS3ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsS3ServiceApplication.class, args);
	}

}
