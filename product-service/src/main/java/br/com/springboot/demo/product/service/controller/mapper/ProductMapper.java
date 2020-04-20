package br.com.springboot.demo.product.service.controller.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.springboot.demo.product.service.controller.dto.ProductRequestDTO;
import br.com.springboot.demo.product.service.domain.Product;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ProductMapper {

	private final ModelMapper modelMapper;

	public Product toProduct(ProductRequestDTO requestDTO) {
		return modelMapper.map(requestDTO, Product.class);
	}

}
