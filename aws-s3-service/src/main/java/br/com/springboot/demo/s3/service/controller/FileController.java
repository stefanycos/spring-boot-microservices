package br.com.springboot.demo.s3.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.demo.s3.service.controller.dto.FileRequestDTO;
import br.com.springboot.demo.s3.service.controller.dto.FileResponseDTO;
import br.com.springboot.demo.s3.service.controller.dto.Views;
import br.com.springboot.demo.s3.service.service.FileService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/files")
public class FileController {

	private FileService fileService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FileResponseDTO upload(@Validated(Views.onCreate.class) @RequestBody FileRequestDTO payload) {
		return fileService.upload(payload);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping
	public void delete(@Validated @RequestBody FileRequestDTO payload) {
		fileService.delete(payload);
	}

}
