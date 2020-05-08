package br.com.springboot.demo.auth.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import br.com.springboot.demo.auth.server.domain.Role;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception { //@formatter:off
		http
			.csrf()
				.disable()
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/v1/users").permitAll()
				.antMatchers(HttpMethod.POST, "/v1/clients").permitAll()
				.antMatchers(HttpMethod.DELETE, "/v1/users/*").hasAuthority(Role.SUPER.name())
				.antMatchers(HttpMethod.DELETE, "/v1/clients/*").hasAuthority(Role.SUPER.name())
			.anyRequest()
				.authenticated(); //@formatter:on
	}
}
