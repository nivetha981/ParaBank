package parabank;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class paraAccountOverview {

WebDriver driver;
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://parabank.parasoft.com");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
    }
	public void paralogin()
	{
		
		try {
            // Maximize the window and navigate to the website
          
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


            // Check for successful login or error message
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
                if (successText.contains("Welcome")) {
                    System.out.println("Login Successful, " + successText+" !");
                } else {
                    System.out.println("Unexpected login outcome. Content: " + successText);
                }
            }
        } catch (Exception e) 
		{
            e.printStackTrace();
        } finally
		{
            // Close the browser
        }
	}
	
void accountoverview()
{
	
		// ACCOUNTOVERVIEW
		WebElement table = driver.findElement(By.xpath("//table[@id='accountTable']"));

		// Print the headers from the <thead>
		List<WebElement> headers = table.findElements(By.xpath(".//thead/tr/th"));
		for (WebElement header : headers) {
			System.out.print(header.getText() + "  "); // Print header text separated by space
		}
		System.out.println(); // Move to the next line after printing headers

		// Locate all rows in the <tbody>
		List<WebElement> rows = table.findElements(By.xpath(".//tbody/tr"));

		// Loop through each row
		for (WebElement row : rows) {
			// Locate all cells in the current row
			List<WebElement> cells = row.findElements(By.xpath(".//td"));

			// Skip empty rows (no <td> elements)
			if (cells.isEmpty()) {
				continue;
			}

			// Print all cell values in the current row
			for (WebElement cell : cells) {
				System.out.print(cell.getText() + "   "); // Print cell text separated by space
			}
			System.out.println(); // Move to the next line after each row
		}
		driver.quit();
}
public static void main(String args[]) {
	paraAccountOverview obj=new paraAccountOverview();
	obj.setup();
	obj.paralogin();
	obj.accountoverview();
}

}
