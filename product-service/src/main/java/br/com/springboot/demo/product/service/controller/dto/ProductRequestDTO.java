package br.com.springboot.demo.product.service.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.springboot.demo.product.service.controller.dto.Views.OnCreate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {

	@NotNull(groups = OnCreate.class)
	@NotEmpty(groups = OnCreate.class)
	private String name;

	private String description;

	@NotNull(groups = OnCreate.class)
	private Integer amount;
	
	private ProductImageDTO image;
}
