 package TestNg;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Para_Login {

	// Declare WebDriver instance
	WebDriver driver;

	@BeforeClass
	public void setup() {
		// Set up WebDriver before running tests
		System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://parabank.parasoft.com");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // Implicit wait
	}

	@Test
	void login()
	{
		try {
            // Maximize the window and navigate to the website
            driver.manage().window().maximize();
            driver.get("http://parabank.parasoft.com");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            // Locators for Login
            WebElement txt_username = driver.findElement(By.xpath("//input[@name='username' and @class='input']"));
            WebElement txt_password = driver.findElement(By.xpath("//input[@name='password' and @class='input']"));
            WebElement btn_submit = driver.findElement(By.xpath("//input[@value='Log In' and @type='submit']"));

            // Input credentials
            txt_username.sendKeys("abccd");
            txt_password.sendKeys("abccd");
            btn_submit.click();

            // Wait for the response

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
        }
	}
	
	@AfterClass
	public void tearDown() {
		// Close the browser
		if (driver != null) {
			driver.quit();
		}

	}
}
