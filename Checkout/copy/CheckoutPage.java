package Checkout.copy;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends BasePage {
	By nextButton=By.cssSelector("button[data-analytics-name='next']");
	By continueAsGuestButton=By.cssSelector("button[data-analytics-name='continue-as-guest']");
 By emailFieldLocator=By.cssSelector("input[id='checkout_step1_email']");
 By firstNameFieldLocator=By.cssSelector("input[data-analytics-name='first_name']");
 By lastNameFieldLocator=By.cssSelector("input[data-analytics-name='last_name']");
 By streetFieldLocator=By.cssSelector("input[data-analytics-name='street_address']");
 By zipFieldLocator=By.cssSelector("input[data-analytics-name='zip_postal_code']");
 
 By phoneFieldLocator=By.cssSelector("input[data-analytics-name='phone']");
 By shippingNextButton=By.cssSelector("button[data-analytics-name='next']");
 

   WebDriverWait wait;

    public CheckoutPage(WebDriver driver){
        super(driver);
        wait=new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void fillGuestCheckoutForm(String email,String firstName,String lastName,
                                      String street,String zip,String phone)throws InterruptedException{

    	if(Config.getUserType().equalsIgnoreCase("guest")){
            wait.until(ExpectedConditions.elementToBeClickable(continueAsGuestButton)).click();
           // System.out.println("Continue as Guest clicked");
            Thread.sleep(500);
       
    	if(Config.getUserType().equalsIgnoreCase("guest")){
        Thread.sleep(5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailFieldLocator)).sendKeys(email);
        Thread.sleep(15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameFieldLocator)).sendKeys(firstName);
        Thread.sleep(3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameFieldLocator)).sendKeys(lastName);
        Thread.sleep(3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(streetFieldLocator)).sendKeys(street);
        Thread.sleep(3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(zipFieldLocator)).sendKeys(zip);
        Thread.sleep(3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(phoneFieldLocator)).sendKeys(phone);
        Thread.sleep(3);} }
        wait.until(ExpectedConditions.elementToBeClickable(shippingNextButton)).click();
        Thread.sleep(3);
        System.out.println("Shipping Next clicked.");

      /*  wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
        Thread.sleep(3);
        System.out.println("Next clicked, proceeding to payment.");*/
    }
}