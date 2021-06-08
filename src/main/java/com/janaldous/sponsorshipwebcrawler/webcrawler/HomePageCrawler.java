package com.janaldous.sponsorshipwebcrawler.webcrawler;

import java.io.IOException;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.PageType;
import com.janaldous.sponsorshipwebcrawler.webcrawler.domain.WebCrawlerCommand;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class HomePageCrawler implements PageCrawler {

	public static Pattern careerLinkLabel = Pattern.compile("\s*(career[s]?|job[s]?)\s*", Pattern.CASE_INSENSITIVE);
	
	@Override
	public WebCrawlerCommand crawl(String url) throws IOException {
		log.info("going to " + url);
		Document document = Jsoup.connect(url).get();
		log.info("document fetched");
		Elements linksOnPage = document.getElementsMatchingText(careerLinkLabel);
		// reverse so that it goes to specific element first before document
		Collections.reverse(linksOnPage);

		for (Element elemPotentialLink : linksOnPage) {
			Element elemLink = findNearestAnchor(elemPotentialLink);
			if (elemLink == null) continue;
			
			log.info("found anchor link");
			
			Matcher labelMatch = careerLinkLabel.matcher(elemLink.text());
			if (labelMatch.matches()) {
				if ("a".equals(elemLink.tagName())) {
					String urlStr = elemLink.attr("abs:href");
					if (urlStr != null) return new WebCrawlerCommand(urlStr, PageType.CAREERS);
				}
			}
		}
		return null;
	}


	/**
	 * Finds nearest element that is an anchor (up to 3 levels of hierarchy including itself)
	 * 
	 * @param elem
	 * @return
	 */
	private Element findNearestAnchor(Element elem) {
		if (elem == null) return null;
		for (int i = 0; i < 3; i++) {
			if (elem.tagName().equals("#root")) return null;
			if ("a".equals(elem.tagName())) {
				return elem;
			}
			elem = elem.parent();
		}
		return null;
	}
	
}
