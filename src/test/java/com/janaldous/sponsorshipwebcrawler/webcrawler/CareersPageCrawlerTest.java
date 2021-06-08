package com.janaldous.sponsorshipwebcrawler.webcrawler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.PageType;
import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.WebCrawlerCommand;
import com.janaldous.sponsorshipwebcrawler.webcrawler.selenium.SeleniumConfig;

class CareersPageCrawlerTest {

	private static CareersPageCrawler careersPageCrawler;
	private static SeleniumConfig seleniumConfig;

	@BeforeAll
	static void beforeAll() {
		seleniumConfig = new SeleniumConfig();
		careersPageCrawler = new CareersPageCrawler(seleniumConfig);
	}
	
	@AfterAll
	static void afterAll() {
		seleniumConfig.getDriver().close();
	}
	
	@Test
	void test_Zoopla() throws IOException {
		WebCrawlerCommand result = careersPageCrawler.crawl("https://careers.zoopla.co.uk/");
		assertNotNull(result);
		assertEquals(PageType.JOB_LISTING, result.getPageType());
		assertEquals("https://apply.workable.com/zoopla/", result.getUrl());
	}
	
	@Test
	void test_100shapes() throws IOException {
		WebCrawlerCommand result = careersPageCrawler.crawl("https://www.100shapes.com/careers/");
		assertNotNull(result);
		assertEquals(PageType.JOB_LISTING, result.getPageType());
		assertEquals("https://www.100shapes.com/careers/#jobs", result.getUrl());
	}

}
