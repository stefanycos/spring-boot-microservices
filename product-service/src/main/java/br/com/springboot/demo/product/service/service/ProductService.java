package br.com.springboot.demo.product.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import br.com.springboot.demo.product.service.client.FileClient;
import br.com.springboot.demo.product.service.client.request.FileRequest;
import br.com.springboot.demo.product.service.client.response.FileResponse;
import br.com.springboot.demo.product.service.config.ApplicationProperties;
import br.com.springboot.demo.product.service.domain.Product;
import br.com.springboot.demo.product.service.repository.ProductRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

	private final ProductRepository repository;

	private final MongoTemplate mongoTemplate;

	private final FileClient fileClient;

	private final ApplicationProperties properties;

	public void save(Product product) {
		this.callFileServiceAndSetUrl(product);
		repository.save(product);
	}

	public void delete(Product product) {
		this.deleteProductImage(product);
		repository.delete(product);
	}

	public Optional<Product> findById(String id) {
		return repository.findById(id);
	}

	public Page<Product> findAll(Pageable pageable) {
		Query query = new Query().with(pageable);

		List<Product> products = mongoTemplate.find(query, Product.class);
		return PageableExecutionUtils.getPage(products, pageable, () -> mongoTemplate.count(query, Product.class));
	}

	private void callFileServiceAndSetUrl(Product product) {
		if (product.getImage() == null || product.getId() != null) {
			return;
		}

		//@formatter:off
		FileRequest request = FileRequest.builder() 
				.base64(product.getImage().getBase64())
				.key(product.getImage().getFilename())
				.bucket(properties.getBucket())
				.build(); //@formatter:on

		FileResponse response = fileClient.upload(request);
		product.getImage().setUlr(response.getFileUrl());
	}

	private void deleteProductImage(Product product) {
		if (product.getImage() == null) {
			return;
		}

		FileRequest request = FileRequest
				.builder() //@formatter:off
				.key(product.getImage().getFilename())
				.bucket(properties.getBucket())
				.build(); //@formatter:on

		fileClient.delete(request);
	}

}
