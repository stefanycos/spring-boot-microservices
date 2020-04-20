package br.com.springboot.demo.s3.service.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import br.com.springboot.demo.s3.service.service.exception.AwsS3Exception;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@AllArgsConstructor
@Service
public class AwsS3Service {

	private S3Client s3Client;

	public void makePutRequest(String key, String bucket, InputStream input) {
		byte[] bytes = getBytes(input);

		try {

			log.info("Making put request to Amazon S3 with key '{}'", key);

			PutObjectRequest request = buildPutRequest(key, bucket);
			RequestBody requestBody = RequestBody.fromBytes(bytes);
			s3Client.putObject(request, requestBody);

			log.info("Object uploaded successfully!");

		} catch (final Exception e) {
			log.error("Request to Amazon S3 failed with error {}", e.getMessage());
			throw new AwsS3Exception(e.getMessage());
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (final IOException e) {
				log.error("Failed to close stream. Error {}", e.getMessage());
			}
		}
	}

	public void makeDeleteRequest(String key, String bucket) {
		try {

			log.info("Making delete request to Amazon S3 with key '{}'", key);

			DeleteObjectRequest request = buildDeleteRequest(key, bucket);
			s3Client.deleteObject(request);

			log.info("Object deleted successfully!");

		} catch (final Exception e) {
			log.info("Error on trying to delete object with key '{}'. Error {}", key, e.getMessage());
			throw new AwsS3Exception(e.getMessage());
		}
	}

	public void makeGetRequest(String key, String bucket) {
		try {

			GetObjectRequest request = buildGetRequest(key, bucket);

			ResponseTransformer<GetObjectResponse, GetObjectResponse> transformer = ResponseTransformer
					.toFile(Paths.get("multiPartKey"));

			s3Client.getObject(request, transformer);

		} catch (final Exception e) {
			log.info("Error on trying to get object with key '{}'. Error {}", key, e.getMessage());
			throw new AwsS3Exception(e.getMessage());
		}

	}

	private byte[] getBytes(InputStream input) {
		try {
			return IOUtils.toByteArray(input);
		} catch (final IOException e) {
			log.error("Error on trying to read bytes. {}", e.getMessage());
			throw new RuntimeException();
		}
	}

	private GetObjectRequest buildGetRequest(String key, String bucket) { //@formatter:off
		return GetObjectRequest.builder()
				.bucket(bucket)
				.key(key)
				.build();
	}

	private DeleteObjectRequest buildDeleteRequest(String key, String bucket) {
		return DeleteObjectRequest.builder()
				.bucket(bucket)
				.key(key)
				.build();
	}

	private PutObjectRequest buildPutRequest(String key, String bucket) { 
		return PutObjectRequest.builder() 
				.bucket(bucket)
				.key(key)
				.acl(ObjectCannedACL.PUBLIC_READ)
				.build(); //@formatter:on
	}

}
