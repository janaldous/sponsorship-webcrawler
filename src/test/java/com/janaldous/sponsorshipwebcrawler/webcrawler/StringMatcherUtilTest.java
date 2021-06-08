package com.janaldous.sponsorshipwebcrawler.webcrawler;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StringMatcherUtilTest {

	@ParameterizedTest
	@ValueSource(strings = {"See all jobs", "View job openings", " see all jobs "})
	void matchViewJobListingLabel(String text) {
	    assertTrue(StringMatcherUtil.matchesViewJobListingLabel(text));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"Join us", "Careers", "Career", "Jobs", " Jobs "})
	void matchCareerLinkLabel(String text) {
	    assertTrue(StringMatcherUtil.matchesCareerLink(text));
	}

}
