package br.com.springboot.demo.s3.service.controller.errors;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
	
	private final LocalDateTime timestamp = LocalDateTime.now();

	private Integer status;

	private String error;

	@JsonInclude(Include.NON_EMPTY)
	private String code;

	private String message;

	private String path;

	private Object trace;

	@JsonInclude(Include.NON_EMPTY)
	private Map<String, Object> violations;

	public void addViolation(String key, Object value) {
		if (violations == null) {
			violations = new HashMap<>();
		}
		violations.put(key, value);
	}

}
