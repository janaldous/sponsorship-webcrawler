package com.janaldous.sponsorshipwebcrawler.webcrawler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.janaldous.sponsorshipwebcrawler.webcrawler.repository.JobOpeningRespository;
import com.janaldous.sponsorshipwebcrawler.webcrawler.selenium.SeleniumConfig;

class MainWebCrawlerTest {

	SeleniumConfig seleniumConfig = new SeleniumConfig();
	JobOpeningRespository jobOpeningRepository = new JobOpeningRespository();
	MainWebCrawler webCrawler = new MainWebCrawler(seleniumConfig, jobOpeningRepository);
	
	
	@Test
	void testGetsCareersUrl_whenCareersPageExists_100shapes() {
		String result = webCrawler.getCareersPageUrl("https://www.100shapes.com/");
		assertNull(result);
		assertEquals("https://www.100shapes.com/careers/", result);
	}
	
	@Test
	void test2() {
		String result = webCrawler.getCareersPageUrl("https://www.zoopla.co.uk/");
		assertNull(result);
		assertEquals(31, jobOpeningRepository.findAll());
	}

}
