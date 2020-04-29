package br.com.springboot.demo.auth.server.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.springboot.demo.auth.server.domain.User;

public interface UserRepositiry extends MongoRepository<User, String> {

	Optional<User> findByEmailIgnoreCase(String email);

}
