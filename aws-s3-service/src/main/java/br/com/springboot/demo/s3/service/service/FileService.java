package br.com.springboot.demo.s3.service.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

import org.springframework.stereotype.Service;

import br.com.springboot.demo.s3.service.controller.dto.FileRequestDTO;
import br.com.springboot.demo.s3.service.controller.dto.FileResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class FileService {

	private static final String PREFIX = "https://";
	private static final String S3_ENDPOINT = ".s3.amazonaws.com/";

	private AwsS3Service s3Service;

	public FileResponseDTO upload(FileRequestDTO fileRequest) {
		String key = fileRequest.getKey();
		log.info("Uploading file with key '{}'", key);

		InputStream input = loadInputStream(fileRequest.getBase64());
		s3Service.makePutRequest(key, fileRequest.getBucket(), input);

		String fileUrl = this.buildFileUrl(key, fileRequest.getBucket());
		log.info("File uploaded successfully. Download URL '{}'", fileUrl);

		return new FileResponseDTO(fileUrl);
	}

	public void delete(FileRequestDTO fileRequest) {
		log.info("Deleting file with key '{}' in bucket '{}'", fileRequest.getKey(), fileRequest.getBucket());
		s3Service.makeDeleteRequest(fileRequest.getKey(), fileRequest.getBucket());
	}

	private InputStream loadInputStream(String base64) {
		byte[] bytes = Base64.getDecoder().decode(base64);
		return new ByteArrayInputStream(bytes);
	}

	private String buildFileUrl(String key, String bucket) {
		StringBuilder builder = new StringBuilder(); //@formatter:off
		builder.append(PREFIX)
			   .append(bucket)
			   .append(S3_ENDPOINT)
			   .append(key);
		
		return builder.toString(); //@formatter:on
	}

}
