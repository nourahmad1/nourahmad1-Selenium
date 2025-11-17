package TestNG.Homework;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestWithExcel{

    WebDriver driver;
    String excelPath="src/test/java/Book 2.xlsx";
    int currentRow=1;

    @BeforeTest
    public void setup(){
        ChromeOptions options=new ChromeOptions();
        options.setExperimentalOption("excludeSwitches",new String[]{"enable-automation"});
        options.addArguments("--disable-blink-features=AutomationControlled");
        driver=new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        driver.get("https://www.frontgate.com/ShoppingCartView");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        /*try{
           // driver.findElement(By.cssSelector("button[data-analytics-name='navigate_back']")).click();
        } catch(Exception e){
            System.out.println("Back button not available");   
            }*/
       }

    @DataProvider(name = "loginUsers")
    public Object[][] fetchLoginData() throws Exception{
    	ExcelUtils.open(excelPath, "Sheet1");
        int total=ExcelUtils.rows();
        Object[][] data=new Object[total-1][3];

        for (int i=1;i<total;i++){
            data[i-1][0]=ExcelUtils.getCell(i,0);
            data[i-1][1]=ExcelUtils.getCell(i,1);
            data[i-1][2]=ExcelUtils.getCell(i,2);
        }
        return data;
    }

    @Test(dataProvider="loginUsers")
    public void loginTest(String email,String password,String expectedMsg)throws Exception{
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
        String actualMsg="";

        driver.findElement(By.cssSelector("input[data-analytics-name='email']")).clear();
        driver.findElement(By.cssSelector("input[data-analytics-name='email']")).sendKeys(email);
        driver.findElement(By.cssSelector("input[data-analytics-name='password']")).clear();
        driver.findElement(By.cssSelector("input[data-analytics-name='password']")).sendKeys(password);
        driver.findElement(By.cssSelector("button[data-analytics-name='login']")).click();

        try{
            String e1=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='errorMessage-email']"))).getText();
            String e2=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='errorMessage-password']"))).getText();
            if (!e1.isEmpty()||!e2.isEmpty())actualMsg="Fail - "+ e1+ " "+e2;
        } catch(Exception e){}

        if(actualMsg.isEmpty()){
            try{
                String genErr=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='signin-form']/div/div[3]"))).getText();
                if (!genErr.isEmpty())actualMsg="Fail - " + genErr;
            } catch(Exception e){}
        }

        if(actualMsg.isEmpty())actualMsg="Pass";

        try{
            Assert.assertEquals(actualMsg.trim(),expectedMsg.trim());
        }catch (AssertionError e){
            System.out.println("Test failed for: "+ email+" Expected: "+expectedMsg+" Actual: "+actualMsg);
            actualMsg = "Fail - " + actualMsg;
            throw e;
        } finally{
            ExcelUtils.setCell(actualMsg,currentRow,2,excelPath);
            currentRow++;
        }

        System.out.println("Test passed for: " +email);
    }

    @AfterTest
    public void closeBrowser(){
        driver.quit();
    }
}
