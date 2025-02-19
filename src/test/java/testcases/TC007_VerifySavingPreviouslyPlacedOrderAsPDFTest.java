package testcases;


import base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ui.pageobjects.HomePageObject;
import ui.pageobjects.LoginPageObject;
import ui.pageobjects.OrderPageObject;
import utils.ConfigurationUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Set;

public class TC007_VerifySavingPreviouslyPlacedOrderAsPDFTest extends BaseTest {

    HomePageObject homePageObject;
    OrderPageObject orderPageObject;
    LoginPageObject loginPageObject;
    SoftAssert softAssert ;
    String email , password ;

    @BeforeClass
    public void initializeTestEnvironment() throws InterruptedException {
        homePageObject = new HomePageObject(driver);
        homePageObject.click_on_account();
        homePageObject.click_on_my_account();
        loginPageObject = new LoginPageObject(driver);
        email = ConfigurationUtils.getInstance().getUserEmail();
        password = ConfigurationUtils.getInstance().getUserPassword();
        loginPageObject.Login(email,password);
        Thread.sleep(2000);
        homePageObject.click_on_my_order();
    }

    @Story("Verify That You Will Be Able To Save A Previously Placed Order As A PDF File")
    @Description("Test Steps:\n\n" +
            "1- Goto http://live.techpanda.org/\n" +
            "2- Click On My Account Link\n" +
            "3- Login In Application Using Previously Created Credential\n" +
            "4- Click On 'My Orders'\n" +
            "5- Click On 'View Order'\n" +
            "6- Verify The Previously Created Order Is Displayed In 'Recent Orders' Table And Status Is Pending\n" +
            "7- Click On 'Print Order' Link\n" +
            "8- Verify Order Is Saved As Pdf.\n\n" +
            "Expected Result:\n\n" +
            "- Previously Created Order Is Displayed In 'Recent Orders' Table And Status Is Pending\n" +
            "- Order Is Saved As Pdf file.\n\n" +
            "Actual Result:\n\n" +
            "- Previously Created Order Is Displayed In 'Recent Orders' Successfully \n" +
            "- Order Is Saved As Pdf file Successfully.\n\n" +
            "Priority:\n\n" +
            "- Medium"
    )

    @Test
    public void Validate_That_You_Will_Be_Able_To_Save_Previously_Placed_Order_As_A_Pdf_File()
    {
        // Take Screen Shot For Actual Result.
        TakesScreenshot ts = (TakesScreenshot) driver;
        File sourcefile = ts.getScreenshotAs(OutputType.FILE);
        File targetfile = new File("C:\\Users\\M.ELHADAF\\IdeaProjects\\MagentoProject\\ScreenShots\\RecentOrders.png");
        sourcefile.renameTo(targetfile); // copy sourcefile to target file
        try {
            InputStream is = new FileInputStream(targetfile);
            Allure.addAttachment("RecentOrders.png",is);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        orderPageObject = new OrderPageObject(driver);
        softAssert = new SoftAssert();

        orderPageObject.click_on_view_order();
        String act = driver.findElement(By.xpath("//div[@class='box-content']/address")).getText();
        String str = "Mostafa Ebrahim Mostafa\n" +
                "ASU\n" +
                "abc7895\n" +
                "def7895\n" +
                "New York, New York, 542896\n" +
                "United States\n" +
                "T: 01005747258\n" +
                "F: 7895";

        softAssert.assertEquals(act,str);

        orderPageObject.click_on_print_order();
        // Step 4: Handle the new tab
        String originalWindow = driver.getWindowHandle(); // Store the original window
        Set<String> allWindows = driver.getWindowHandles(); // Get all open windows/tabs

        // Switch to the new tab
        for (String window : allWindows) {
            if (!window.equals(originalWindow)) {
                driver.switchTo().window(window); // Switch to the new tab
                break;
            }
        }

        // (Optional) Verify something in the new tab if needed

        // Step 5: Close the new tab and switch back to the original tab
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        // Take Screen Shot For Actual Result.
        TakesScreenshot ts1 = (TakesScreenshot) driver;
        File sourcefile1 = ts1.getScreenshotAs(OutputType.FILE);
        File targetfile1 = new File("C:\\Users\\M.ELHADAF\\IdeaProjects\\MagentoProject\\ScreenShots\\OrderPrinting.png");
        sourcefile1.renameTo(targetfile1); // copy sourcefile to target file
        try {
            InputStream is = new FileInputStream(targetfile1);
            Allure.addAttachment("OrderPrinting.png",is);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        driver.close(); // Close the current tab (new tab)
        driver.switchTo().window(originalWindow); // Switch back to the original window

        // Step 6: Continue with the rest of the test
        softAssert.assertAll();

        orderPageObject.click_on_account();
        orderPageObject.click_on_logout();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));

    }

}
