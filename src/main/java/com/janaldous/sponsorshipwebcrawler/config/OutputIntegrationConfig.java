package com.janaldous.sponsorshipwebcrawler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;

import com.janaldous.sponsorshipwebcrawler.service.domain.WebCrawlerResult;

@Configuration
public class OutputIntegrationConfig {

	@Bean
	@ServiceActivator(inputChannel = "webCrawlerResult")
	public HttpRequestExecutingMessageHandler outbound() {
	    HttpRequestExecutingMessageHandler handler =
	        new HttpRequestExecutingMessageHandler("http://localhost:8080/pdfsponsor");
	    handler.setHttpMethod(HttpMethod.POST);
	    handler.setExpectedResponseType(WebCrawlerResult.class);
	    handler.setOutputChannelName("nullChannel");
	    return handler;
	}
}
