package testcases;


import base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ui.pageobjects.HomePageObject;
import ui.pageobjects.LoginPageObject;
import ui.pageobjects.PurchasePageObject;
import utils.ConfigurationUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TC009_VerifyDiscountCouponFunctionalityTest extends BaseTest {


    HomePageObject homePageObject;
    PurchasePageObject purchasePageObject;
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
        loginPageObject.Login(email , password);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[normalize-space()='Mobile']")).click();
        driver.findElement(By.xpath("//li[1]//div[1]//div[3]//button[1]")).click();
        purchasePageObject = new PurchasePageObject(driver);
        softAssert = new SoftAssert();
    }

    @Story("Verify That The Discount Coupon Works Correctly")
    @Description("Test Steps:\n\n" +
            "1- Goto http://live.techpanda.org/\n" +
            "2- Go To Mobile And Add Iphone To Cart\n" +
            "3- Enter Coupon Code Correctly , Coupon Code Is ( GURU50 ) , Make 5% Discount\n" +
            "4- Verify The Discount Generated.\n\n" +
            "Expected Result:\n\n" +
            "- Price Is Discounted By 5%\n\n" +
            "Actual Result:\n\n" +
            "- Discount Coupon Works Correctly \n\n" +
            "Priority:\n\n" +
            "- Medium"
    )
    @Test
    public void Validate_Discount_Coupon_Works_Correctly() throws InterruptedException {

        // GURU50 make 5% discount
        purchasePageObject.enter_couponCode("GURU50");
        purchasePageObject.click_on_apply_btn();
        Thread.sleep(2000);

        purchasePageObject.select_country("United States");
        purchasePageObject.select_state("New York");
        purchasePageObject.enter_zibCode("542896");
        purchasePageObject.click_on_estimate_button();
        Thread.sleep(2000);



        driver.findElement(By.xpath("//input[@id='s_method_flatrate_flatrate']")).click();

        purchasePageObject.click_on_update_button();
        Thread.sleep(2000);


        String total = driver.findElement(By.xpath("//strong//span[@class='price'][normalize-space()='$500.00']")).getText();



        // Scroll to a specific element
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(By.id("shopping-cart-totals-table"));
        js.executeScript("arguments[0].scrollIntoView(true);", element);

        File sourcefile = element.getScreenshotAs(OutputType.FILE);
        File targetfile = new File("C:\\Users\\M.ELHADAF\\IdeaProjects\\MagentoProject\\ScreenShots\\DiscountCoupon.png");
        sourcefile.renameTo(targetfile); // copy sourcefile to target file
        try {
            InputStream is = new FileInputStream(targetfile);
            Allure.addAttachment("DiscountCoupon.png",is);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            Assert.assertEquals(total,"$475.00");
        } catch (Exception e) {
            throw new RuntimeException("Discount Coupon Functionality Isn't Working Correctly Because Total price Still $500.00");
        }
    }
}
