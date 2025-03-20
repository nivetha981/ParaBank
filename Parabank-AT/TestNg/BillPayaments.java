package parabankTestng;

import java.util.List;
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

public class BillPayaments {

	WebDriver driver;

	@BeforeClass
	public void setup() {
		// Set up WebDriver before running tests
		System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://parabank.parasoft.com");

		driver.manage().window().maximize();
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

	public void BillPayaments()  {
		// BILLPAYMENTS
		driver.findElement(By.xpath("//a[normalize-space()='Bill Pay']")).click();
		// Find Elements
		List<WebElement> input = driver.findElements(By.xpath("//input[@class='input']"));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String[] arr_input = { "nibo", "che", "kvp", "TY", "789456" };

		// Loop through the smaller of input.size() and arr_input.length
		for (int i = 0; i < Math.min(input.size(), arr_input.length); i++) {
			input.get(i).clear(); // Clear the existing value in the field
			input.get(i).sendKeys(arr_input[i]);
		}
		driver.findElement(By.xpath("//input[@name='payee.phoneNumber']")).sendKeys("989898988");
		driver.findElement(By.xpath("//input[@name='payee.accountNumber']")).sendKeys("989000898988");
		driver.findElement(By.xpath("//input[@name='verifyAccount']")).sendKeys("989000898988");
		driver.findElement(By.xpath("//input[@name='amount']")).sendKeys("78");

		driver.findElement(By.xpath("//select[@name='fromAccountId']")).click();

		driver.findElement(By.xpath("//input[@value='Send Payment']")).click();
		WebDriverWait waits = new WebDriverWait(driver, 20); // Use integer timeout for Selenium 3.x
		waits.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='billpayResult']"))).click();

		WebElement resultDiv = driver.findElement(By.id("billpayResult"));
		WebElement header = resultDiv.findElement(By.tagName("h1"));
		System.out.println(header.getText());

		WebElement paragraph = resultDiv.findElement(By.xpath("//*[@id=\"billpayResult\"]/p[1]"));
		System.out.println(paragraph.getText());

		WebElement paragraph1 = resultDiv.findElement(By.xpath("//*[@id=\"billpayResult\"]/p[2]"));
		System.out.println(paragraph1.getText());
	}

	@AfterClass
	public void tearDown() {
		// Close the browser
		if (driver != null) {
			driver.quit();
		}
	}
}
