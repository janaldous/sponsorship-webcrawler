package com.janaldous.sponsorshipwebcrawler.webcrawler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.WebCrawlerCommand;
import com.janaldous.sponsorshipwebcrawler.webcrawler.selenium.SeleniumConfig;

class WorkableSeleniumCrawlerTest {

	private static WorkableCrawler workableSeleniumCrawler;
	private static SeleniumConfig seleniumConfig;

	@BeforeAll
	static void beforeAll() {
		seleniumConfig = new SeleniumConfig();
		workableSeleniumCrawler = new WorkableCrawler(seleniumConfig);
	}

	@AfterAll
	static void afterAll() {
		seleniumConfig.getDriver().close();
	}

	@Test
	void testZoopla() throws IOException {
		WebCrawlerCommand result = workableSeleniumCrawler.crawl("https://apply.workable.com/zoopla/");
		assertEquals(31, result.getJobOpenings().size());
	}

	@Test
	void testAdludio() throws IOException {
		WebCrawlerCommand result = workableSeleniumCrawler.crawl("https://apply.workable.com/adludio/");
		assertTrue(result.getJobOpenings().isEmpty());
	}

}
