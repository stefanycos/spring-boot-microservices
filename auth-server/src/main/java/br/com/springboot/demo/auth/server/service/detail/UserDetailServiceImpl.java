package br.com.springboot.demo.auth.server.service.detail;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.springboot.demo.auth.server.service.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {

	private final UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userService.findByEmail(email);
	}

}
