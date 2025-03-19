package parabankTestng;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class OpenNewAccount {
    ChromeDriver driver;

    @BeforeClass
    public void setup() {
        // Set the path to ChromeDriver
    	System.setProperty("webdriver.chrome.driver","Driver/chromedriver.exe");


        // Initialize the ChromeDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
    public void openNewAccount() {
        // Navigate to Open New Account page
        driver.findElement(By.xpath("//a[normalize-space()='Open New Account']")).click();
        driver.findElement(By.xpath("//option[@value='0']")).click();
        driver.findElement(By.xpath("//select[@id='fromAccountId']")).click();
        driver.findElement(By.xpath("//input[@value='Open New Account']")).click();


        // Validate success message
        WebElement para = driver.findElement(By.xpath("//b[normalize-space()='Welcome']"));
        String para1 = para.getText();
        System.out.println(para1+ " to your account");

   
            driver.quit();
        }
    }

