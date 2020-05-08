package br.com.springboot.demo.auth.server.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.springboot.demo.auth.server.controller.dto.UserRequestDTO;
import br.com.springboot.demo.auth.server.domain.User;
import br.com.springboot.demo.auth.server.mapper.UserMapper;
import br.com.springboot.demo.auth.server.service.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {

	private final UserService userService;

	private final UserMapper mapper;

	@GetMapping("/{email}")
	public User read(@PathVariable String email) {
		return userService.findByEmail(email);
	}

	@GetMapping
	public List<User> list() {
		return userService.findAll();
	}

	@PostMapping
	public User create(@Validated @RequestBody UserRequestDTO payload) {
		User user = mapper.toUser(payload);
		return userService.create(user);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		final User user = userService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));

		userService.delete(user);
	}
}