package br.com.springboot.demo.product.service.client.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
public class FileRequest {

	private String bucket;

	private String key;

	@ToString.Exclude
	private String base64;

}
