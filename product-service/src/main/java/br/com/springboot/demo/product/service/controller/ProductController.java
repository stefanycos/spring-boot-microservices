package br.com.springboot.demo.product.service.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.springboot.demo.product.service.controller.dto.ProductRequestDTO;
import br.com.springboot.demo.product.service.controller.dto.Views.OnCreate;
import br.com.springboot.demo.product.service.controller.mapper.ProductMapper;
import br.com.springboot.demo.product.service.domain.Product;
import br.com.springboot.demo.product.service.service.ProductService;
import br.com.springboot.demo.product.service.utils.ObjectUtils;
import br.com.springboot.demo.product.service.validation.ProductValidator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/products")
public class ProductController {

	private final ProductService service;

	private final ProductMapper mapper;

	private final ProductValidator validator;

	@GetMapping("/{id}")
	public Product read(@PathVariable String id) {
		return service.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));
	}

	@GetMapping
	public Page<Product> list(Pageable pageable) {
		return service.findAll(pageable);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Product create(@RequestBody @Validated(OnCreate.class) ProductRequestDTO payload) {
		Product product = mapper.toProduct(payload);
		service.save(product);
		return product;
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		Product product = this.read(id);
		service.delete(product);
	}

	@PutMapping("/{id}")
	public Product update(@PathVariable String id, @RequestBody ProductRequestDTO payload) {
		Product product = this.read(id);
		ObjectUtils.copyNonNullProperties(payload, product);
		service.save(product);

		return product;
	}

	@InitBinder("productRequestDTO")
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(validator);
	}

}
