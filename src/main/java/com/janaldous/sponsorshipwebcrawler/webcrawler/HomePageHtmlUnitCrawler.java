package com.janaldous.sponsorshipwebcrawler.webcrawler;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.AjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.PageType;
import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.WebCrawlerCommand;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class HomePageHtmlUnitCrawler implements PageCrawler {

	@Override
	public WebCrawlerCommand crawl(String url) throws IOException {
		log.info("going to " + url);
		try (WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			HtmlPage page = webClient.getPage(url);
			HtmlAnchor careersAnchor = page.getAnchorByText("CAREERS");
			if (careersAnchor != null) {
				return new WebCrawlerCommand(careersAnchor.getAttribute("href"), PageType.HOMEPAGE);
			} else {
				return null;
			}
		}
	}

}
