package br.com.springboot.demo.auth.server.domain;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Profile implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	private Role role;

	@Override
	public String getAuthority() {
		return this.role.name();
	}

}
