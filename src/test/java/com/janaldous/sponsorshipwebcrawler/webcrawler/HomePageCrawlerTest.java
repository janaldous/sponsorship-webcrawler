package com.janaldous.sponsorshipwebcrawler.webcrawler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.WebCrawlerCommand;

class HomePageCrawlerTest {

	HomePageCrawler homePageCrawler = new HomePageCrawler();

	@Test
	void testReturnsCareersUrl_whenCareersPageExists() throws IOException {
		WebCrawlerCommand result = homePageCrawler.crawl("https://www.100shapes.com/");
		assertNotNull(result);
		assertEquals("https://www.100shapes.com/careers/", result.getUrl());
	}
	
	@Test
	void testReturnsCareersUrl_whenCareersPageExists2() throws IOException {
		WebCrawlerCommand result = homePageCrawler.crawl("https://www.zoopla.co.uk/");
		assertNotNull(result);
		assertEquals("https://careers.zoopla.co.uk/", result.getUrl());
	}
	
	@Test
	void testReturnsCareersUrl_whenCareersPageExists3() throws IOException {
		WebCrawlerCommand result = homePageCrawler.crawl("https://www.adludio.com/");
		assertNull(result);
	}
	
}
