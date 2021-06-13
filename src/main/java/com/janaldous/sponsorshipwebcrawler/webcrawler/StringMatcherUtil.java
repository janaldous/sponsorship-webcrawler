package com.janaldous.sponsorshipwebcrawler.webcrawler;

import java.util.regex.Pattern;

public class StringMatcherUtil {

	public final static Pattern careerLinkLabel = Pattern.compile("(career[s]?|job[s]?|join( us)?).*", Pattern.CASE_INSENSITIVE);

	public static boolean matchesCareerLink(String text) {
		text = text.trim();
		return careerLinkLabel.matcher(text).matches();
	}

	public static Pattern viewJobListingStartsWithVerb = Pattern.compile("(see|view|current|check).*(job|position|vacancies|role)[s]?.*", Pattern.CASE_INSENSITIVE);
	
	public final static boolean matchesViewJobListingLabel(String text) {
		text = text.trim();
		boolean match = viewJobListingStartsWithVerb.matcher(text).matches();
		if (!match) return matchesViewJobListingLabel2(text);
		return match;
	}
	
	public static Pattern viewJobListingNoun = Pattern.compile("job( (search|openings))?.*", Pattern.CASE_INSENSITIVE);
	
	private final static boolean matchesViewJobListingLabel2(String text) {
		text = text.trim();
		return viewJobListingNoun.matcher(text).matches();
	}
	
}
