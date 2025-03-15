package parabank;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class openNewAccount {

		
		// TODO Auto-generated method stub
		
	WebDriver driver;  

    public void setup() {
        System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://parabank.parasoft.com");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
                        }

    public void paraLogin() 
    {
        try {
            WebElement txt_username = driver.findElement(By.xpath("//input[@name='username' and @class='input']"));
            WebElement txt_password = driver.findElement(By.xpath("//input[@name='password' and @class='input']"));
            WebElement btn_submit = driver.findElement(By.xpath("//input[@value='Log In' and @type='submit']"));

            txt_username.sendKeys("abccd");
            txt_password.sendKeys("abccd");
            btn_submit.click();

            WebDriverWait wait = new WebDriverWait(driver, 20);  // 10 is in seconds

            try {
                WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='error']")));
                if (errorMessage.isDisplayed()) {
                    System.out.println("Login Failed. Error: " + errorMessage.getText());
                }
            } 
            catch (Exception e)
            {
                // If no error message, check for successful login
                WebElement successPanel = driver.findElement(By.xpath("//b[normalize-space()='Welcome']"));
                String successText = successPanel.getText();
                if (successText.contains("Welcome")) 
                {
                    System.out.println("Login Successful, " + successText + " !");
                } 
                else {
                    System.out.println("Unexpected login outcome. Content: " + successText);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
    public void openaccount()
	{
	driver.findElement(By.xpath("//a[normalize-space()='Open New Account']")).click();
	driver.findElement(By.xpath("//option[@value='0']")).click();
	driver.findElement(By.xpath("//select[@id='fromAccountId']")).click();
	driver.findElement(By.xpath("//input[@value=\"Open New Account\"]")).click();
	 System.out.println("Account was sucessfully created");
	WebElement para = driver.findElement(By.xpath("//p[text()='Congratulations, your account is now open.']"));
	//String para1 = para.getText();
	//System.out.println("Paragraph Text: " + para1);

	WebElement accountNumElement = driver.findElement(By.xpath("//b[text()='Your new account number:']"));
	//String accountNumber = accountNumElement.getText();

	//System.out.println("Account Number: " + accountNumber);
	driver.quit();
	}


    public static void main(String[] args)
    {
        openNewAccount obj1 = new openNewAccount(); 
        obj1.setup();      
        obj1.paraLogin(); 
        obj1.openaccount();
    }

	
}



