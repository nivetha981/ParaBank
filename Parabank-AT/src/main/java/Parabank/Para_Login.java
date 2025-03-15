package parabank;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.concurrent.TimeUnit;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Para_Login {

    WebDriver driver;  

    public void setup() {
        System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://parabank.parasoft.com");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
    }

    public void paraLogin() {
        try {
            WebElement txt_username = driver.findElement(By.xpath("//input[@name='username' and @class='input']"));
            WebElement txt_password = driver.findElement(By.xpath("//input[@name='password' and @class='input']"));
            WebElement btn_submit = driver.findElement(By.xpath("//input[@value='Log In' and @type='submit']"));

            txt_username.sendKeys("abcccd");
            txt_password.sendKeys("abcccd");
            btn_submit.click();

            WebDriverWait wait = new WebDriverWait(driver, 20);  // 10 is in seconds

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
                    System.out.println("Login Successful, " + successText + " !");
                } else {
                    System.out.println("Unexpected login outcome. Content: " + successText);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    public static void main(String[] args) {
        Para_Login obj = new Para_Login(); 
        obj.setup();      
        obj.paraLogin();  
    }
}
