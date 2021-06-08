package com.janaldous.sponsorshipwebcrawler.webcrawler;

import java.io.IOException;

import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.WebCrawlerCommand;

public class JobListingWebCrawler implements PageCrawler {

	@Override
	public WebCrawlerCommand crawl(String url) throws IOException {
		return new WebCrawlerCommand();
	}

}
