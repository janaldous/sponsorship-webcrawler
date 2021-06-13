package com.janaldous.sponsorshipwebcrawler.webcrawler.selenium;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class SeleniumConfig {

	@Getter
	private WebDriver driver;

	public SeleniumConfig() {
//		FirefoxOptions firefoxOptions = new FirefoxOptions();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	static {
		System.setProperty("webdriver.chrome.driver", "C:/Users/USER/Documents/code/stuff2021-2/sponsorship-webcrawler/src/main/resources/bin/chromedriver.exe");
	}

	static private String findFile(String filename) {
		String paths[] = { "", "bin/", "target/classes" };
		for (String path : paths) {
			if (new File(path + filename).exists())
				return path + filename;
		}
		return "";
	}

	public void close() {
		driver.close();
	}

}
