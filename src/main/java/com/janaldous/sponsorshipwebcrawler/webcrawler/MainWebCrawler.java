package com.janaldous.sponsorshipwebcrawler.webcrawler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.PageType;
import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.WebCrawlerCommand;
import com.janaldous.sponsorshipwebcrawler.webcrawler.repository.JobOpeningRespository;
import com.janaldous.sponsorshipwebcrawler.webcrawler.selenium.SeleniumConfig;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MainWebCrawler {

	private HomePageCrawler homePageCrawler;
	
	private WorkableCrawler workableCrawler;
	
	private CareersPageCrawler careersPageCrawler;
	
	private Queue<WebCrawlerCommand> queue;

	private JobOpeningRespository jobOpeningRepository;

	private SeleniumConfig seleniumConfig;
	
	public MainWebCrawler(SeleniumConfig seleniumConfig, JobOpeningRespository jobOpeningRepository) {
		homePageCrawler = new HomePageCrawler();
		this.seleniumConfig = seleniumConfig;
		careersPageCrawler = new CareersPageCrawler(seleniumConfig);
		workableCrawler = new WorkableCrawler(seleniumConfig);
		this.jobOpeningRepository = jobOpeningRepository;
		queue = new LinkedList<>();
	}
	
	public String getCareersPageUrl(String homepageUrl) {
		queue.add(new WebCrawlerCommand(homepageUrl, PageType.HOMEPAGE));
		String result = crawlLoop();
		seleniumConfig.close();
		return result;
	}
	
	@SuppressWarnings("unused")
	public String crawlLoop() {
		String careersPageUrl = null;
		while (!queue.isEmpty()) {
			WebCrawlerCommand command = queue.poll();
			try {
				WebCrawlerCommand nextCommand = crawl(command);
				if (nextCommand == null) {
					return null;
				} else if (nextCommand.getPageType() == null) {
					jobOpeningRepository.save(nextCommand.getJobOpenings());
					return null;
				} else {
					careersPageUrl= nextCommand.getUrl();
					queue.add(nextCommand);
				}
			} catch (IOException e) {
				log.error(e);
				throw new RuntimeException(e);
			}
		}
		return careersPageUrl;
	}

	public WebCrawlerCommand crawl(WebCrawlerCommand command) throws IOException {
		String url = command.getUrl();
		
		if (url.contains("apply.workable")) {
			return workableCrawler.crawl(url);
		}
		switch (command.getPageType()) {
			case HOMEPAGE:
				return homePageCrawler.crawl(url);
			case CAREERS:
				return careersPageCrawler.crawl(url);
			default:
				throw new RuntimeException("page type not recognized " + command.getPageType());
		}
	}

}
