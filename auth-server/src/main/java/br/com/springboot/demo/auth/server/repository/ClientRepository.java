package br.com.springboot.demo.auth.server.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.springboot.demo.auth.server.domain.Client;

public interface ClientRepository extends MongoRepository<Client, String> {

	Optional<Client> findByClientIdIgnoreCase(String clientId);

}
