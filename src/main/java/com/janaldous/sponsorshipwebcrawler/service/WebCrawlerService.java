package com.janaldous.sponsorshipwebcrawler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.janaldous.sponsorshipwebcrawler.service.domain.WebCrawlerRequest;
import com.janaldous.sponsorshipwebcrawler.webcrawler.MainWebCrawler;
import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.WebCrawlResult;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class WebCrawlerService {
	
	private MainWebCrawler mainWebCrawler;
	
	@Autowired
	public WebCrawlerService(MainWebCrawler mainWebCrawler) {
		this.mainWebCrawler = mainWebCrawler;
	}

	@ServiceActivator(inputChannel = "webCrawlerRequest")
	public void acceptRequest(@Payload WebCrawlerRequest request) {
		log.info("processing {}", request);
		WebCrawlResult result = mainWebCrawler.getCareersPageUrl(request.getUrl());
		log.info("result {}", result.getUrl());
	}
	
}
