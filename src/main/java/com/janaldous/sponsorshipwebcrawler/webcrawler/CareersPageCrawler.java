package com.janaldous.sponsorshipwebcrawler.webcrawler;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.PageType;
import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.WebCrawlerCommand;
import com.janaldous.sponsorshipwebcrawler.webcrawler.selenium.SeleniumConfig;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CareersPageCrawler implements PageCrawler {

	private WebDriver driver;

	public CareersPageCrawler(SeleniumConfig seleniumConfig) {
		driver = seleniumConfig.getDriver();
	}

	@Override
	public WebCrawlerCommand crawl(String url) throws IOException {
		log.info("going to " + url);
		driver.navigate().to(url);

		List<WebElement> elemSeeJobs = driver.findElements(By.xpath("//a[contains(translate(text(), 'JOB', 'job'), 'job')]"));
		elemSeeJobs.addAll(driver.findElements(By.xpath("//a[//*[contains(translate(text(), 'JOB', 'job'), 'job')]]")));
		if (elemSeeJobs.isEmpty()) {
			elemSeeJobs.addAll(driver.findElements(By.xpath("//a[contains(translate(text(), 'POSITION', 'position'), 'position')]")));
			elemSeeJobs.addAll(driver.findElements(By.xpath("//a[//*[contains(translate(text(), 'POSITION', 'position'), 'position')]]")));
		}
		
		if (elemSeeJobs.isEmpty()) {
			elemSeeJobs.addAll(driver.findElements(By.xpath("//a[contains(translate(text(), 'ROLE', 'role'), 'role')]")));
			elemSeeJobs.addAll(driver.findElements(By.xpath("//a[//*[contains(translate(text(), 'ROLE', 'role'), 'role')]]")));
		}
		
		elemSeeJobs.forEach(x -> log.debug(x.getText() + " - " + x.getAttribute("href")));
		
		Optional<WebElement> optElemSeeJobs = elemSeeJobs.stream()
				.filter(elem -> StringMatcherUtil.matchesViewJobListingLabel(elem.getText()))
				.filter(elem -> !url.equals(elem.getAttribute("href")))
				.findAny();
		
		if (optElemSeeJobs.isPresent()) {
			String urlCareers = optElemSeeJobs.get().getAttribute("href");
			return new WebCrawlerCommand(urlCareers, PageType.JOB_LISTING);
		}
		return null;
	}

}
