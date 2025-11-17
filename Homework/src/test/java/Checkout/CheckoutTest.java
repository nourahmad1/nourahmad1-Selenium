package Checkout;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckoutTest {

    @DataProvider(name = "checkoutData")
    public Object[][] checkoutData() {
        return new Object[][] {
            {"login",List.of("WN268")},
            {"login",List.of("AW164")},
            {"login",List.of("FF016KRH", "FT237", "#FM139")},
            {"guest",List.of("WN268")},
            {"guest",List.of("AW164")},
            {"guest",List.of("FF016KRH", "FT237", "#FM139")}
        };
    }

    @Test(dataProvider = "checkoutData")
    public void checkoutFlow(String userType, List<String> productCodes) throws InterruptedException {

        
    	Config.setUserType(userType);


        ChromeOptions options=new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.addArguments("--disable-blink-features=AutomationControlled");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://devwcs3.ballarddesigns.com/?aka_bypass=5C73514EE7A609054D81DE61DD9CA3D6");

        ProductPage productPage=new ProductPage(driver);
        CartPage cartPage=new CartPage(driver);
        CheckoutPage checkoutPage=new CheckoutPage(driver);
        DeliveryMethodVerifier deliveryVerifier = new DeliveryMethodVerifier(driver);
        PaymentHandler paymentHandler=new PaymentHandler(driver);

     
        if (Config.getUserType().equals("login")) {
            loginUser(driver);
        }

        
        for (String code : productCodes) {
            productPage.searchProduct(code);
            productPage.addProductToCart();
        }

        cartPage.openMiniCart();
        boolean allItemsFound = cartPage.areExpectedItemsInCart(productCodes);
        System.out.println(allItemsFound ? "All products are in the cart" : "Some products are missing");

        cartPage.goToCheckout();

       
        if (Config.getUserType().equals("guest")) {
            checkoutPage.fillGuestCheckoutForm(
                "guest@email.com",
                "John",
                "Doe",
                "123 Main St",
                "10001",
                "2125551111"
            );
        }

        System.out.println("Checkout process completed up to payment");

        boolean hasGiftProduct = true;
        System.out.println("Step Title correct? " + deliveryVerifier.verifyStepTitle(hasGiftProduct));
        System.out.println("Shipping Help displayed? " + deliveryVerifier.isShippingHelpDisplayed());
        System.out.println("Standard Delivery title correct? " + deliveryVerifier.verifyStandardDeliveryTitle());
        System.out.println("Truck Delivery title correct? " + deliveryVerifier.verifyTruckDeliveryTitle());

        deliveryVerifier.verifyAllProductDetails();
        deliveryVerifier.verifyStandardShippingOptions();
        deliveryVerifier.verifyTruckDeliveryOptions();

        if (requiresInHomeDelivery(productCodes)) {
            deliveryVerifier.updateContactPhoneNumber("1234567890");
        }

        List<WebElement> productItems = driver.findElements(By.cssSelector(".t-checkout-delivery__product-item"));
        for (WebElement product : productItems) {
            deliveryVerifier.handleGiftOptionsForProduct(product, "Happy Birthday");
        }

        deliveryVerifier.clickContinueToPayment();

       
        paymentHandler.completePayPalPayment();

        System.out.println("Checkout completed successfully for userType: " + userType + " with products: " + productCodes);

        driver.quit();
    }


    public static void loginUser(WebDriver driver) throws InterruptedException {
        driver.findElement(By.cssSelector("a[title='Account']")).click();
        driver.findElement(By.cssSelector("input[data-analytics-name='email']")).sendKeys("nourbzour65@gmail.com");
        driver.findElement(By.cssSelector("input[data-analytics-name='password']")).sendKeys("Noor@123456");
        driver.findElement(By.cssSelector("button[data-analytics-name='login']")).click();
        System.out.println("Logged-in successfully");
        Thread.sleep(1000);
    }

   
    public static boolean requiresInHomeDelivery(List<String> productCodes) {
        return productCodes.stream().anyMatch(code -> code.startsWith("#BA01"));
    }
}
