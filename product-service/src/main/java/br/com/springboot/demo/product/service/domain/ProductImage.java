package br.com.springboot.demo.product.service.domain;

import org.springframework.data.annotation.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImage {

	private String filename;
	
	@Transient
	private String base64;
	
	private String ulr;
}
