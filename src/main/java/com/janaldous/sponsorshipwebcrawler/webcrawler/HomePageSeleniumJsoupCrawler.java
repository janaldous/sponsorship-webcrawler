package com.janaldous.sponsorshipwebcrawler.webcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.PageType;
import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.WebCrawlerCommand;
import com.janaldous.sponsorshipwebcrawler.webcrawler.selenium.SeleniumConfig;

import lombok.extern.log4j.Log4j2;
import us.codecraft.xsoup.Xsoup;

@Log4j2
public class HomePageSeleniumJsoupCrawler implements PageCrawler {

	private WebDriver driver;
	
	public HomePageSeleniumJsoupCrawler(SeleniumConfig seleniumConfig) {
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
		
		Document document = Jsoup.parse(driver.findElement(By.tagName("body")).getAttribute("outerHTML"));
		Elements careersElems = Xsoup.compile("//a[//*[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'career')]]").evaluate(document).getElements();
		careersElems.addAll(Xsoup.compile("//a[//*[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'jobs')]]").evaluate(document).getElements());
		if (careersElems.isEmpty()) {
			careersElems.addAll(Xsoup.compile("//a[//*[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'join')]]").evaluate(document).getElements());
		}
		
		List<String> potentialUrls = new ArrayList<>();
		
		for (Element elem: careersElems) {
			log.debug(elem.attr("innerText"));
			if (!StringMatcherUtil.matchesCareerLink(elem.attr("innerText"))) continue;
			
			if ("a".equals(elem.tagName())) {
				log.debug(elem.attr("href"));
				potentialUrls.add(elem.attr("href"));
				
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
