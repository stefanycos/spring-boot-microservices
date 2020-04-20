package br.com.springboot.demo.product.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.springboot.demo.product.service.domain.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
