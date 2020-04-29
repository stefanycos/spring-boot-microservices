package br.com.springboot.demo.auth.server.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.springboot.demo.auth.server.domain.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

	@NotNull
	@NotEmpty
	private String username;

	@NotNull
	@NotEmpty
	private String password;

	@NotNull
	@NotEmpty
	private String email;

	@NotNull
	private Role role;
}
