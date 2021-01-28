package com.kodilla.testing2.google;

import com.kodilla.testing2.config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

public class GoogleTestingApp {
    public static  final String SEARCHFIELD = "gLFyf gsfi";
    //public static final String RELATIVE_PATH = "//div[contains(@class,\"RveJvd snByac\")]";
    public static final String RELATIVE_PATH = "//imput[contains(@class,\"gLFyf gsfi\")]";
    public static final String ABSOLUTE_PATH = "//html/body/div/c-wiz/div/div/div/div/div/div/form/div/span";
    public static final String X_PATH_POSITION = "//span[4]";
    public static void main (String[] args){
       // System.setProperty("webdriver.chrome.driver", "C:\\gecko\\chrome\\chromedriver.exe");
        WebDriver driver = WebDriverConfig.getDriver(WebDriverConfig.FIREFOX);
        driver.get("https://www.google.pl/?gws_rd=ssl");
        driver.switchTo().frame(0);
        WebElement agree = driver.findElement(By.id("introAgreeButton"));
        agree.click();
        driver.get("https://www.google.com");

        WebElement searchField = driver.findElement(By.name("q"));
        searchField.sendKeys("Kodilla");
        searchField.submit();
    }
}
