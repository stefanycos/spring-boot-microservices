package br.com.springboot.demo.auth.server.controller;


import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
	
	@GetMapping
	public Principal auth(Principal user) {
		return user;
	}

}
