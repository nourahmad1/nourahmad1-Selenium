package Noor.Test;


import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.lang.Thread;

public class Home {

	public static void main(String[] args) throws InterruptedException {
		
		
		WebDriver driver= new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get("https://www.lancome-usa.com");
		driver.findElement(By.xpath("/html/body/div[18]/div[2]/div/div[2]/button")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		if	(driver.findElement(By.xpath("/html/body/div[9]/header/div[1]/div[3]/div[1]/a/img[3]")).isDisplayed()) {
			System.out.println("Logo is displayed");
		} else {
			System.out.println("Logo is not displayed");
		}

		
		if	(driver.findElement(By.xpath("/html/body/div[9]/div/div[4]/div/div/button")).isDisplayed()) {
			System.out.println("Bag is displayed");
		} else {
			System.out.println("Bag is not displayed");
		
		}
		
	if	(driver.findElement(By.cssSelector("div[class='c-myaccount-button']")).isDisplayed()) {
		System.out.println("My account panel is displayed");
	} else {
		System.out.println("My account panel is not not displayed");
	}
		
		driver.findElement(By.cssSelector("div[class='c-myaccount-button']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	

				if	(driver.findElement(By.cssSelector("button[aria-label='Sign In or Create Account']")).isDisplayed()) {
					System.out.println("Sing up is displayed");
				} else {
					System.out.println("Sign up is not displayed");
				}
				driver.findElement(By.cssSelector("button[aria-label='Sign In or Create Account']")).click();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				
				String pageTitle = driver.findElement(By.className("c-account__title")).getText();
				System.out.println("Page Title: " + pageTitle);

	
				if(pageTitle.equals("SIGN IN")) {
				    System.out.println("Title is correct");
				} else {
				    System.out.println("Title is not correct");
				}
				driver.findElement(By.name("password")).sendKeys("Noor@123456");
				
				
				List<WebElement> inputs = driver.findElements(By.cssSelector("input.c-text-field__input"));

				for (WebElement input : inputs) {
				    if (input.isDisplayed()) {
				        input.sendKeys("nourbzour65@gmail.com");
				        break;
				    }
				}
				
				driver.findElement(By.xpath("/html/body/div[25]/div/div/div/div/div[1]/div[1]/form/div[9]")).click();
				Thread.sleep(2000);
				
			
				WebElement accountTitle = driver.findElement(By.className("c-account-panel__title"));
				String text = accountTitle.getText();
				System.out.println(text);
				if(text.contains("Nour")) {
				    System.out.println("User logged in successfully");
				} else {
				    System.out.println("Login failed");
				}
	}

}
