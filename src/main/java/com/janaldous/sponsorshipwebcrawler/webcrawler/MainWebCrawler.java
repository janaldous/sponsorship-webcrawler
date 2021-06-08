package com.janaldous.sponsorshipwebcrawler.webcrawler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.PageType;
import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.WebCrawlResult;
import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.WebCrawlerCommand;
import com.janaldous.sponsorshipwebcrawler.webcrawler.repository.JobOpeningRespository;
import com.janaldous.sponsorshipwebcrawler.webcrawler.selenium.SeleniumConfig;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MainWebCrawler {

	private HomePageSeleniumCrawler homePageCrawler;
	
	private WorkableCrawler workableCrawler;
	
	private CareersPageCrawler careersPageCrawler;
	
	private Queue<WebCrawlerCommand> queue;

	private JobOpeningRespository jobOpeningRepository;

	private SeleniumConfig seleniumConfig;

	private JobListingWebCrawler jobListingPageCrawler;
	
	public MainWebCrawler(SeleniumConfig seleniumConfig, JobOpeningRespository jobOpeningRepository) {
		this.seleniumConfig = seleniumConfig;
		this.homePageCrawler = new HomePageSeleniumCrawler(seleniumConfig);
		this.careersPageCrawler = new CareersPageCrawler(seleniumConfig);
		this.workableCrawler = new WorkableCrawler(seleniumConfig);
		this.jobListingPageCrawler = new JobListingWebCrawler();
		this.jobOpeningRepository = jobOpeningRepository;
		this.queue = new LinkedList<>();
	}
	
	public WebCrawlResult getCareersPageUrl(String homepageUrl) {
		queue.add(new WebCrawlerCommand(homepageUrl, PageType.HOMEPAGE));
		WebCrawlResult result = crawlLoop();
		return result;
	}
	
	private WebCrawlResult crawlLoop() {
		WebCrawlerCommand lastCommand = null;
		while (!queue.isEmpty()) {
			lastCommand = queue.poll();
			try {
				WebCrawlerCommand nextCommand = crawl(lastCommand);
				if (nextCommand == null) {
					return new WebCrawlResult(lastCommand.getUrl());
				}
				log.info("next url " + nextCommand.getUrl());
				if (nextCommand.getPageType() == null) {
					if (nextCommand.getJobOpenings() != null) {
						jobOpeningRepository.save(nextCommand.getJobOpenings());
					}
					return new WebCrawlResult(lastCommand.getUrl());
				} else {
					queue.add(nextCommand);
				}
			} catch (IOException e) {
				log.error(e);
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	private WebCrawlerCommand crawl(WebCrawlerCommand command) throws IOException {
		String url = command.getUrl();
		
		if (url.contains("apply.workable")) {
			return workableCrawler.crawl(url);
		}
		switch (command.getPageType()) {
			case HOMEPAGE:
				return homePageCrawler.crawl(url);
			case CAREERS:
				return careersPageCrawler.crawl(url);
			case JOB_LISTING:
				return jobListingPageCrawler.crawl(url);
			default:
				throw new RuntimeException("page type not recognized " + command.getPageType());
		}
	}

}
