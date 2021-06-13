package com.janaldous.sponsorshipwebcrawler.webcrawler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;

import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.WebCrawlerCommand;
import com.janaldous.sponsorshipwebcrawler.webcrawler.selenium.SeleniumConfig;
import com.janaldous.sponsorshipwebcrawler.webcrawler.testutils.VariableSource;

import lombok.extern.log4j.Log4j2;

@Log4j2
class HomePageSeleniumJsoupCrawlerTest {

	private static HomePageSeleniumCrawler homePageSeleniumCrawler;
	private static SeleniumConfig seleniumConfig;

	private static long startTimestamp;
	
	@BeforeAll
	static void beforeAll() {
		startTimestamp = System.currentTimeMillis();
		seleniumConfig = new SeleniumConfig();
		homePageSeleniumCrawler = new HomePageSeleniumCrawler(seleniumConfig);
	}

	@AfterAll
	static void afterAll() {
		seleniumConfig.getDriver().close();
		long duration_s = (System.currentTimeMillis()-startTimestamp) / 1000;
		log.info("total duration: " + duration_s);
	}

	static Stream<Arguments> arguments = Stream.of(
			Arguments.of("https://www.adludio.com/", "https://apply.workable.com/adludio/?lng=en"),
			Arguments.of("https://www.adtechnology.com/", "https://www.adtechnology.com/company/careers"),
			Arguments.of("https://www.101ways.com/", "https://www.101ways.com/join-us/"),
			Arguments.of("https://www.100shapes.com/", "https://www.100shapes.com/careers/"),
			Arguments.of("https://www.zoopla.co.uk/", "https://careers.zoopla.co.uk/"),
			Arguments.of("https://www.adtechnology.com/", "https://www.adtechnology.com/company/careers"),
			Arguments.of("https://www.brainlabsdigital.com/", "https://www.brainlabsdigital.com/careers/"),
			Arguments.of("https://www.madebymany.com/", "https://www.madebymany.com/join"),
			Arguments.of("https://orionhealth.com/uk/", "https://orionhealth.com/uk/careers/job-search/"),
			Arguments.of("http://www.scskeu.com/en/", "http://www.scskeu.com/en/career.html")
		);

	@ParameterizedTest
	@VariableSource("arguments")
	void testReturnsCareersUrl_whenCareersPageExists(String inputUrl, String expectedOutput) throws IOException {
		WebCrawlerCommand result = homePageSeleniumCrawler.crawl(inputUrl);
		assertNotNull(result);
		assertEquals(expectedOutput, result.getUrl());
	}

}
