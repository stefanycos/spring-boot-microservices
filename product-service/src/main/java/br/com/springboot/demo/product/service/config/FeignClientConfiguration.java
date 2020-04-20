package br.com.springboot.demo.product.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Request;

@Configuration
public class FeignClientConfiguration {
	
	@Bean
	public Request.Options options() {
	    return new Request.Options(10_000, 600_000);
	}

}
