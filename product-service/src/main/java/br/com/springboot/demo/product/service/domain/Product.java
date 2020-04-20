package br.com.springboot.demo.product.service.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(content = Include.NON_NULL)
@Document(collection = Product.COLLECTION_NAME)
@Data
public class Product {

	public static final String COLLECTION_NAME = "products";

	@Id
	private String id;

	@NotNull
	private String name;

	private String description;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	@NotNull
	private Integer amount;

	private ProductImage image;
	
	private Boolean enabled = true;

}
