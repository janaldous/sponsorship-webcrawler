package com.janaldous.sponsorshipwebcrawler.webcrawler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.PageType;
import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.WebCrawlerCommand;
import com.janaldous.sponsorshipwebcrawler.webcrawler.selenium.SeleniumConfig;
import com.janaldous.sponsorshipwebcrawler.webcrawler.testutils.VariableSource;

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
	
	static Stream<Arguments> arguments = Stream.of(
			Arguments.of("https://www.100shapes.com/careers/", "https://www.100shapes.com/careers/#jobs"),
			Arguments.of("https://careers.zoopla.co.uk/", "https://apply.workable.com/zoopla/"));

//	@ParameterizedTest
	@VariableSource("arguments")
	void testReturnsCareersUrl_whenCareersPageExists(String inputUrl, String expectedOutput) throws IOException {
		WebCrawlerCommand result = workableSeleniumCrawler.crawl(inputUrl);
		assertNotNull(result);
		assertEquals(PageType.JOB_LISTING, result.getPageType());
		assertEquals(expectedOutput, result.getUrl());
	}

}
