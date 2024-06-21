package seleniumcscripts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class FristScript {

    public static void main(String[] args) {
        // Set path to ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\user\\Desktop\\AxisPro\\Selenium\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        // Enable ChromeDriver logging
        System.setProperty("webdriver.chrome.logfile", "C:\\Users\\user\\Desktop\\AxisPro\\Selenium\\chromedriver.log");
        System.setProperty("webdriver.chrome.verboseLogging", "true");

        // Set Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        // Initialize a new ChromeDriver instance with options
        WebDriver driver = new ChromeDriver(options);

        // Navigate to a website
        driver.get("https://www.nseindia.com/");

        // Perform any actions you need to
        System.out.println("Page title is: " + driver.getTitle());

        // Close the browser
        driver.quit();
    }
}
