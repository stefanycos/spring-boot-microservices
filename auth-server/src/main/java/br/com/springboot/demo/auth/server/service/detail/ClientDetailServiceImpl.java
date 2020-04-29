package br.com.springboot.demo.auth.server.service.detail;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import br.com.springboot.demo.auth.server.domain.Client;
import br.com.springboot.demo.auth.server.mapper.ClientMapper;
import br.com.springboot.demo.auth.server.service.ClientService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClientDetailServiceImpl implements ClientDetailsService {

	private final ClientService service;

	private final ClientMapper mapper;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		Client client = service.findByClientId(clientId);
		return mapper.toBaseClientDetails(client);
	}

}
