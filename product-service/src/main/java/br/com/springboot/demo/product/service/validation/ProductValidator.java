package br.com.springboot.demo.product.service.validation;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.springboot.demo.product.service.controller.dto.ProductRequestDTO;

@Component
public class ProductValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ProductRequestDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProductRequestDTO request = (ProductRequestDTO) target;
		this.validateBase64(request, errors);
	}

	private void validateBase64(ProductRequestDTO request, Errors errors) {
		if (request.getImage() == null) {
			return;
		}

		String extension = FilenameUtils.getExtension(request.getImage().getFilename());

		if (extension == null || extension.isEmpty()) {
			errors.rejectValue("filename.empty", "Field filename must hava a valid extension or field is empty");
			return;
		}

		if (request.getImage().getBase64() == null || request.getImage().getBase64().isEmpty()) {
			errors.rejectValue("base64.empty", "Field base64 could not be empty");
		}

	}

}
