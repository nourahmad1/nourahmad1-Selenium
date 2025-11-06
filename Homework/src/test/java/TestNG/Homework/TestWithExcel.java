package TestNG.Homework;



import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestWithExcel {

    WebDriver driver;
    String excelPath = "src/test/java/Book 2.xlsx"; 

    @BeforeTest
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.addArguments("--disable-blink-features=AutomationControlled");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

  
        driver.get("https://www.frontgate.com/ShoppingCartView");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
            driver.findElement(By.cssSelector("button[data-analytics-name='navigate_back']")).click();
        } 
        catch (Exception e) {
            System.out.println("Navigate back button not found, continuing...");
        }
    }

    @Test
    public void loginUsingExcel() throws Exception {
        ExcelUtils.setExcelFile(excelPath, "Sheet1");
        int rown=ExcelUtils.getRowCount();

        for (int i = 1; i < rown; i++) {
            String email=ExcelUtils.getCellData(i, 0);
            String password=ExcelUtils.getCellData(i, 1);

            driver.findElement(By.cssSelector("input[data-analytics-name='email']")).clear();
            driver.findElement(By.cssSelector("input[data-analytics-name='email']")).sendKeys(email);
            driver.findElement(By.cssSelector("input[data-analytics-name='password']")).clear();
            driver.findElement(By.cssSelector("input[data-analytics-name='password']")).sendKeys(password);
            driver.findElement(By.cssSelector("button[data-analytics-name='login']")).click();

            WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));

            String result="";
            try{
            	
                String emailErro = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='errorMessage-email']"))).getText();
                String passwordErro = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='errorMessage-password']"))).getText();
                if (!emailErro.isEmpty()||!passwordErro.isEmpty()) {
                    result="Fail - "+ emailErro+" "+ passwordErro;
                    System.out.println("Login failed: "+result);
                }} 
            catch(Exception e) {}
            if(result.isEmpty()) {
            try{
                String emailError=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='errorMessage-email']"))).getText();
                if (!emailError.isEmpty()) {
                    result="Fail - "+ emailError;
                    System.out.println("Login failed: "+result);
                }} 
            catch(Exception e){}}

            if(result.isEmpty()) {
                try{
                    String passwordError=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='errorMessage-password']"))).getText();
                    if (!passwordError.isEmpty()) {
                        result="Fail - "+passwordError;
                        System.out.println("Login failed: "+result);} } 
                catch(Exception e){}
            }

           
            if(result.isEmpty()){
                try{
                    String generalError=wait.until(ExpectedConditions.visibilityOfElementLocated( By.xpath("//*[@id='signin-form']/div/div[3]"))).getText();
                    if(!generalError.isEmpty()){
                        result="Fail - "+ generalError;
                        System.out.println("Login failed: "+result);}} 
                catch(Exception e){ }
            }

          if(result.isEmpty()) {
                try{
                        result="Pass";
                        System.out.println("Login Successful for user: "+email); }
                catch (Exception e) {
                    result="Fail - Email/Password you entered is not correct.";
                    System.out.println("Login failed for user: " + email); } }

           
            ExcelUtils.setCellData(result, i, 2, excelPath);
        }
    }

    @AfterTest
    public void down() {
        driver.quit();}
}


