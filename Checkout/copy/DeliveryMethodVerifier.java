package Checkout.copy;

import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration; import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.ElementClickInterceptedException;

public class DeliveryMethodVerifier extends BasePage{
  WebDriverWait wait;

 public DeliveryMethodVerifier(WebDriver driver){
        super(driver);
        wait=new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public boolean verifyStepTitle(boolean hasGiftProduct){
        List<WebElement> elements = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector(".c-checkout-accordion__header-step-text")));
        if(elements.size()<2){
            System.out.println(" Could not find the second step title element");
            return false;}

        WebElement secondElement=elements.get(1);
        String titleText = secondElement.getText().trim();

        if(hasGiftProduct){
            return titleText.equalsIgnoreCase("Delivery Method & Gift");
        }else {
            return titleText.equalsIgnoreCase("Delivery Method");
        }
    }

   
    public boolean isShippingHelpDisplayed(){
        try {
            WebElement helpButton = driver.findElement(By.cssSelector("button[data-analytics-name='shipping-help']"));
            return helpButton.isDisplayed();
        }catch (NoSuchElementException e1){
            try{
                WebElement truckHelpButton=driver.findElement(By.cssSelector("button[data-analytics-name='truck-delivary-help']"));
                return truckHelpButton.isDisplayed();
            }catch (NoSuchElementException e2) {
                return false;
            } } }

    public boolean verifyStandardDeliveryTitle(){
        try {
            WebElement title = driver.findElement(By.cssSelector(".t-checkout-standard-delivery__title"));
            return title.getText().contains("Standard Delivery");
        } catch (NoSuchElementException e){
            return false;   } }

    
    public boolean verifyTruckDeliveryTitle(){
        try{ WebElement title = driver.findElement(By.cssSelector(".t-checkout-truck-delivery__title"));
           return title.getText().contains("Truck Delivery");
        }catch (NoSuchElementException e) {
            return false; } }

    public void verifyDeliveryProducts(){
        List<WebElement> productItems = driver.findElements(By.cssSelector(".t-checkout-delivery__product-item"));

        for(WebElement product : productItems){
            WebElement image = product.findElement(By.cssSelector(".c-image.c-order-product-item__image"));
            assert image.isDisplayed();
            WebElement rightContainer = product.findElement( By.cssSelector(".c-checkout-delivery__product-card-right-container"));
            assert rightContainer.isDisplayed();
            WebElement nameLink = rightContainer.findElement(By.cssSelector("a.c-link.c-order-product-item_title.u-p2"));
            WebElement nameDiv = nameLink.findElement(By.tagName("div"));
            assert nameLink.getAttribute("href").contains(nameDiv.getText());

            List<WebElement> priceParts=rightContainer.findElements( By.cssSelector(".price__currency, .price__integer, .price__decimals"));
            assert !priceParts.isEmpty();

            WebElement qty=rightContainer.findElement(By.cssSelector(".c-checkout-delivery__product-card-quantity-value.border-0"));
            assert qty.isDisplayed();
        } }

   
    public void verifyStandardShippingOptions(){
        List<WebElement> productItems = driver.findElements(By.cssSelector(".t-checkout-delivery__product-item")
        );

        for (WebElement product:productItems) {
            try{
                
                WebElement dropdownBtn = product.findElement( By.cssSelector("button[data-analytics-name='shipping_method']")
                );

              
                List<WebElement> options = product.findElements( By.cssSelector(".c-custom-selectbox-multiple-content button[data-analytics-name='save-shipping-method']")
                );

                for (int i=0;i<options.size();i++){
                    
                 wait.until(ExpectedConditions.elementToBeClickable(dropdownBtn)).click();

                    
                    WebElement menu = wait.until(
                            ExpectedConditions.visibilityOf(  product.findElement(By.cssSelector(".c-custom-selectbox-multiple-content.show"))) );

                    
                    List<WebElement> currentOptions = menu.findElements(
                            By.cssSelector("button[data-analytics-name='save-shipping-method']")
                    );

                    WebElement optionBtn = currentOptions.get(i);
       wait.until(ExpectedConditions.elementToBeClickable(optionBtn)).click();

                 Thread.sleep(500);
                }

            } catch (Exception e) {
                System.out.println("⚠️Error verifying shipping options: " + e.getMessage());
            }
        }
    }


    private void clickWithRetry(WebElement element, Actions actions) throws InterruptedException {
        int attempts = 0;
        while (attempts < 3) {
            try {
                actions.moveToElement(element).click().perform();
                return;
            } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
                Thread.sleep(300);
                attempts++;
            }
        }
        throw new RuntimeException("Unable to click element after multiple attempts: " + element);
    }

    
    public void verifyTruckDeliveryOptions() {
        List<WebElement> productItems = driver.findElements(By.cssSelector(".t-checkout-delivery__product-item"));

        for (WebElement product : productItems) {
            List<WebElement> truckOptions = product.findElements(By.cssSelector(".truck-delivery-option-title"));

            for (WebElement option : truckOptions) {
                try {
                    WebElement radio = option.findElement(By.cssSelector("input[type='radio'][data-analytics-name='gift-option']"));
                    WebElement label = option.findElement(By.tagName("label"));
                    radio.click();
                    Thread.sleep(300);
                } catch (Exception e) {
                    System.out.println(" Missing radio or label in truck delivery option.");
                }
            }
        }
    }

 
    public void updateContactPhoneNumber(String newPhoneNumber) {
        try {
            WebElement editBtn = driver.findElement(By.cssSelector("button[analytics-name='c-delivery__edit-phone']"));
            editBtn.click();

            WebElement phoneInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("input[data-analytics-name='phone']")));
            phoneInput.clear();
            phoneInput.sendKeys(newPhoneNumber);

            WebElement saveBtn = driver.findElement(By.cssSelector("button[analytics-name='c-delivery__save-phone-number']"));
            saveBtn.click();
        } catch (Exception e) {
            System.out.println("Could not update contact phone: " + e.getMessage());
        }
    }

   
    public void handleGiftOptionsForProduct(WebElement product, String giftMessage) {
        try {
            List<WebElement> giftButtons = product.findElements(By.cssSelector("button[data-analytics-name='gift']"));
            if (!giftButtons.isEmpty()) {
                WebElement giftBtn = giftButtons.get(0);
                giftBtn.click();

                WebElement messageBox = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.cssSelector("textarea[data-analytics-name='gift-message']")));
                messageBox.clear();
                messageBox.sendKeys(giftMessage);

                WebElement saveBtn = driver.findElement(By.cssSelector("button[data-analytics-name='save-gift-wrap']"));
                saveBtn.click();
            }
        } catch (Exception e) {
            System.out.println("Could not handle gift options: " + e.getMessage());
        }
    }

   
    public void verifyAllProductDetails() {
        List<WebElement> products = driver.findElements(By.cssSelector(".t-checkout-delivery__product-item"));

        for (WebElement product : products) {
            try {
                WebElement img = product.findElement(By.cssSelector("img.c-order-product-item__image"));
                System.out.println("Image: " + img.getAttribute("src"));

                WebElement titleLink = product.findElement(By.cssSelector("a.c-order-product-item_title div"));
                String productName = titleLink.getText().trim();
                System.out.println("Title: " + productName);

                // Prices
                List<WebElement> originalPrice = product.findElements(By.cssSelector(".c-universal-price-new .price__integer, .c-universal-price-new .price__decimals"));
                if (!originalPrice.isEmpty()) {
                    StringBuilder op = new StringBuilder();
                    for (WebElement p : originalPrice) op.append(p.getText());
                    System.out.println("Original Price: $" + op);
                }

                List<WebElement> discountedPrice = product.findElements(By.cssSelector(".c-universal-price-promo .price__integer, .c-universal-price-promo .price__decimals"));
                if (!discountedPrice.isEmpty()) {
                    StringBuilder dp = new StringBuilder();
                    for (WebElement p : discountedPrice) dp.append(p.getText());
                    System.out.println("Discounted Price: $" + dp);
                }

                WebElement qty = product.findElement(By.cssSelector(".c-checkout-delivery__product-card-quantity-value"));
                System.out.println("Quantity: " + qty.getText());

                WebElement availability = product.findElement(By.cssSelector(".c-availability-message.u-p4"));
                System.out.println("Availability: " + availability.getText());

               
                List<WebElement> promoMsg = product.findElements(By.cssSelector(".c-delivery__promotion-container span"));
                if (!promoMsg.isEmpty()) System.out.println("Promotion: " + promoMsg.get(0).getText());

                List<WebElement> giftBtns = product.findElements(By.cssSelector("button[data-analytics-name='gift']"));
                System.out.println(giftBtns.isEmpty() ? "No gift option" : "Gift option available");

              
                List<WebElement> truckOptions = product.findElements(By.cssSelector(".truck-delivery-option"));
                for (WebElement option : truckOptions) {
                    WebElement label = option.findElement(By.tagName("label"));
                    System.out.println("Truck Delivery Option: " + label.getText());

                    List<WebElement> descriptions = option.findElements(By.cssSelector(".truck-description-list li"));
                    for (WebElement desc : descriptions) System.out.println("  - " + desc.getText());
                }

            
                List<WebElement> returnMsg = product.findElements(By.cssSelector(".c-checkout-delivery__unapproved-returns__msg a.unapproved-returns-msg-link"));
                if (!returnMsg.isEmpty()) {
                    String msgText = returnMsg.get(0).getText().trim();
                    if (msgText.equalsIgnoreCase("This item cannot be returned")) {
                        System.out.println("Message verified for personalized product");
                    } else {
                        System.out.println("Unexpected return message: " + msgText);
                    }
                } else {
                    System.out.println(" No return message found for this produc.");
                }

                System.out.println("--------------------------------------------------");

            } catch (Exception e) {
                System.out.println("⚠️Error verifying product details: " + e.getMessage());
            }
        }
        
        
    }
 
    public void clickContinueToPayment() {
        try {
            By continueButton = By.cssSelector("button#nextBtn[data-analytics-name='next']");
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
            button.click();
            System.out.println("Clicked 'Continue to Payment' button.");
        } catch (Exception e) {
            System.out.println("⚠️ ould not click 'Continue to Payment': " + e.getMessage());
        }
    }


    }