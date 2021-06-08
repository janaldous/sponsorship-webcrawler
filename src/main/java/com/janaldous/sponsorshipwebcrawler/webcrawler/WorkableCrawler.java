package com.janaldous.sponsorshipwebcrawler.webcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.Job;
import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.WebCrawlerCommand;
import com.janaldous.sponsorshipwebcrawler.webcrawler.selenium.SeleniumConfig;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class WorkableCrawler implements PageCrawler {

	private WebDriver driver;

	public WorkableCrawler(SeleniumConfig seleniumConfig) {
		driver = seleniumConfig.getDriver();
	}

	@Override
	public WebCrawlerCommand crawl(String url) throws IOException {
		log.info("going to " + url);
		driver.navigate().to(url);

		WebElement searchField = driver.findElement(By.xpath("//input[@type='search']"));
		searchField.sendKeys("software");

		Optional<WebElement> showMoreButton = driver.findElements(By.xpath("//button[@data-ui='load-more-button']"))
				.stream().findAny();

		while (showMoreButton.isPresent()) {
			showMoreButton.ifPresent(button -> {
				log.debug("clicking on show more button");
				button.click();

				try {
					// wait for 2 seconds for AJAX response
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					log.error(e);
				}
			});

			showMoreButton = driver.findElements(By.xpath("//button[@data-ui='load-more-button']")).stream().findAny();
		}

		List<WebElement> elemJobOpenings = driver.findElements(By.xpath("//li[@data-ui='job-opening']"));
		log.info("number of job openings " + elemJobOpenings.size());

		List<Job> jobOpenings = new ArrayList<>(elemJobOpenings.size());
		
		for (int i = 0; i < elemJobOpenings.size(); i++) {
			WebElement job = elemJobOpenings.get(i);
			String jobUrl = job.findElement(By.xpath("./div/a")).getAttribute("href");
			String jobTitle = job.findElement(By.xpath("./div/h3[@data-id='job-item']")).getAttribute("innerText");
			String location = job.findElement(By.xpath("./div/div/p/span[@data-ui='job-location']")).getAttribute("innerText");
			jobOpenings.add(new Job(jobUrl, jobTitle, location));
		}

		return new WebCrawlerCommand(jobOpenings);
	}

}
