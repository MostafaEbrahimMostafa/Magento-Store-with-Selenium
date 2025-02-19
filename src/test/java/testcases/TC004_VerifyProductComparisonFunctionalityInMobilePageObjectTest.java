package testcases;


import base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ui.pageobjects.MobilePageObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

public class TC004_VerifyProductComparisonFunctionalityInMobilePageObjectTest extends BaseTest {

    MobilePageObject mobilePageObject;
    SoftAssert softAssert;

    @Story("Verify That You Are Able To Compare Two Products")
    @Description("Test Steps:\n\n" +
            "1- Goto http://live.techpanda.org/\n" +
            "2- Click On 'Mobile' Menu\n" +
            "3- In Mobile Products List, Click On 'Add To Compare' For 2 Mobiles\n" +
            "4- Click On 'Compare' Button.\n" +
            "5- Verify The Pop-Up Window And Check That The Products Are Reflected In It.\n" +
            "6- Close The Popup Window.\n\n" +
            "Expected Result:\n\n" +
            "Phone 1 - Sony Xperia\n" +
            "Phone 2 - Iphone\n" +
            "- A Popup Window Opens With Heading As 'Compare Products' And The Selected Products Are Present In It.\n" +
            "- Popup Window Is Closed.\n\n" +
            "Actual Result:\n\n" +
            "- Compare Products Are Working Correctly .\n" +
            "- Popup Window Is Closed Successfully.\n\n" +
            "Priority:\n\n" +
            "- Medium"
    )

    @Test(priority = 4)
    public void Validate_That_You_Are_Able_To_Compare_Two_Product()
    {
        softAssert = new SoftAssert();
        mobilePageObject = new MobilePageObject(driver);
        mobilePageObject.click_on_mobile_category();
        mobilePageObject = new MobilePageObject(driver);
        mobilePageObject.click_on_first_compare();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        String message = driver.findElement(By.xpath("//li[@class='success-msg']//ul//li")).getText();
        softAssert.assertEquals(message,"The product IPhone has been added to comparison list.");

        mobilePageObject.click_on_second_compare();
        String message1 = driver.findElement(By.xpath("//li[@class='success-msg']//ul//li")).getText();
        softAssert.assertEquals(message1,"The product Sony Xperia has been added to comparison list.");

        mobilePageObject.click_on_compare_button();

        // Switch to new tab
        Set<String> windowHandles = driver.getWindowHandles();
        Iterator<String> iterator = windowHandles.iterator();
        String mainWindow = iterator.next();
        String newTab = iterator.next();
        driver.switchTo().window(newTab);
        driver.manage().window().maximize();

        // Validate the content in the new tab (example)
        String actual_value = driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div[1]/h1")).getText();
        driver.manage().window().maximize();

        // Take Screen Shot For Actual Result.
        TakesScreenshot ts = (TakesScreenshot) driver;
        File sourcefile = ts.getScreenshotAs(OutputType.FILE);
        File targetfile = new File("C:\\Users\\M.ELHADAF\\IdeaProjects\\MagentoProject\\ScreenShots\\CompareProducts.png");
        sourcefile.renameTo(targetfile); // copy sourcefile to target file
        try {
            InputStream is = new FileInputStream(targetfile);
            Allure.addAttachment("CompareProducts.png",is);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        softAssert.assertEquals(actual_value,"COMPARE PRODUCTS");

        // Locate and click the "Close Window" button
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement closeButton =driver.findElement(By.xpath("//*[@id=\"top\"]/body/div[1]/div[2]/button/span/span"));
        closeButton.click();

        // Switch back to the main tab
        driver.switchTo().window(mainWindow); // Return to the main tab


        driver.findElement(By.xpath("//img[@class='large']")).click();
        softAssert.assertAll();
    }
}
