package com.janaldous.sponsorshipwebcrawler.webcrawler;

import java.util.regex.Pattern;

public class StringMatcherUtil {

	public final static Pattern careerLinkLabel = Pattern.compile("\s*(career[s]?|job[s]?|join us)\s*", Pattern.CASE_INSENSITIVE);

	public static boolean matchesCareerLink(String text) {
		text = text.trim();
		return careerLinkLabel.matcher(text).matches();
	}

	public static Pattern viewJobListingLabel = Pattern.compile("(see|view).*(job).*", Pattern.CASE_INSENSITIVE);
	
	public final static boolean matchesViewJobListingLabel(String text) {
		text = text.trim();
		return viewJobListingLabel.matcher(text).matches();
	}
	
}
