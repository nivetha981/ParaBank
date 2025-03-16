package parabank;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class updateProfile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver","Driver/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://parabank.parasoft.com");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		try {
            // Maximize the window and navigate to the website
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
                    System.out.println("Login Successful, " + successText+" !");
                } else {
                    System.out.println("Unexpected login outcome. Content: " + successText);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
        }
		
		// UPDATEPROFILE
		driver.findElement(By.xpath("//a[normalize-space()='Update Contact Info']")).click();
		WebDriverWait waits = new WebDriverWait(driver, 10); // Use integer timeout for Selenium 3.x

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

		driver.quit();
	}

}
