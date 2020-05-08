package br.com.springboot.demo.product.service.domain;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(value = Include.NON_NULL)
@Getter
@Setter
public class ProductImage {
	
	private String id;

	private String filename;
	
	@Transient
	private String base64;
	
	private String url;
	
	private String key;
	
	private ImageStatus status;
}
