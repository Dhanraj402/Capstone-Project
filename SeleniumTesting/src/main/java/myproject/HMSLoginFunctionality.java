package myproject;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HMSLoginFunctionality {

			
    private WebDriver driver;

	 @Before
    public void setUp() {
    	
  	  // Set path to ChromeDriver
      System.setProperty("webdriver.chrome.driver", "C:\\Users\\user\\Desktop\\AxisPro\\Selenium\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

      // Enable ChromeDriver logging
      System.setProperty("webdriver.chrome.logfile", "C:\\Users\\user\\Desktop\\AxisPro\\Selenium\\chromedriver.log");
      System.setProperty("webdriver.chrome.verboseLogging", "true");

      // Set Chrome options
      ChromeOptions options = new ChromeOptions();
      options.addArguments("--remote-allow-origins=*");

      // Initialize a new ChromeDriver instance with options
      driver = new ChromeDriver(options);
      driver.manage().window().maximize();
      // Navigate to a website
      driver.get("http://localhost:3000");
      try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


  }

  @After
  public void tearDown() {
      if (driver != null) {
          driver.quit();
      }
  }

  @Test
  public void falseLoginTest() throws InterruptedException {
	
	  
	  WebElement login = driver.findElement(By.xpath("//button[@id='login__btn']"));
      
	   login.click();
       Thread.sleep(2000);
       WebElement usernamename = driver.findElement(By.xpath("//input[@type='text']"));
       WebElement pass = driver.findElement(By.xpath("//input[@type='password']"));
       WebElement loginSubmit= driver.findElement(By.xpath("//button[@type='submit']"));
       
       usernamename.isDisplayed();
       usernamename.isEnabled();
       usernamename.clear();
       usernamename.sendKeys("Dhanraj");
       Thread.sleep(2000);
       
       pass.isDisplayed();
       pass.isEnabled();
       pass.clear();
       pass.sendKeys("pass");
       Thread.sleep(2000);
      
       loginSubmit.click();
       Thread.sleep(2000);
       
       String toastMessage="*****";
       
       try {
           // Wait for the toast notification to appear
     	  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

           WebElement toastElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("Toastify")));

           // Get text content of the toast notification
           toastMessage = toastElement.getText();
           System.out.println("Toast Message: " + toastMessage);
       } catch (Exception e) {
           System.err.println("Toast notification not found within expected time.");
           e.printStackTrace();
       } 
       
       String expectedResult="Incorrect username or password. Please try again.";
       String actualResult=toastMessage;  
       System.out.println("falseLoginTest");         
       assertEquals(expectedResult,actualResult);
  }

  @Test
  public void doctorLoginTest() throws InterruptedException {
	
	  
	  WebElement login = driver.findElement(By.xpath("//button[@id='login__btn']"));
      
	   login.click();
       Thread.sleep(2000);
       WebElement usernamename = driver.findElement(By.xpath("//input[@type='text']"));
       WebElement pass = driver.findElement(By.xpath("//input[@type='password']"));
       WebElement loginSubmit= driver.findElement(By.xpath("//button[@type='submit']"));
       
       usernamename.isDisplayed();
       usernamename.isEnabled();
       usernamename.clear();
       usernamename.sendKeys("saiful");
       Thread.sleep(2000);
       
       pass.isDisplayed();
       pass.isEnabled();
       pass.clear();
       pass.sendKeys("saiful@123");
       Thread.sleep(2000);
      
       loginSubmit.click();
       Thread.sleep(2000);
       
       String toastMessage="*****";
       
       ////div[@class='sidebar']//ul
       
       WebElement ulElement = driver.findElement(By.xpath("//div[@class='sidebar']//ul"));

       // Find all list items within the unordered list
       List<WebElement> listItems = ulElement.findElements(By.tagName("li"));

       List<String>ExpectedOptions= new ArrayList<String>();
       ExpectedOptions.add("Dashboard");
       ExpectedOptions.add("Appointments");
       ExpectedOptions.add("Prescription");
       ExpectedOptions.add("Bill");
       ExpectedOptions.add("Clinic");
       ExpectedOptions.add("Add Employee");
       ExpectedOptions.add("Settings");
       
       
       // Iterate through the list items and print their text
       for (WebElement listItem : listItems) {
           System.out.println(listItem.getText());
           String option=listItem.getText();
           for(String op :ExpectedOptions)
           {
        	   if(op.equals(option))
        	   {
        		   break;
        	   }
           }
       }
       
       try {
           // Wait for the toast notification to appear
     	  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

           WebElement toastElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("Toastify")));

           // Get text content of the toast notification
           toastMessage = toastElement.getText();
           System.out.println("Toast Message: " + toastMessage);
       } catch (Exception e) {
           System.err.println("Toast notification not found within expected time.");
           e.printStackTrace();
       } 
       
       String expectedResult="Login successful!";
       String actualResult=toastMessage;  
       System.out.println("doctorLoginTest");         
       assertEquals(expectedResult,actualResult);
  }

  
  
}
