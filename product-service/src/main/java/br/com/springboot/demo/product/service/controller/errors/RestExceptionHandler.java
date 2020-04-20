package br.com.springboot.demo.product.service.controller.errors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
		// @formatter:off
		ErrorDTO details = ErrorDTO.builder()
			.status(HttpStatus.BAD_REQUEST.value())
			.error("Invalid request")
			.message("Validation Failed")
			.path(request.getRequestURI())
			.build();
		// @formatter:on

		ex.getConstraintViolations().forEach(
				violation -> details.addViolation(violation.getPropertyPath().toString(), violation.getMessage()));

		return details;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		// @formatter:off
		ErrorDTO details = ErrorDTO.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.error("Invalid request")
				.message("Validation Failed")
				.path(request.getRequestURI())
				.build();
		// @formatter:on

		BindingResult result = ex.getBindingResult();

		result.getGlobalErrors().forEach(
				objectError -> details.addViolation(objectError.getObjectName(), objectError.getDefaultMessage()));

		result.getFieldErrors()
				.forEach(fieldError -> details.addViolation(fieldError.getField(), fieldError.getDefaultMessage()));

		return details;
	}

}
