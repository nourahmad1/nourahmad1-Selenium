package Checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasePage{

public WebDriver driver;

    public BasePage(WebDriver driver){
        this.driver = driver;}

    public void clickElement(By element){
        driver.findElement(element).click();}

    public void enterText(By element,String text) {
        driver.findElement(element).sendKeys(text);
    }

    public String getElementText(By element) {
        return driver.findElement(element).getText();
    }

    public void closeBrowser() {
        if (driver != null) {
            driver.quit();  } }}