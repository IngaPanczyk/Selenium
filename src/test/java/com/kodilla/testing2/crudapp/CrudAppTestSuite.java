package com.kodilla.testing2.crudapp;

import com.kodilla.testing2.config.WebDriverConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;

public class CrudAppTestSuite {
    public static final String BASE_URL = "https://ingapanczyk.github.io";
    private WebDriver driver;
    private Random generator;

    @Before
    public void initTests() {
        driver = WebDriverConfig.getDriver(WebDriverConfig.FIREFOX);
        driver.get("https://www.google.pl/?gws_rd=ssl");
        driver.switchTo().frame(0);
        WebElement agree = driver.findElement(By.id("introAgreeButton"));
        agree.click();

        driver.get(BASE_URL);
        generator = new Random();
    }

    @After
    public void cleanUpAfterTest() {
        driver.close();
    }

    private void sendTestTaskToTrello(String taskName) throws InterruptedException {
        driver.navigate().refresh();

        while (!driver.findElement(By.xpath("//select[1]")).isDisplayed()) ;

        driver.findElements(By.xpath("//form[@class=\"datatable__row\"]")).stream()
                .filter(anyForm ->
                        anyForm.findElement(By.xpath(".//p[@class=\"datatable__field-value\"]"))
                                .getText().equals(taskName))
                .forEach(theForm -> {
                    WebElement selectElememnt = theForm.findElement(By.xpath(".//select[1]"));
                    Select select = new Select(selectElememnt);
                    select.selectByIndex(3);

                    WebElement buttonCreateCard =
                            theForm.findElement(By.xpath(".//button[contains(@class, \"card-creation\")]"));
                    buttonCreateCard.click();
                });
        Thread.sleep(5000);
        driver.switchTo().alert().accept();
    }

    private String createCrudAppTestTask() throws InterruptedException {
        final String XPATH_TASK_NAME = "//form[contains(@action, \"createTask\")]/fieldset[1]/input";
        final String XPATH_TASK_CONTENT = "//form[contains(@action, \"createTask\")]/fieldset[2]/textarea";
        final String XPATH_ADD_BUTTON = "//form[contains(@action, \"createTask\")]/fieldset[3]/button";
        String taskName = "Task number " + generator.nextInt(100000);
        String taskContent = taskName + " content";

        WebElement name = driver.findElement(By.xpath(XPATH_TASK_NAME));
        name.sendKeys(taskName);

        WebElement content = driver.findElement(By.xpath(XPATH_TASK_CONTENT));
        content.sendKeys(taskContent);

        WebElement addButton = driver.findElement(By.xpath(XPATH_ADD_BUTTON));
        addButton.click();
        Thread.sleep(2000);
        return taskName;
    }

    private boolean checkTaskExistsInTrello(String taskName) throws InterruptedException {
        final String TRELLO_URL = "https://trello.com/login";
        boolean result = false;
        WebDriver driverTrello = WebDriverConfig.getDriver(WebDriverConfig.FIREFOX);
        driverTrello.get("https://www.google.pl/?gws_rd=ssl");
        driverTrello.switchTo().frame(0);
        WebElement agree = driverTrello.findElement(By.id("introAgreeButton"));
        agree.click();
        driverTrello.get(TRELLO_URL);

        driverTrello.findElement(By.id("user")).sendKeys("ingawaaa@gmail.com");
        driverTrello.findElement(By.id("password")).sendKeys("Stas2019");
        WebElement el = driverTrello.findElement(By.id("login"));
        el.submit();

        Thread.sleep(4000);

        driverTrello.findElement(By.id("password")).sendKeys("Stas2019");
        driverTrello.findElement(By.id("login-submit")).submit();

        Thread.sleep(10000);

        driverTrello.findElements(By.xpath("//a[@class=\"board-tile\"]")).stream()
                .filter(aHref -> aHref.findElements(By.xpath(".//div[@title=\"Kodilla Application\"]")).size() > 0)
                .forEach(WebElement::click);

        Thread.sleep(6000);

        result = driverTrello.findElements(By.xpath("//span[@class=\"list-card-title js-card-name\"]")).stream()
                .anyMatch(theSpan -> {
                    System.out.println(theSpan.getText());
                    return theSpan.getText().equals(taskName);
                });

        driverTrello.close();

        return result;

    }

    private void shouldDeleteTrelloCard(String taskName) throws InterruptedException {

        WebDriver driverTrello = WebDriverConfig.getDriver(WebDriverConfig.FIREFOX);
        driverTrello.get("https://www.google.pl/?gws_rd=ssl");
        driverTrello.switchTo().frame(0);
        WebElement agree = driverTrello.findElement(By.id("introAgreeButton"));
        agree.click();
        driverTrello.get(BASE_URL);

        Thread.sleep(10000);
        driver.findElements(By.xpath("//form[@class=\"datatable__row\"]")).stream()
                .filter(anyForm ->
                        anyForm.findElement(By.xpath(".//p[@class=\"datatable__field-value\"]"))
                                .getText().equals(taskName))
                .forEach(theForm -> {
                    WebElement buttonDeleteCard =
                            theForm.findElement(By.xpath(".//button[contains(@class,\"datatable__button\")][4]"));
                    buttonDeleteCard.click();
                });
        Thread.sleep(5000);
    }

    @Test
    public void shouldCreateTrelloCard() throws InterruptedException {
        String taskName = createCrudAppTestTask();
        sendTestTaskToTrello(taskName);
        assert (checkTaskExistsInTrello(taskName));
    }


    @Test
    public void deleteTrelloCard() throws InterruptedException {
        String taskName = createCrudAppTestTask();
        shouldDeleteTrelloCard(taskName);

    }
}
