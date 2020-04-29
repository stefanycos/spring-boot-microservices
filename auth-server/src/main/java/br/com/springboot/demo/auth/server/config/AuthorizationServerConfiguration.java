package br.com.springboot.demo.auth.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

import br.com.springboot.demo.auth.server.service.detail.ClientDetailServiceImpl;
import br.com.springboot.demo.auth.server.service.detail.UserDetailServiceImpl;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	private AuthenticationManager authenticationManager;

	private UserDetailServiceImpl userDetailsService;

	private ClientDetailServiceImpl clientDetailService;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetailService);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception { //@formatter:off
		endpoints.authenticationManager(authenticationManager)
				 .userDetailsService(userDetailsService); 
	}
   
}
