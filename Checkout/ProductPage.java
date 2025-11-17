package Checkout;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage extends BasePage{

    By searchField=By.id("search-0");
    By searchButton=By.cssSelector("button[data-analytics-name='search']");
    By addToCartButton=By.cssSelector("button[data-analytics-name='add_to_cart']");
    By closePopupButton=By.cssSelector("button[data-granify-event='click_close']");
    By imageOptionButton=By.cssSelector("button[data-analytics-name='image']");
    By textOptionButton=By.cssSelector("button[data-analytics-name='text']");
    //By.cssSelector("input[id='email']");
   
    By dismissmodel=By.cssSelector("button[data-analytics-name='dismiss_modal']");
    By personalizationButton=By.cssSelector("button.t-universal-personalization__add");
    By monogramInput=By.cssSelector("input[data-analytics-name='INPUT_Monogram']");
    By personalizationSaveButton=By.cssSelector("button.m-universal-personalization-accordion__action-save");

    WebDriverWait wait;
    Actions actions;

    public ProductPage(WebDriver driver){
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        actions = new Actions(driver);}

    public void searchProduct(String productName)throws InterruptedException{
        Thread.sleep(50);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        try{WebElement popup=wait.until(ExpectedConditions.presenceOfElementLocated(closePopupButton));
            popup.click();} 
        
         catch(Exception e){
           // System.out.println("No popup appeared"); 
        	 }
        enterText(searchField,productName);
        clickElement(searchButton);
        closePopupIfVisible(); }
  

    public void closePopupIfVisible(){
        try{ WebElement modal = wait.until(ExpectedConditions.presenceOfElementLocated(closePopupButton));
            modal.click();
        } catch (Exception e) {
           // System.out.println("No popup to close");
        }
    }

    
    public void chooseOptionsAndPersonalization(){
        try {
            List<WebElement> imageOptions=driver.findElements(imageOptionButton);
            if (!imageOptions.isEmpty()){
                actions.moveToElement(imageOptions.get(0)).click().perform();
                Thread.sleep(300);}
            
            List<WebElement> textOptions=driver.findElements(textOptionButton);
            if (!textOptions.isEmpty()){
                actions.moveToElement(textOptions.get(0)).click().perform();
                Thread.sleep(300);}

     
            List<WebElement> personalizationButtons=driver.findElements(personalizationButton);
            if (!personalizationButtons.isEmpty()) {
                WebElement addMonogramButton=personalizationButtons.get(0);
                wait.until(ExpectedConditions.elementToBeClickable(addMonogramButton));
                actions.moveToElement(addMonogramButton).perform();
                Thread.sleep(300);
                actions.click(addMonogramButton).perform();
                System.out.println("Personalization button clicked");
                WebElement inputField=wait.until(ExpectedConditions.visibilityOfElementLocated(monogramInput));
                inputField.clear();
                inputField.sendKeys("ABC");
                //System.out.println("Monogram text entered");
                WebElement saveButton=wait.until(ExpectedConditions.elementToBeClickable(personalizationSaveButton));
                actions.moveToElement(saveButton).click().perform();
                //System.out.println("Personalization added to selection");
                Thread.sleep(300);
            }else{
               //System.out.println("No personalization option available");
            }

        } catch (Exception e) {
            //System.out.println("Failed to choose options or personalization:"+ e.getMessage());
        } }

    public void addProductToCart(){
        try {chooseOptionsAndPersonalization();
         WebElement addButton=wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
            Thread.sleep(50);
            addButton.click();
           // System.out.println("Product added to cart");
          closePopupIfVisible();

        }catch (Exception e){
           // System.out.println("Failed to add product:"+e.getMessage());
        }
        
        
        try{ WebElement modal=wait.until(ExpectedConditions.presenceOfElementLocated(dismissmodel));
            modal.click(); }
        catch (Exception e){
            //System.out.println("No popup to close");
        }
       
        try{
            WebElement modal=wait.until(ExpectedConditions.presenceOfElementLocated(dismissmodel));
            modal.click();} 
        catch (Exception e){
             //System.out.println("No popup to close");
        }

        try{ WebElement modal=wait.until(ExpectedConditions.presenceOfElementLocated(dismissmodel));
            modal.click();
        }catch (Exception e){
           // System.out.println("No popup to close");
        }

        try { WebElement modal=wait.until(ExpectedConditions.presenceOfElementLocated(dismissmodel));
            modal.click();
        }catch (Exception e) {
            //System.out.println("No popup to close");
        }
    }}