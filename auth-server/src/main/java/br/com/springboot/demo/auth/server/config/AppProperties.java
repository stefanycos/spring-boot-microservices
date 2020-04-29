package br.com.springboot.demo.auth.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

	private Client client;

	@Getter
	@Setter
	public static class Client {

		private String scope;

		private int refreshTokenValiditySeconds;

		private int accessTokenValiditySeconds;

		private String authorizedGrantTypes;

	}

}
