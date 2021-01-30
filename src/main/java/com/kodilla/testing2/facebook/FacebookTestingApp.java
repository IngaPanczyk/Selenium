package com.kodilla.testing2.facebook;

import com.kodilla.testing2.config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FacebookTestingApp {
    //public static  final String ACCEPT_COOKIE = "u_0_h";
    public static final String XPATH_ACCEPT_COOKIE = "//div[contains(@class, \"9fiw\")]/button[2]";
    public static final String XPATH_NEW_ACCOUNT = "//div[contains(@class, \"6ltg\")]/a";
    public static final String ID_FIRSTNAME = "u_2_b";
    public static final String ID_LASTNAME = "u_2_d";
    public static final String ID_NUMBER = "u_2_g";
    public static final String ID_SEX_FEMALE = "u_2_2";
    public static final String ID_WEB_SUBMIT = "u_2_s";
    public static final String ID_PREFERED_PRONOUN = "js_0";
    public static final String ID_PASSWORD = "password_step_input";
    public static final String XPATH_DAY = "//div[contains(@class, \"5k_5\")]/span/span/select[1]";
    public static final String XPATH_MONTH = "//div[contains(@class, \"5k_5\")]/span/span/select[2]";
    public static final String XPATH_YEAR = "//div[contains(@class, \"5k_5\")]/span/span/select[3]";

    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = WebDriverConfig.getDriver(WebDriverConfig.FIREFOX);

        driver.get("https://www.google.pl/?gws_rd=ssl");
        driver.switchTo().frame(0);
        WebElement agree = driver.findElement(By.id("introAgreeButton"));
        agree.click();

        driver.get("https://www.facebook.com/");

        WebElement acceptCookie = driver.findElement(By.xpath(XPATH_ACCEPT_COOKIE));
        acceptCookie.click();

        WebElement newAccount = driver.findElement(By.xpath(XPATH_NEW_ACCOUNT));
        newAccount.click();

        Thread.sleep(1000);

        WebElement firstname = driver.findElement(By.id(ID_FIRSTNAME));
        firstname.sendKeys("Jan");

        WebElement lastname = driver.findElement(By.id(ID_LASTNAME));
        lastname.sendKeys("Kowalski");

        WebElement number = driver.findElement(By.id(ID_NUMBER));
        number.sendKeys("789789021");

        WebElement password = driver.findElement(By.id(ID_PASSWORD));
        password.sendKeys("123456789@ZosiaJanek");

        Select day = new Select(driver.findElement(By.xpath(XPATH_DAY)));
        day.selectByIndex(2);

        Select month = new Select(driver.findElement(By.xpath(XPATH_MONTH)));
        month.selectByIndex(2);

        Select year = new Select(driver.findElement(By.xpath(XPATH_YEAR)));
        year.selectByValue("1934");

        WebElement sex = driver.findElement(By.id(ID_SEX_FEMALE));
        sex.click();

        WebElement webSumbit = driver.findElement(By.id(ID_WEB_SUBMIT));
        webSumbit.click();
    }
}
