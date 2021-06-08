package com.janaldous.sponsorshipwebcrawler.webcrawler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;

import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.PageType;
import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.WebCrawlerCommand;
import com.janaldous.sponsorshipwebcrawler.webcrawler.selenium.SeleniumConfig;
import com.janaldous.sponsorshipwebcrawler.webcrawler.testutils.VariableSource;

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
	
	static Stream<Arguments> arguments = Stream.of(
//			Arguments.of("https://www.100shapes.com/careers/", "https://www.100shapes.com/careers/#jobs"),
//			Arguments.of("https://careers.zoopla.co.uk/", "https://apply.workable.com/zoopla/"),
//			Arguments.of("https://www.nodesagency.com/join-us/", "https://nodes.recruitee.com/"),
//			Arguments.of("https://orionhealth.com/uk/careers/", "https://orionhealth.com/uk/careers/job-search/"),
//			Arguments.of("https://www.quantcast.com/careers/", "https://www.quantcast.com/careers/openings/"),
//			Arguments.of("https://ripple.com/company/careers/", "https://ripple.com/company/careers/all-jobs"),
			Arguments.of("https://careers.unbiased.co.uk/", "https://careers.unbiased.co.uk/#section-jobs")
			
		);
	
	static Stream<Arguments> arguments2 = Stream.of(
			Arguments.of("https://www.brainlabsdigital.com/careers/"));

	@ParameterizedTest
	@VariableSource("arguments")
	void testReturnsCareersUrl_whenCareersPageExists(String inputUrl, String expectedOutput) throws IOException {
		WebCrawlerCommand result = careersPageCrawler.crawl(inputUrl);
		assertNotNull(result);
		assertEquals(PageType.JOB_LISTING, result.getPageType());
		assertEquals(expectedOutput, result.getUrl());
	}
	
	@Disabled
	@ParameterizedTest
	@VariableSource("arguments2")
	void testReturnsCareersUrl_argumentsNoResult(String inputUrl) throws IOException {
		WebCrawlerCommand result = careersPageCrawler.crawl(inputUrl);
		assertNotNull(result);
		assertNull(result.getPageType());
	}

}
