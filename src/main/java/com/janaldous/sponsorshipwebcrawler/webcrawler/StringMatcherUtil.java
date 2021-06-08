package com.janaldous.sponsorshipwebcrawler.webcrawler;

import java.util.regex.Pattern;

public class StringMatcherUtil {

	public static Pattern careerLinkLabel = Pattern.compile("\s*(career[s]?|job[s]?)\s*", Pattern.CASE_INSENSITIVE);

	public static boolean matchesCareerLabel(String text) {
		return careerLinkLabel.matcher(text).matches();
	}

	public static Pattern viewJobListingLabel = Pattern.compile("w3schools", Pattern.CASE_INSENSITIVE);
	
	public static boolean matchesViewJobListingLabel(String text) {
		return viewJobListingLabel.matcher(text).matches();
	}
	
}
