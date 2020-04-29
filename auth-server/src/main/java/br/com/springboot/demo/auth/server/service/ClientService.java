package br.com.springboot.demo.auth.server.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.springboot.demo.auth.server.domain.Client;
import br.com.springboot.demo.auth.server.repository.ClientRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClientService {

	private final ClientRepository repositiry;

	public Client findByClientId(String clientId) {
		return repositiry.findByClientIdIgnoreCase(clientId)
				.orElseThrow(() -> new UsernameNotFoundException("Client not found. Client: " + clientId));
	}

	public Client save(Client client) {
		if (this.exists(client)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Client with id " + client.getClientId() + " is already in use.");
		}

		return repositiry.save(client);
	}

	public List<Client> findAll() {
		return repositiry.findAll();
	}

	private boolean exists(Client client) {
		return repositiry.findByClientIdIgnoreCase(client.getClientId()).isPresent();
	}

}
