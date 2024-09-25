package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

public class selenium {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chormedriver\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-web-security");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][]{
            {"testuser", "testpassword", true}, // Dữ liệu hợp lệ
            {"wronguser", "wrongpassword", false} // Dữ liệu không hợp lệ
        };
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, boolean expectedResult) {
        File file = new File("src/main/resources/form.html");
        String filePath = "file:///" + file.getAbsolutePath();
        driver.get(filePath);

        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.cssSelector("input[type='submit']"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        submitButton.click();

        // Giả định bạn có cách kiểm tra đăng nhập thành công hay thất bại
        boolean loginSuccess = driver.getTitle().contains("Welcome");
        Assert.assertEquals(loginSuccess, expectedResult);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
