package br.com.springboot.demo.auth.server.mapper;

import java.util.Arrays;
import java.util.HashSet;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import br.com.springboot.demo.auth.server.config.AppProperties;
import br.com.springboot.demo.auth.server.controller.dto.ClientRequestDTO;
import br.com.springboot.demo.auth.server.domain.Client;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ClientMapper {

	private final ModelMapper modelMapper;

	private final AppProperties properties;
	
	private final PasswordEncoder passwordEncoder;

	public BaseClientDetails toBaseClientDetails(Client client) {
		return modelMapper.map(client, BaseClientDetails.class);
	}

	public Client toClientWithProperties(ClientRequestDTO request) {
		Client client = modelMapper.map(request, Client.class);
		
		client.setAccessTokenValiditySeconds(properties.getClient().getAccessTokenValiditySeconds());
		client.setRefreshTokenValiditySeconds(properties.getClient().getRefreshTokenValiditySeconds());
		client.setScope(new HashSet<String>(Arrays.asList(properties.getClient().getScope())));
		client.setAuthorizedGrantTypes(new HashSet<String>(Arrays.asList(properties.getClient().getAuthorizedGrantTypes())));
		client.setClientSecret(passwordEncoder.encode(request.getClientSecret()));

		return client;
	}
}
