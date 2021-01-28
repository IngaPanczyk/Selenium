package com.kodilla.testing2.ebay;

import com.kodilla.testing2.config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EBayTestingApp {
    public static  final String SEARCHFIELD = "gh-ac";

    public static void main (String [] args){

        WebDriver driver = WebDriverConfig.getDriver(WebDriverConfig.FIREFOX);

        driver.get("https://www.google.pl/?gws_rd=ssl");
        driver.switchTo().frame(0);
        WebElement agree = driver.findElement(By.id("introAgreeButton"));
        agree.click();

        driver.get("https://www.ebay.com/");

        WebElement searchField = driver.findElement(By.id(SEARCHFIELD));
        searchField.sendKeys("Laptop");
        searchField.submit();
    }
}
