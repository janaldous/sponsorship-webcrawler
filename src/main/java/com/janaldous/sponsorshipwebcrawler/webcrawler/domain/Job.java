package com.janaldous.sponsorshipwebcrawler.webcrawler.domain;

import lombok.Value;

@Value
public class Job {

	private String url;
	private String title;
	private String location;
	
}
