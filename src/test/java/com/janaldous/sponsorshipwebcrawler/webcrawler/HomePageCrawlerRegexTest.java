package com.janaldous.sponsorshipwebcrawler.webcrawler;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;

import org.junit.jupiter.api.Test;

class HomePageCrawlerRegexTest {

	@Test
	void testCareerLinkLabelRegex() {
		Matcher labelMatch = HomePageCrawler.careerLinkLabel.matcher("Careers");
		assertTrue(labelMatch.matches());
	}
	
	@Test
	void testCareerLinkLabelRegex_withLeadingAndTrailingSpaces() {
		Matcher labelMatch = HomePageCrawler.careerLinkLabel.matcher(" Careers ");
		assertTrue(labelMatch.matches());
	}

}
