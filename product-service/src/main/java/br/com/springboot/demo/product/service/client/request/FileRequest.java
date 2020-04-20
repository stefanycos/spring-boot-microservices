package br.com.springboot.demo.product.service.client.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FileRequest {

	private String bucket;

	private String key;

	private String base64;

}
