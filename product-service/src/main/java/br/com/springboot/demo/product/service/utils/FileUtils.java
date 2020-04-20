package br.com.springboot.demo.product.service.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

import org.apache.commons.io.IOUtils;

import lombok.SneakyThrows;

public class FileUtils {

	private FileUtils() {
	}

	public static InputStream toInputStream(String base64) {
		final byte[] decodedBytes = Base64.getDecoder().decode(base64);
		return new ByteArrayInputStream(decodedBytes);
	}

	@SneakyThrows
	public static String toBase64(InputStream input) {
		byte[] bytes = IOUtils.toByteArray(input);
		byte[] encoded = Base64.getEncoder().encode(bytes);

		return new String(encoded);
	}

}
