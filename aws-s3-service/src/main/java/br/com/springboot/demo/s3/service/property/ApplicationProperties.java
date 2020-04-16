package br.com.springboot.demo.s3.service.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties
public class ApplicationProperties {

	private String accessKeyId;

	private String secretAccessKey;

}
