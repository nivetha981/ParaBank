package parabankTestng;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FundTransfer {

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

	@Test(priority = 1)
	public void login() {
        // Navigate to the application
        driver.get("http://parabank.parasoft.com");

        try {
            // Locators for Login
            WebElement txt_username = driver.findElement(By.xpath("//input[@name='username' and @class='input']"));
            WebElement txt_password = driver.findElement(By.xpath("//input[@name='password' and @class='input']"));
            WebElement btn_submit = driver.findElement(By.xpath("//input[@value='Log In' and @type='submit']"));

            // Input credentials
            txt_username.sendKeys("abccd");
            txt_password.sendKeys("abccd");
            btn_submit.click();

            // Wait for the response
            WebDriverWait wait = new WebDriverWait(driver, 10);

            // Check for successful login or error message
            try {
                WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='error']")));
                if (errorMessage.isDisplayed()) {
                    System.out.println("Login Failed. Error: " + errorMessage.getText());
                }
            } catch (Exception e) {
                // If no error message, check for successful login
                WebElement successPanel = driver.findElement(By.xpath("//b[normalize-space()='Welcome']"));
                String successText = successPanel.getText();
                if (successText.contains("Welcome")) {
                    System.out.println("Login Successful, " + successText + "!");
                } else {
                    System.out.println("Unexpected login outcome. Content: " + successText);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Test(priority = 2)
	public void fundTransfer() {
        try {
            // FUND TRANSFER
            WebDriverWait wait = new WebDriverWait(driver, 20);
            
            // Wait for the "Transfer Funds" link and click it
            WebElement transferFundsLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Transfer Funds']")));
            transferFundsLink.click();

            // Enter amount
            WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='amount' and @type='text']")));
            amountField.sendKeys("1000");

            // Click "Transfer" button
            WebElement transferButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Transfer']")));
            transferButton.click();

            // Wait for the result section
            WebDriverWait wait01 = new WebDriverWait(driver, 10);
            WebElement resultDiv = wait01.until(ExpectedConditions.visibilityOfElementLocated(By.id("showResult")));

            // Extract result details
            WebElement header = resultDiv.findElement(By.tagName("h1"));
            WebElement paragraph = resultDiv.findElement(By.xpath("./p[1]"));
            WebElement detailsParagraph = resultDiv.findElement(By.xpath("./p[2]"));

            // Print transfer result
            System.out.println("Fund Transfer Result:");
            System.out.println(header.getText());
            System.out.println(paragraph.getText());
            System.out.println(detailsParagraph.getText());

        } catch (Exception e) {
            System.out.println("Error: Fund transfer failed - " + e.getMessage());
        }
		driver.quit();

    }


//	@AfterClass
//	public void tearDown() {
//		// Close the browser
//		if (driver != null) {
		
	}

