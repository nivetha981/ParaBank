package parabank;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class loanReq {
	WebDriver driver;

	void setup()
	{
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver","Driver/chromedriver.exe");
		 driver = new ChromeDriver();
        driver.get("http://parabank.parasoft.com");
		driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
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
	            WebDriverWait wait = new WebDriverWait(driver, 10);
	    		driver.findElement(By.xpath("//a[normalize-space()='Request Loan']")).click();

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
		}
		
		
void loanreq()
{
	driver.findElement(By.xpath("//a[normalize-space()='Request Loan']")).click();
	driver.findElement(By.xpath("//input[@id='amount']")).sendKeys("8900");
	driver.findElement(By.xpath("//input[@id='downPayment']")).sendKeys("899");
	driver.findElement(By.xpath("//select[@id='fromAccountId']")).click();
	driver.findElement(By.xpath("//input[@value='Apply Now']")).click();
	
	WebDriverWait wait = new WebDriverWait(driver, 10);

    // Check if the loan result is displayed
    boolean isResultDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("requestLoanResult"))).isDisplayed();

    if (isResultDisplayed) {
        WebElement loanResult = driver.findElement(By.id("requestLoanResult"));
        System.out.println("Loan Approved:");
        System.out.println(loanResult.getText());
    } else {
        WebElement loanError = driver.findElement(By.id("requestLoanError"));
        System.out.println("Loan Request Failed:");
        System.out.println(loanError.getText());
    }
	driver.quit();
}
		
public static void main(String[] args) {
	loanReq obj=new loanReq();
	obj.setup();
	obj.login();
	obj.loanreq();

}
}
