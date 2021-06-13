package com.janaldous.sponsorshipwebcrawler.service.domain;

import lombok.Data;

@Data
public class WebCrawlerRequest {

	private Long id;
	
	private String url;
	
	private String companyName;
	
}
