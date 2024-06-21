package myproject;



import static org.junit.Assert.assertEquals;

import java.time.Duration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HMSBookAppointmentFunctionality {

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
    public void SingleAppointmentForMail() throws InterruptedException {
          WebElement bookAppointment = driver.findElement(By.xpath("//button[normalize-space()='Book An Appointment']"));
         // System.out.println(bookAppointment.getText());
          bookAppointment.click();
          Thread.sleep(3000);
          WebElement name = driver.findElement(By.xpath("//input[@type='text']"));
          WebElement email = driver.findElement(By.xpath("//input[@type='email']"));
          WebElement apDate = driver.findElement(By.xpath("//input[@type='date']"));
          WebElement timeDropDown = driver.findElement(By.xpath("//form[@class='modal__form']//select"));
          
          Select select= new Select(timeDropDown); 
          
          
          
          name.isDisplayed();
          name.isEnabled();
          name.clear();
          name.sendKeys("Dhanraj Mankar");
          Thread.sleep(2000);
          
          email.isDisplayed();
          email.isEnabled();
          email.clear();
          email.sendKeys("dhanrajmanakr402@gmail.com");
          Thread.sleep(2000);
          
          apDate.isDisplayed();
          apDate.isEnabled();
          apDate.clear();
          apDate.sendKeys("24-06-2024");
          Thread.sleep(2000);
          
          select.selectByVisibleText("10:00 AM");
          Thread.sleep(2000);
          
          
        //button[normalize-space()='Book Appointment']
        //  System.out.println("Hi");
          WebElement bookApp = driver.findElement(By.xpath("//button[normalize-space()='Book Appointment']"));
          Thread.sleep(2000);
          
          bookApp.click();
          Thread.sleep(2000);
          
        //div[contains(text(),'Booking confirmed, check your email for booking co')]
        //div[@id='6']
        //div[@class='Toastify']  
          
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
          
          String expectedResult="Cannot book multiple appointments with the same name and email on the same day";
          String actualResult=toastMessage;  
          System.out.println("SingleAppointmentForMail");         
          assertEquals(expectedResult,actualResult);
    }
    
    
    
    @Test
    public void clineWorkingTimeValidation () throws InterruptedException {
          WebElement bookAppointment = driver.findElement(By.xpath("//button[normalize-space()='Book An Appointment']"));
          System.out.println(bookAppointment.getText());
          bookAppointment.click();
          Thread.sleep(3000);
          WebElement name = driver.findElement(By.xpath("//input[@type='text']"));
          WebElement email = driver.findElement(By.xpath("//input[@type='email']"));
          WebElement apDate = driver.findElement(By.xpath("//input[@type='date']"));
          WebElement timeDropDown = driver.findElement(By.xpath("//form[@class='modal__form']//select"));
          
          Select select= new Select(timeDropDown); 
         
          
          name.isDisplayed();
          name.isEnabled();
          name.clear();
          name.sendKeys("Dhanraj Mankar");
          Thread.sleep(1500);
          
          email.isDisplayed();
          email.isEnabled();
          email.clear();
          email.sendKeys("dhanrajmankar@gmail.com");
          Thread.sleep(1500);
          
          apDate.isDisplayed();
          apDate.isEnabled();
          apDate.clear();
          apDate.sendKeys("24-06-2024");
          Thread.sleep(1500);
          
          select.selectByVisibleText("06:00 AM");
          Thread.sleep(1500);
          
          
        //button[normalize-space()='Book Appointment']
          
          WebElement bookApp = driver.findElement(By.xpath("//button[normalize-space()='Book Appointment']"));
          Thread.sleep(2000);
          
          bookApp.click();
          Thread.sleep(2000);
          
        //div[contains(text(),'Booking confirmed, check your email for booking co')]
        //div[@id='6']
        //div[@class='Toastify']  
          
          String toastMessage="";
          
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
          String expectedResult="Appointment time is outside clinic operating hours. Please choose a time between 9:00 AM - 6:00 PM";
          String actualResult=toastMessage;  
          System.out.println("clineWorkingTimeValidation");         
          assertEquals(expectedResult,actualResult);
    }
   
    @Test
    public void clinicWorkingDayValidation () throws InterruptedException {
          WebElement bookAppointment = driver.findElement(By.xpath("//button[normalize-space()='Book An Appointment']"));
          System.out.println(bookAppointment.getText());
          bookAppointment.click();
          Thread.sleep(3000);
          WebElement name = driver.findElement(By.xpath("//input[@type='text']"));
          WebElement email = driver.findElement(By.xpath("//input[@type='email']"));
          WebElement apDate = driver.findElement(By.xpath("//input[@type='date']"));
          WebElement timeDropDown = driver.findElement(By.xpath("//form[@class='modal__form']//select"));
          
          Select select= new Select(timeDropDown); 
          
          
          name.isDisplayed();
          name.isEnabled();
          name.clear();
          name.sendKeys("Dhanraj Mankar");
          Thread.sleep(1500);
          
          email.isDisplayed();
          email.isEnabled();
          email.clear();
          email.sendKeys("dhanraj4020@gmail.com");
          Thread.sleep(1500);
          
          apDate.isDisplayed();
          apDate.isEnabled();
          apDate.clear();
          apDate.sendKeys("23-06-2024");
          Thread.sleep(1500);
          
          select.selectByVisibleText("10:00 AM");
          Thread.sleep(1500);
          
          
        //button[normalize-space()='Book Appointment']
          
          WebElement bookApp = driver.findElement(By.xpath("//button[normalize-space()='Book Appointment']"));
          Thread.sleep(2000);
          
          bookApp.click();
          Thread.sleep(1500);
          
        //div[contains(text(),'Booking confirmed, check your email for booking co')]
        //div[@id='6']
        //div[@class='Toastify']  
          String toastMessage="";
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
          String expectedResult="Appointment date is outside clinic operating days. Please choose a date within the operating days: Monday To Friday";
          String actualResult=toastMessage;  
          System.out.println("clineWorkingDayValidation");         
          assertEquals(expectedResult,actualResult);
    }
    
    
}
