package br.com.springboot.demo.auth.server.mapper;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.springboot.demo.auth.server.controller.dto.UserRequestDTO;
import br.com.springboot.demo.auth.server.domain.Profile;
import br.com.springboot.demo.auth.server.domain.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class UserMapper {

	private final ModelMapper modelMapper;

	private final PasswordEncoder passwordEncoder;

	public User toUser(UserRequestDTO request) {
		User user = modelMapper.map(request, User.class);
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		Profile profile = new Profile(request.getRole());
		user.setProfiles(Arrays.asList(profile));

		return user;
	}

}
