package br.com.springboot.demo.product.service.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import br.com.springboot.demo.product.service.controller.dto.Views.OnCreate;
import br.com.springboot.demo.product.service.controller.dto.Views.OnUpdate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {

	@NotNull(groups = OnCreate.class)
	@NotEmpty(groups = OnCreate.class)
	private String name;

	@NotNull(groups = OnCreate.class)
	@NotEmpty(groups = OnCreate.class)
	private String description;

	@NotNull(groups = OnCreate.class)
	@Null(groups = OnUpdate.class)
	private Integer amount;
	
	private ProductImageDTO image;
}
