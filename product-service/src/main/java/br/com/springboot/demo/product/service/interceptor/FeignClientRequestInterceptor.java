package br.com.springboot.demo.product.service.interceptor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FeignClientRequestInterceptor implements RequestInterceptor {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String TOKEN_TYPE = "Bearer";

	@Override
	public void apply(RequestTemplate template) {
		Authentication authentication = this.getAuthentication();

		if (authentication == null) {
			return;
		}

		log.info("Adding '{}' header to request;", AUTHORIZATION_HEADER);
		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
		String token = this.buildToken(details);

		template.header(AUTHORIZATION_HEADER, token);
	}

	private Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	private String buildToken(OAuth2AuthenticationDetails details) {
		return TOKEN_TYPE + details.getTokenValue();
	}

}
