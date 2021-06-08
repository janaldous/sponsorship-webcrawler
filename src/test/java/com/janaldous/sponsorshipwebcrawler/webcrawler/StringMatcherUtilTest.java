package com.janaldous.sponsorshipwebcrawler.webcrawler;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StringMatcherUtilTest {

	// current vacancies, open vacancies

	@ParameterizedTest
	@ValueSource(strings = { "See all jobs", "View job openings", " see all jobs ", "See positions",
			"Current vacancies", "Check open positions", "Job search", "See open roles", "View Open Roles",
			"Job Openings", "Featured Jobs" })
	void testMatchViewJobListingLabel(String text) {
		assertTrue(StringMatcherUtil.matchesViewJobListingLabel(text));
	}

	@ParameterizedTest
	@ValueSource(strings = { "Join us", "Careers", "Career", "Jobs", " Jobs ", "Join", "Career opportunities",
			"Job openings" })
	void testMatchCareerLinkLabel(String text) {
		assertTrue(StringMatcherUtil.matchesCareerLink(text));
	}

}
