package br.com.springboot.demo.s3.service.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileRequestDTO {

	@NotNull
	@NotEmpty
	private String bucket;

	@NotNull
	@NotEmpty
	private String key;

	@NotNull(groups = Views.onCreate.class)
	@NotNull(groups = Views.onCreate.class)
	private String base64;

}
