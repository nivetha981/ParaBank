package parabank;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BillPayments {
    WebDriver driver;

    void setup() {
        System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://parabank.parasoft.com"); // Load the website only once
    }

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

            // Wait for the response
           

    void billPayments() {
        try {
            driver.findElement(By.xpath("//a[normalize-space()='Bill Pay']")).click();
            List<WebElement> inputFields = driver.findElements(By.xpath("//input[@class='input']"));
            String[] inputData = {"John Doe", "123 Street", "City", "State", "12345"};

            for (int i = 0; i < Math.min(inputFields.size(), inputData.length); i++) {
                inputFields.get(i).clear();
                inputFields.get(i).sendKeys(inputData[i]);
            }

            driver.findElement(By.xpath("//input[@name='payee.phoneNumber']")).sendKeys("989898988");
            driver.findElement(By.xpath("//input[@name='payee.accountNumber']")).sendKeys("989000898988");
            driver.findElement(By.xpath("//input[@name='verifyAccount']")).sendKeys("989000898988");
            driver.findElement(By.xpath("//input[@name='amount']")).sendKeys("78");

            Select dropdown = new Select(driver.findElement(By.xpath("//select[@name='fromAccountId']")));
            dropdown.selectByIndex(0);

            driver.findElement(By.xpath("//input[@value='Send Payment']")).click();
         WebDriverWait wait = new WebDriverWait(driver, 15);

            WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("///div[@id='billpayResult']")));
          System.out.println("Payment Successful: " + successMessage.getText());
        }
    
    

        
        

            // Wait for confirmation or error
//            WebDriverWait wait = new WebDriverWait(driver, 15);
//
//            if (!driver.findElements(By.xpath("//p[@class='error']")).isEmpty()) {
//                List<WebElement> errorMessages = driver.findElements(By.xpath("//p[@class='error']"));
//                for (WebElement err : errorMessages) {
//                    System.out.println("Payment Error: " + err.getText());
//                }
//            } else {
//                // Modified XPath for success message
//                WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(text(),'Bill Payment Complete')]")));
//                System.out.println("Payment Successful: " + successMessage.getText());
//            }
       // } 
catch (Exception e) {
            System.out.println("Error during bill payment: " + e.getMessage());
            System.out.println("Full Page Source:\n" + driver.getPageSource());
        } finally {
            driver.quit();
        }
        }
    
   


    public static void main(String[] args) {
        BillPayments obj = new BillPayments();
        obj.setup();
        obj.login();
        obj.billPayments();
    }
}
