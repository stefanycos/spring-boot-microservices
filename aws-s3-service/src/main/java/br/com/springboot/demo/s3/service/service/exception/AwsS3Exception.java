package br.com.springboot.demo.s3.service.service.exception;

public class AwsS3Exception extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AwsS3Exception(String message) {
		super(message);
	}

}
