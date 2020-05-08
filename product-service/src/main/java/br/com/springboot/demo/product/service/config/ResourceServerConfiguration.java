package br.com.springboot.demo.product.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception { //@formatter:off
		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/v1/products/*").permitAll()
				.antMatchers(HttpMethod.GET, "/**").permitAll()
				/*.antMatchers("/hystrix", "/hystrix/**").permitAll()
				.antMatchers("/actuator/**", "/actuator").permitAll()
				.antMatchers("/webjars/**", "/resources/**").permitAll()*/
			.anyRequest()
				.authenticated(); //@formatter:on
	}
	
}
