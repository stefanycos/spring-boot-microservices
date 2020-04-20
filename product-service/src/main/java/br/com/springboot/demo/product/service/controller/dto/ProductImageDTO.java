package br.com.springboot.demo.product.service.controller.dto;

import br.com.springboot.demo.product.service.domain.ImageStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageDTO {

	private String id;
	
	private ImageStatus status;

	private String filename;

	private String base64;

}
