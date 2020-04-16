package br.com.springboot.demo.s3.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.springboot.demo.s3.service.property.ApplicationProperties;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class SdkS3Configuration {

	@Bean
	public S3Client s3Client(ApplicationProperties properties) {
		AwsCredentials credentials = AwsBasicCredentials.create(properties.getAccessKeyId(),
				properties.getSecretAccessKey());

		return S3Client
				.builder() // @formatter:off
				.credentialsProvider(StaticCredentialsProvider.create(credentials))
				.region(Region.US_EAST_1)
				.build(); // @formatter:on
	}
}
