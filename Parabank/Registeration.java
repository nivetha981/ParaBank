package Parabank;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Registeration {

    // Declare WebDriver at class level
    static WebDriver driver;

    // Setup method to initialize WebDriver
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://parabank.parasoft.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // Implicit wait
    }

    public static void main(String[] args) {
        setup(); 

        try {
            driver.findElement(By.xpath("//a[normalize-space()='Register']")).click();
            driver.findElement(By.id("customer.firstName")).sendKeys("Niivv");
            driver.findElement(By.id("customer.lastName")).sendKeys("M");
            driver.findElement(By.id("customer.address.street")).sendKeys("123, 7th cross");
            driver.findElement(By.id("customer.address.city")).sendKeys("HYD");
            driver.findElement(By.id("customer.address.state")).sendKeys("TN");
            driver.findElement(By.id("customer.address.zipCode")).sendKeys("6001");
            driver.findElement(By.id("customer.phoneNumber")).sendKeys("989898898");
            driver.findElement(By.id("customer.ssn")).sendKeys("855");

            driver.findElement(By.xpath("//input[@name='customer.username']")).sendKeys("abccd");
            driver.findElement(By.xpath("//input[@name='customer.password']")).sendKeys("abccd");
            driver.findElement(By.xpath("//input[@id='repeatedPassword']")).sendKeys("abccd");

            driver.findElement(By.xpath("//input[@value='Register']")).sendKeys(Keys.ENTER);

            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(text(),'Your account was created successfully. You are now logged in.')]")
            ));

            String display = successMessage.getText();
            System.out.println(display);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
