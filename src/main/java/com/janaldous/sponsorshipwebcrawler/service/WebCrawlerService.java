package com.janaldous.sponsorshipwebcrawler.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import com.janaldous.sponsorshipwebcrawler.service.domain.WebCrawlerRequest;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class WebCrawlerService {

	@ServiceActivator(inputChannel = "webCrawlerRequest")
	public void acceptRequest(WebCrawlerRequest request) {
		log.info("do something with web crawler request");
	}
	
}
