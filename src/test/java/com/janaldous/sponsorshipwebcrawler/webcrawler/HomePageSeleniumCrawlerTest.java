package com.janaldous.sponsorshipwebcrawler.webcrawler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.WebCrawlerCommand;
import com.janaldous.sponsorshipwebcrawler.webcrawler.selenium.SeleniumConfig;

class HomePageSeleniumCrawlerTest {

	private static HomePageSeleniumCrawler homePageSeleniumCrawler;
	private static SeleniumConfig seleniumConfig;

	@BeforeAll
	static void beforeAll() {
		seleniumConfig = new SeleniumConfig();
		homePageSeleniumCrawler = new HomePageSeleniumCrawler(seleniumConfig);
	}
	
	@AfterAll
	static void afterAll() {
		seleniumConfig.getDriver().close();
	}
	
	
	@Test
	void test() throws IOException {
		WebCrawlerCommand result = homePageSeleniumCrawler.crawl("https://www.adludio.com/");
		assertNotNull(result);
		assertEquals("https://apply.workable.com/adludio/?lng=en", result.getUrl());
	}
	
	@Test
	void testReturnsCareersUrl_whenCareersPageExists2() throws IOException {
		WebCrawlerCommand result = homePageSeleniumCrawler.crawl("https://www.zoopla.co.uk/");
		assertNotNull(result);
		assertEquals("https://careers.zoopla.co.uk/", result.getUrl());
	}
	
	@Test
	void testReturnsCareersUrl_whenCareersPageExists() throws IOException {
		WebCrawlerCommand result = homePageSeleniumCrawler.crawl("https://www.100shapes.com/");
		assertNotNull(result);
		assertEquals("https://www.100shapes.com/careers/", result.getUrl());
	}

}
