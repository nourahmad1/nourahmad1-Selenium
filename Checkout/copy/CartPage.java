package Checkout.copy;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage extends BasePage{

     By miniCartButton=By.cssSelector("button[data-analytics-name='show_mini_cart']");
     By cartHeaderText=By.cssSelector("span.t-cart__header-text.t-cart__header-estimated-text");
    By productCodeElements=By.cssSelector(".c-product-details__item-number");
    By checkoutButtons=By.cssSelector("button.c-checkout-buttons__checkout");

    public CartPage(WebDriver driver){
        super(driver);}

    public void openMiniCart(){
        try{
            driver.findElement(miniCartButton).click();
            driver.findElement(miniCartButton).click();
            //System.out.println("Mini cart opened.");
        } catch (Exception e) {
            //System.out.println("Failed to open mini cart");
        }}

    public boolean isOnCartPage(){
        try{
            WebElement header=driver.findElement(cartHeaderText);
            return header.getText().contains("Shopping Cart");}
        catch (Exception e){
            return false;
        }
    }

    public boolean areExpectedItemsInCart(List<String> expectedProductCodes){
        boolean allFound = true;
        List<WebElement> items=driver.findElements(productCodeElements);

        for(String expectedCode:expectedProductCodes){
            boolean found = items.stream()
                .anyMatch(el->el.getText().replace("#","").trim().equalsIgnoreCase(expectedCode.replace("#","").trim()));
            if (!found){
                allFound = false; } }
        return allFound; }

    public void goToCheckout(){
        try{
            List<WebElement> buttons=driver.findElements(checkoutButtons);
            if(buttons.size()>1){
                buttons.get(1).click();
                //System.out.println("Clicked the second checkout button");
            }else if(!buttons.isEmpty()){
                buttons.get(0).click();
                //System.out.println("Clicked the first checkout button");
            }else{
                //System.out.println("No checkout button found");
            }
        }catch(Exception e){
            System.out.println("Error while trying to go to checkout: "+e.getMessage());
            }}
}