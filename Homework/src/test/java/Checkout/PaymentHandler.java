package Checkout;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaymentHandler extends BasePage {

    private WebDriverWait wait;
    private Actions actions;

    private String paypalEmail = "funkoqa@gmail.com";      
    private String paypalPassword = "P@ssw0rd.123";        

    public PaymentHandler(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        this.actions = new Actions(driver);
    }

   
    public void selectPayPal() {
        try {
            By paypalLabelSelector = By.cssSelector("label[for='paypal-tab']");
            WebElement paypalLabel = wait.until(ExpectedConditions.elementToBeClickable(paypalLabelSelector));
            paypalLabel.click();
            System.out.println("PayPal option selected successfully");
        } catch (Exception e) {
            System.out.println(" Failed to select PayPal option: " + e.getMessage());
        }
    }


    public String clickPayPalButton() {
        try {
            WebElement paypalFrame = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("iframe[src*='paypal'], iframe.component-frame.visible")
                )
            );

            driver.switchTo().frame(paypalFrame);

            WebElement paypalBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button[type='submit'], div[data-funding-source]")
                )
            );

            paypalBtn.click();
            System.out.println("PayPal button clicked - popup opening...");

            driver.switchTo().defaultContent();

        } catch (Exception e) {
            System.out.println("Failed to click PayPal button: " + e.getMessage());
        }

        return driver.getWindowHandle();
    }

   
    public void switchToPayPalPopup(String mainWindow) {
        wait.until(driver -> driver.getWindowHandles().size() > 1);

        for (String window : driver.getWindowHandles()) {
            if (!window.equals(mainWindow)) {
                driver.switchTo().window(window);
                System.out.println("Switched to PayPal popup window");
                break;
            }
        }
    }

 
    public void loginAndPay() {
        try {
            WebElement emailInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("/html/body/div[1]/section[1]/div/div[2]/div[1]/div/form/div[3]/div[1]/div[2]/div[1]/input")
                )
            );
            emailInput.sendKeys(paypalEmail);

            WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnNext")));
            nextBtn.click();

         
            WebElement passwordInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("input[type='password']")
                )
            );
            passwordInput.sendKeys(paypalPassword);

            WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnLogin")));
            loginBtn.click();

            WebElement payBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(
                        By.cssSelector("button[data-id='payment-submit-btn']")
                    )
            );
            payBtn.click();

            System.out.println("PayPal final 'Complete Purchase' button clicked successfully");

        } catch (Exception e) {
            System.out.println("Error during PayPal login/payment: " + e.getMessage());
        }
    }

   
    public void returnToMainWindow(String mainWindow) {
        driver.switchTo().window(mainWindow);
        System.out.println(" Returned to main checkout window");
    }

    public void clickLogoToHome() {
        try {
            WebElement logo = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.cssSelector("a[title='Ballard Designs LOGO']")
                )
            );

      
            logo.click();
            System.out.println("Clicked on the logo, navigated to home page");

        } catch (Exception e) {
            System.out.println("Failed to click logo: " + e.getMessage());
        }
    }

    
    public void completePayPalPayment() {
        selectPayPal();                  
        String mainWindow = clickPayPalButton(); 
        switchToPayPalPopup(mainWindow); 
        loginAndPay();                   
        returnToMainWindow(mainWindow);   
     //   clickLogoToHome();                
    }
}