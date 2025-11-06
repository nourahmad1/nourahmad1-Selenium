package TestNGHomework2;
	import static org.testng.Assert.assertEquals;
    import java.time.Duration;
	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.chrome.ChromeOptions;
	import org.openqa.selenium.edge.EdgeDriver;
	import org.testng.Assert;
	import org.testng.annotations.AfterClass;
	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.Test;

public class BallardDesignsTest{
		
		  WebDriver driver;
		    @BeforeClass
		    public void setUp(){
		        ChromeOptions options=new ChromeOptions();
		        options.setExperimentalOption("excludeSwitches",new String[]{"enable-automation"});
		        options.addArguments("--disable-blink-features=AutomationControlled");
		        driver=new ChromeDriver(options);
		        driver.manage().window().maximize();
		        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
                driver.get("https://www.ballarddesigns.com/");
		    }
          @Test(priority=1)
		    public void verifyLogoIsDisplayed(){
		        boolean logoDisplayed=driver.findElement(By.cssSelector("a[title='Ballard Designs LOGO']")).isDisplayed();
		        Assert.assertTrue(logoDisplayed,"Logo should be displayed on the homepage");}

		    @Test(priority=2)
		    public void clickAccountButton() {
		        try {
		            driver.findElement(By.xpath("//*[@id=\"app-header\"]/header/div[3]/div/div[4]/div[3]/a")).click();
		            System.out.println("Account button clicked successfully.");
		        } catch (Exception e){
		            System.out.println("Account button not found or not clickable.");
		        }}

		    @Test(priority=3,dependsOnMethods="clickAccountButton")
		    public void verifyMessage() throws InterruptedException {
		        Thread.sleep(2000);
		        String actualText = driver.findElement(By.xpath("//*[@id=\"BDLoginMessageNormal\"]/p[1]")).getText();
		        String expectedText = "Welcome back! To access your account, please enter your email address and password and click Sign In.";
		        System.out.println("Displayed Message: " + actualText);
		        Assert.assertEquals(actualText, expectedText,"message should match");}

		    @Test(priority=4,dependsOnMethods ="clickAccountButton")
		    public void loginToAccount() {
		        driver.findElement(By.cssSelector("input[data-analytics-name='email']")).sendKeys("nourbzour65@gmail.com");
		        driver.findElement(By.cssSelector("input[data-analytics-name='password']")).sendKeys("Noor@123456");
		        driver.findElement(By.cssSelector("button[data-analytics-name='login']")).click();
                System.out.println("Login attempted with provided credentials.");
		    }
		    @AfterClass
		    public void Down() {
		             driver.quit();
		            System.out.println("Browser closed successfully."); }

	}
