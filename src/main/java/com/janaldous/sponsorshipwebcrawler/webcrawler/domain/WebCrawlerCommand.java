package com.janaldous.sponsorshipwebcrawler.webcrawler.domain;

import java.util.List;

import lombok.Getter;

public class WebCrawlerCommand {

	public WebCrawlerCommand() {
	}
	
	public WebCrawlerCommand(String url, PageType pageType) {
		this.url = url;
		this.pageType = pageType;
	}
	
	public WebCrawlerCommand(List<Job> jobOpenings) {
		this.jobOpenings = jobOpenings;
	}
	
	@Getter
	private String url;
	
	@Getter
	private PageType pageType;
	
	@Getter
	private List<Job> jobOpenings;
	
}
