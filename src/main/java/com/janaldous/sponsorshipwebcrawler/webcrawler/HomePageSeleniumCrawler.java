package com.janaldous.sponsorshipwebcrawler.webcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.PageType;
import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.WebCrawlerCommand;
import com.janaldous.sponsorshipwebcrawler.webcrawler.selenium.SeleniumConfig;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class HomePageSeleniumCrawler implements PageCrawler {

	private WebDriver driver;
	
	@Autowired
	public HomePageSeleniumCrawler(SeleniumConfig seleniumConfig) {
		driver = seleniumConfig.getDriver();
	}

	@Override
	public WebCrawlerCommand crawl(String url) throws IOException {
		log.info("going to " + url);
		driver.navigate().to(url);
		List<WebElement> cookiesButtons = driver.findElements(By.xpath("//button[contains(text(), 'Accept')]"));
		if (!cookiesButtons.isEmpty()) {
			Optional<WebElement> optional = cookiesButtons.stream().filter(x -> x.getAttribute("innerText").toLowerCase().contains("accept")).findAny();
			
			optional.ifPresentOrElse(button -> {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", button);
			}, () -> log.error("cannot interact with button"));
		}
		List<WebElement> careersElems = driver.findElements(By.xpath("//a[//*[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'career')]]"));
		careersElems.addAll(driver.findElements(By.xpath("//a[//*[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'jobs')]]")));
		if (careersElems.isEmpty()) {
			careersElems.addAll(driver.findElements(By.xpath("//a[//*[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'join')]]")));
		}
		
		List<String> potentialUrls = new ArrayList<>();
		
		for (WebElement elem: careersElems) {
			log.debug(elem.getAttribute("innerText"));
			if (!StringMatcherUtil.matchesCareerLink(elem.getAttribute("innerText"))) continue;
			
			if ("a".equals(elem.getTagName())) {
				log.debug(elem.getAttribute("href"));
				potentialUrls.add(elem.getAttribute("href"));
				
			}
		}
		if (!potentialUrls.isEmpty()) {
			Collections.reverse(potentialUrls);
			WebCrawlerCommand command = new WebCrawlerCommand(potentialUrls.get(0), PageType.CAREERS);
			return command;
		}
		
		return null;
	}

}
