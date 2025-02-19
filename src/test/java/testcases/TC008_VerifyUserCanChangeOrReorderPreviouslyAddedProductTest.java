package testcases;


import base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ui.pageobjects.*;
import utils.ConfigurationUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Duration;

public class TC008_VerifyUserCanChangeOrReorderPreviouslyAddedProductTest extends BaseTest {


    HomePageObject homePageObject;
    OrderPageObject orderPageObject;
    LoginPageObject loginPageObject;
    PurchasePageObject purchasePageObject;
    CheckoutPageObject checkoutPageObject;
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
        homePageObject = new HomePageObject(driver);
        homePageObject.click_on_my_order();
        orderPageObject = new OrderPageObject(driver);
        softAssert = new SoftAssert();
    }

    @Story("Verify That You Are Able To Change Or Reorder A Previously Added Product")
    @Description("Test Steps:\n\n" +
            "1- Goto http://live.techpanda.org/\n" +
            "2- Click On My Account Link\n" +
            "3- Login In Application Using Previously Created Credential\n" +
            "4- Click On 'Reorder' Link, Change Qty & Click Update\n" +
            "5- Verify Grand Total Is Changed\n" +
            "6- Complete Billing & Shipping Information\n" +
            "7- Verify Order Is Generated And Note The Order Number Qty - 10.\n\n" +
            "Expected Result:\n\n" +
            "- Grand Total Is Changed\n" +
            "- Order Number Is Generated\n\n" +
            "Actual Result:\n\n" +
            "1) Grand Total Is Changed Successfully\n" +
            "2) Order Number Is Generated Successfully\n\n" +
            "Priority:\n\n" +
            "- High"
    )

    @Test
    public void Validate_You_Are_Able_To_Change_Or_Reorder_Previously_Added_Product() throws InterruptedException {
        orderPageObject.click_on_reorder();
        orderPageObject.enter_quantity("10");
        orderPageObject.click_on_update();
        Thread.sleep(1000);

        WebElement element = driver.findElement(By.id("shopping-cart-totals-table"));
        File sourcefile = element.getScreenshotAs(OutputType.FILE);
        File targetfile = new File("C:\\Users\\M.ELHADAF\\IdeaProjects\\MagentoProject\\ScreenShots\\UpdatedTotalCost.png");
        sourcefile.renameTo(targetfile); // copy sourcefile to target file
        try {
            InputStream is = new FileInputStream(targetfile);
            Allure.addAttachment("UpdatedTotalCost.png",is);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String str1= driver.findElement(By.xpath("//strong//span[@class='price'][normalize-space()='$6,150.00']")).getText();
        softAssert.assertEquals(str1,"$6,150.00");

        purchasePageObject = new PurchasePageObject(driver);
        purchasePageObject.click_on_checkout_button();


        checkoutPageObject = new CheckoutPageObject(driver);

        driver.findElement(By.xpath("//span[contains(text(),'Enter a New Address')]")).click();
        Thread.sleep(1000);
        purchasePageObject.getData();


        checkoutPageObject.enterShippingAddress(purchasePageObject.fn, purchasePageObject.mn, purchasePageObject.ln, purchasePageObject.co, purchasePageObject.tp,
                purchasePageObject.fx, purchasePageObject.ad1, purchasePageObject.ad2, purchasePageObject.cit, purchasePageObject.stat
                , purchasePageObject.zi, purchasePageObject.count);

        checkoutPageObject.click_on_save_address_button();
        Thread.sleep(1000);

        checkoutPageObject.click_on_continue_to_shipping();
        Thread.sleep(1000);

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

        checkoutPageObject.click_on_continue_to_billing();
        Thread.sleep(1000);

        checkoutPageObject.click_on_check_money_order();
        checkoutPageObject.click_on_continue_to_review();
        Thread.sleep(1000);

        checkoutPageObject.click_on_place_order();
        Thread.sleep(1000);


        // Take Screen Shot For Actual Result.
        TakesScreenshot ts = (TakesScreenshot) driver;
        sourcefile = ts.getScreenshotAs(OutputType.FILE);
        targetfile = new File("C:\\Users\\M.ELHADAF\\IdeaProjects\\MagentoProject\\ScreenShots\\ChangedOrderPurshase.png");
        sourcefile.renameTo(targetfile); // copy sourcefile to target file
        try {
            InputStream is = new FileInputStream(targetfile);
            Allure.addAttachment("ChangedOrderPurshase.png",is);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String s = driver.findElement(By.xpath("//div[@class='page-title']")).getText();
        softAssert.assertEquals(s,"ORDER SUCCESS");

        String s2 = driver.findElement(By.xpath("//p[@data-role='order-numbers']")).getText();
        softAssert.assertTrue(s2.contains("Your order number is"));

        softAssert.assertAll();

        driver.findElement(By.xpath("//span[contains(text(),'Continue Shopping')]")).click();

        checkoutPageObject.click_on_account();
        checkoutPageObject.click_on_logout();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
    }


}
