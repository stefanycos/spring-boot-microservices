package br.com.springboot.demo.auth.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.springboot.demo.auth.server.domain.User;
import br.com.springboot.demo.auth.server.repository.UserRepositiry;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

	private final UserRepositiry repositiry;

	public User findByEmail(String email) {
		return repositiry.findByEmailIgnoreCase(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found. Email: " + email));
	}

	public User create(User user) {
		if (this.exists(user)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"User with email " + user.getEmail() + " is already in use.");
		}

		return repositiry.save(user);
	}

	public Optional<User> findById(String id) {
		return repositiry.findById(id);
	}

	public List<User> findAll() {
		return repositiry.findAll();
	}

	public void delete(User user) {
		repositiry.delete(user);
	}

	private boolean exists(User user) {
		return repositiry.findByEmailIgnoreCase(user.getEmail()).isPresent();
	}

}
