package br.com.springboot.demo.auth.server.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientRequestDTO {
	
	private String clientId;
	
	private String clientSecret;
	

}
