package br.com.springboot.demo.product.service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import br.com.springboot.demo.product.service.client.FileClient;
import br.com.springboot.demo.product.service.client.request.FileRequest;
import br.com.springboot.demo.product.service.client.response.FileResponse;
import br.com.springboot.demo.product.service.config.ApplicationProperties;
import br.com.springboot.demo.product.service.domain.ImageStatus;
import br.com.springboot.demo.product.service.domain.Product;
import br.com.springboot.demo.product.service.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService {

	private final ProductRepository repository;

	private final MongoTemplate mongoTemplate;

	private final FileClient fileClient;

	private final ApplicationProperties properties;

	private final FileServiceFallback serviceFallback;

	@HystrixCommand(fallbackMethod = "saveFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public void save(Product product) {
		this.uploadProductImage(product);
		repository.save(product);
	}

	@HystrixCommand(fallbackMethod = "deleteFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
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

	public void saveFallback(Product product) {
		log.info("Executing Fallback for saving product");
		serviceFallback.store(product);
		repository.save(product);
	}

	public void deleteFallback(Product product) {
		log.info("Executing Fallback for deleting product");
		product.getImage().setStatus(ImageStatus.WAITING_DELETION);
		repository.save(product);
		product.setEnabled(false);
	}

	private void uploadProductImage(Product product) {
		if (product.getImage() == null || product.getId() != null) {
			return;
		}

		this.generateAndSetKey(product);
		FileRequest request = this.buildFileRequest(product);

		log.info("Uploading product image. Request '{}'", request);
		FileResponse response = fileClient.upload(request);

		product.getImage().setUrl(response.getFileUrl());

		log.info("Product image uploaded successfully. Response '{}'", response);
	}

	private FileRequest buildFileRequest(Product product) {
		return FileRequest
				.builder() //@formatter:off
				.base64(product.getImage().getBase64())
				.key(product.getImage().getKey())
				.bucket(properties.getBucket())
				.build(); //@formatter:on
	}

	private void deleteProductImage(Product product) {
		if (product.getImage() == null) {
			return;
		}

		FileRequest request = FileRequest
				.builder() //@formatter:off
				.key(product.getImage().getKey())
				.bucket(properties.getBucket())
				.build(); //@formatter:on

		log.info("Deleting product image. Request '{}'", request);
		fileClient.delete(request);
	}

	private void generateAndSetKey(Product product) {
		String uuid = UUID.randomUUID().toString();
		String key = uuid + "_" + product.getImage().getFilename();
		product.getImage().setKey(key);
	}

}
