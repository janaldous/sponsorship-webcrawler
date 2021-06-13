package com.janaldous.sponsorshipwebcrawler.webcrawler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;

import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.WebCrawlResult;
import com.janaldous.sponsorshipwebcrawler.webcrawler.repository.JobOpeningRespository;
import com.janaldous.sponsorshipwebcrawler.webcrawler.selenium.SeleniumConfig;
import com.janaldous.sponsorshipwebcrawler.webcrawler.testutils.VariableSource;

class MainWebCrawlerTest {

	static SeleniumConfig seleniumConfig = new SeleniumConfig();
	JobOpeningRespository jobOpeningRepository = new JobOpeningRespository();
	MainWebCrawler webCrawler = new MainWebCrawler(seleniumConfig, jobOpeningRepository);
	
	static long startTime;
	
	@BeforeAll
	public static void beforeAll() {
		startTime = System.currentTimeMillis();
	}
	
	@AfterEach
	public void afterEach() {
		jobOpeningRepository.clear();
	}
	
	@AfterAll
	public static void afterAll() {
		seleniumConfig.close();
		long duration_s = (System.currentTimeMillis()-startTime) / 1000;
		long duration_min = duration_s/60;
		System.out.println("Finished at " + duration_min);
	}
	
	static Stream<Arguments> arguments = Stream.of(
//			Arguments.of("https://www.100shapes.com/", "https://www.100shapes.com/careers/#jobs", 0),
//			Arguments.of("https://www.zoopla.co.uk/", "https://apply.workable.com/zoopla/", 31),
//			Arguments.of("https://www.brainlabsdigital.com/", "https://www.brainlabsdigital.com/careers/", 0),
//			Arguments.of("https://www.adludio.com/", "https://apply.workable.com/adludio/?lng=en", 0),
//			Arguments.of("https://www.101ways.com/", "https://www.101ways.com/join-us/", 0),
//			Arguments.of("https://www.coadjute.com/", "https://www.coadjute.com/careers", 0),
//			Arguments.of("https://www.demica.com/", "https://www.demica.com/careers/#selected-job", 0),
//			Arguments.of("http://excession.co/", "http://excession.co/", 0),
//			Arguments.of("https://www.fyld.ai/", "https://www.fyld.ai/about-fyld/careers/#current-vacancies", 0),
//			Arguments.of("https://uk.gophr.com/", "https://uk.gophr.com/about-us#careers", 0),
//			Arguments.of("https://www.happy-or-not.com/", "https://www.happy-or-not.com/en/careers/open-positions/", 0),
//			Arguments.of("https://www.infostretch.com/", "https://www.infostretch.com/about/careers/", 0),
//			Arguments.of("http://www.jayam.co.uk/", "http://www.jayam.co.uk/", 0),
//			Arguments.of("https://www.konstructive.com/", "https://www.konstructive.com/careers", 0),
			Arguments.of("https://www.lyst.co.uk/", "https://www.lyst.co.uk/careers/", 0),
			Arguments.of("https://www.madebymany.com/", "https://www.madebymany.com/join", 0),
			Arguments.of("https://www.nodesagency.com/", "https://nodes.recruitee.com/", 0),
			Arguments.of("https://orionhealth.com/uk/", "https://orionhealth.com/uk/careers/job-search/", 0),
			Arguments.of("http://www.polarisoft.com/", "http://www.polarisoft.com/", 0),
			Arguments.of("https://www.quantcast.com/", "https://www.quantcast.com/careers/openings/", 0),
			Arguments.of("https://ripple.com/", "https://ripple.com/company/careers/all-jobs", 0),
			Arguments.of("http://www.scskeu.com/en/", "http://www.scskeu.com/en/career.html", 0),
			Arguments.of("https://www.currencycloud.com/", "https://currencycloud.pinpointhq.com/", 0),
			Arguments.of("https://www.unbiased.co.uk/", "https://careers.unbiased.co.uk/#section-jobs", 0),
			Arguments.of("https://www.vermeg.com/", "https://recruitment.vermeg.com/jobs/", 0),
			Arguments.of("https://whichit.co/", "https://whichit.co/jobs", 0),
			Arguments.of("https://www.xoomworks.com/", "https://content.xoomworks.com/xoomworks-group-job-listings", 0),
			Arguments.of("https://yayzy.com/", "https://yayzy.com/", 0),
			Arguments.of("https://www.zhero.co.uk/", "https://www.zhero.co.uk/careers-new/", 0)
		);

	@ParameterizedTest
	@VariableSource("arguments")
	void testReturnsCareersUrl_whenCareersPageExists(String inputUrl, String expectedOutput, int expectedAddedJobs) throws IOException {
 		WebCrawlResult result = webCrawler.getCareersPageUrl(inputUrl);
		assertEquals(expectedOutput, result.getUrl());
		if (expectedAddedJobs > 0) {
			assertTrue(!jobOpeningRepository.findAll().isEmpty());
		}
	}
	
}
