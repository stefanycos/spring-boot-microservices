package br.com.springboot.demo.product.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.springboot.demo.product.service.client.request.FileRequest;
import br.com.springboot.demo.product.service.client.response.FileResponse;
import br.com.springboot.demo.product.service.config.FeignClientConfiguration;

@FeignClient(name = "s3-service", path = "/s3-service/v1/files", configuration = FeignClientConfiguration.class)
public interface FileClient {

	@PostMapping
	FileResponse upload(@RequestBody FileRequest payload);

	@DeleteMapping
	void delete(@RequestBody FileRequest payload);

}
