package com.janaldous.sponsorshipwebcrawler;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.janaldous.sponsorshipwebcrawler.webcrawler.selenium.SeleniumConfig;

@SpringBootApplication
public class SponsorshipWebcrawlerApplication {

	@Autowired
	private SeleniumConfig seleniumConfig;
	
	@PreDestroy
	public void onDestroy() {
		seleniumConfig.close();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SponsorshipWebcrawlerApplication.class, args);
	}

}
