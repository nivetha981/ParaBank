package TestNg;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class paraRegisteration {

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
	public void paraRegisteration() {
		try {
			// Navigate to the website
			driver.get("http://parabank.parasoft.com"); // Replace with the actual URL of your application

			// Click the 'Register' link
			driver.findElement(By.xpath("//a[normalize-space()='Register']")).click();

			// Fill out the registration form
			driver.findElement(By.id("customer.firstName")).sendKeys("Niivv");
			driver.findElement(By.id("customer.lastName")).sendKeys("M");
			driver.findElement(By.id("customer.address.street")).sendKeys("123, 7th cross");
			driver.findElement(By.id("customer.address.city")).sendKeys("HYD");
			driver.findElement(By.id("customer.address.state")).sendKeys("TN");
			driver.findElement(By.id("customer.address.zipCode")).sendKeys("6001");
			driver.findElement(By.id("customer.phoneNumber")).sendKeys("989898898");
			driver.findElement(By.id("customer.ssn")).sendKeys("855");

			// Input username and password
			driver.findElement(By.xpath("//input[@name='customer.username']")).sendKeys("abccd");
			driver.findElement(By.xpath("//input[@name='customer.password']")).sendKeys("abccd");
			driver.findElement(By.xpath("//input[@id='repeatedPassword']")).sendKeys("abccd");

			// Click the 'Register' button
			driver.findElement(By.xpath("//input[@value='Register']")).sendKeys(Keys.ENTER);

			// Wait for the success message to appear
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//p[contains(text(),'Your account was created successfully. You are now logged in.')]")));

			// Get the success message text and print it
			String display = successMessage.getText();
			System.out.println(display);

		} catch (Exception e) {
			e.printStackTrace();
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
