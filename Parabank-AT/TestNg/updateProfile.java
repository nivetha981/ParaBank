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

public class updateProfile {

	WebDriver driver;

@BeforeClass
public void setup() {
    // Set up WebDriver before running tests
	System.setProperty("webdriver.chrome.driver","Driver/chromedriver.exe");
	
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
	public void updateProfile() {

		// UPDATEPROFILE
		WebDriverWait waits = new WebDriverWait(driver, 10); // Use integer timeout for Selenium 3.x
		driver.findElement(By.xpath("//a[normalize-space()='Update Contact Info']")).click();//a[normalize-space()='Update Contact Info']

		List<WebElement> input1 = driver.findElements(By.xpath("//value[@class='input']"));
		String[] arr = { "nive", "m", "123 bicycle", "chenn", "HYD", "7897", "789456123" };
		for (int i = 0; i < input1.size(); i++) {
			input1.get(i).clear();
			input1.get(i).sendKeys(arr[i]);
		}
		driver.findElement(By.xpath("//input[@value='Update Profile']")).click();
		WebDriverWait wait = new WebDriverWait(driver, 10); // Use integer timeout for Selenium 3.x
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("updateProfileResult")));

		WebElement resultDiv = driver.findElement(By.id("updateProfileResult"));
		WebElement header = resultDiv.findElement(By.tagName("h1"));
		System.out.println(header.getText());

		WebElement paragraph = resultDiv.findElement(By.xpath("./p[1]"));
		System.out.println(paragraph.getText());
	}

	@AfterClass
	public void tearDown() {
		// Close the browser
		if (driver != null) {
			driver.quit();
		}

	}

}
