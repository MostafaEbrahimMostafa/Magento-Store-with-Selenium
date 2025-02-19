package base;

import factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class BaseTest {

    public static WebDriver driver;

    @BeforeTest
    public void setDriver() {
        if (driver == null) {
            driver = new DriverFactory().initializeDriver();
        }
    }

//    @AfterSuite()
//    public void QuitDriver() {
//        driver.quit();
//    }
}
