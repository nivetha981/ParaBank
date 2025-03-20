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

public class loanReq {

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
		try {
			// Locate login fields
			WebElement txt_username = driver.findElement(By.xpath("//input[@name='username' and @class='input']"));
			WebElement txt_password = driver.findElement(By.xpath("//input[@name='password' and @class='input']"));
			WebElement btn_submit = driver.findElement(By.xpath("//input[@value='Log In' and @type='submit']"));

			// Input credentials and click login
			txt_username.sendKeys("abccd");
			txt_password.sendKeys("abccd");
			btn_submit.click();

			// Wait for login response
			WebDriverWait wait = new WebDriverWait(driver, 10);

			try {
				WebElement errorMessage = wait
						.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='error']")));
				System.out.println("Login Failed. Error: " + errorMessage.getText());
			} catch (Exception e) {
				// Check for successful login
				WebElement successPanel = driver.findElement(By.xpath("//b[normalize-space()='Welcome']"));
				String successText = successPanel.getText();
				System.out.println("Login Successful: " + successText);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 2)
	public void loanReq() {

		driver.findElement(By.xpath("//a[normalize-space()='Request Loan']")).click();
		driver.findElement(By.xpath("//input[@id='amount']")).sendKeys("8900");
		driver.findElement(By.xpath("//input[@id='downPayment']")).sendKeys("899");
		driver.findElement(By.xpath("//select[@id='fromAccountId']")).click();
		driver.findElement(By.xpath("//input[@value='Apply Now']")).click();

		WebDriverWait wait = new WebDriverWait(driver, 10);

		// Check if the loan result is displayed
		boolean isResultDisplayed = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("requestLoanResult"))).isDisplayed();

		if (isResultDisplayed) {
			WebElement loanResult = driver.findElement(By.id("requestLoanResult"));
			System.out.println("Loan Approved:");
			System.out.println(loanResult.getText());
		} else {
			WebElement loanError = driver.findElement(By.id("requestLoanError"));
			System.out.println("Loan Request Failed:");
			System.out.println(loanError.getText());
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
