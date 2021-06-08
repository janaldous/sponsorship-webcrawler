package com.janaldous.sponsorshipwebcrawler.webcrawler;

import java.io.IOException;

import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.WebCrawlerCommand;

public interface PageCrawler {

	WebCrawlerCommand crawl(String url) throws IOException;
	
}
