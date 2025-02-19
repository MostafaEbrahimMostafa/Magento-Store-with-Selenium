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

public class TC006_VerifyProductPurchaseUsingRegisteredEmailTest extends BaseTest {

    HomePageObject homePageObject;
    PurchasePageObject purchasePageObject;
    LoginPageObject loginPageObject;
    WishListPageObject wishListPageObject;
    CheckoutPageObject checkoutPageObject;
    String email , password ;
    SoftAssert softAssert;

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
        homePageObject.click_on_account();
        homePageObject.click_on_wishlist();
        wishListPageObject = new WishListPageObject(driver);
        wishListPageObject.click_on_add_to_cart();
        purchasePageObject = new PurchasePageObject(driver);
        softAssert = new SoftAssert();
    }

    @Story("Verify That The User Is Able To Purchase A Product Using A Registered Email Id")
    @Description("Test Steps:\n\n" +
            "1- Go To ( http://live.techpanda.org/ )\n" +
            "2- Click On My Account Link\n" +
            "3- Login In Application Using Previously Created Credential\n" +
            "4- Click On My Wishlist Link\n" +
            "5- In Next Page, Click Add To Cart Link\n" +
            "6- Click Proceed To Checkout\n" +
            "7- Enter Shipping Information\n" +
            "8- Click Estimate\n" +
            "9- Verify Shipping Cost Generated\n" +
            "10- Select Shipping Cost, Update Total\n" +
            "11- Verify Shipping Cost Is Added To Total\n" +
            "12- Click 'Proceed To Checkout'\n" +
            "13- Enter Billing Information\n" +
            "14- In Shipping Method, Click Continue\n" +
            "15- In Payment Information, Select 'Check/Money Order' Radio Button. Click Continue\n" +
            "16- Click 'Place Order' Button\n" +
            "17- Verify Order Is Generated. Note The Order Number.\n\n" +
            "Expected Result:\n\n" +
            "- Flat Rate Shipping Of $5 Is Generated.\n" +
            "- Shipping Cost Is Added To Total Product Cost.\n" +
            "- Order Is Placed And Order Number Is Generated.\n\n" +
            "Actual Result:\n\n" +
            "- Flat Rate Shipping Of $5 Is Generated Successfully.\n" +
            "- Total Product Cost IS Shown.\n" +
            "- Order Is Placed Successfully.\n\n" +
            "Priority:\n\n" +
            "- High"
    )
    @Test
    public void Validate_User_Is_Able_To_Purchase_Product_Using_Registered_Email_Id() throws InterruptedException {
        purchasePageObject.select_country("United States");
        purchasePageObject.select_state("New York");
        purchasePageObject.enter_zibCode("542896");
        purchasePageObject.click_on_estimate_button();
        Thread.sleep(2000);

        // Take Screen Shot For Actual Result.
        WebElement element = driver.findElement(By.xpath("//dl[@class='sp-methods']"));
        File sourcefile = element.getScreenshotAs(OutputType.FILE);
        File targetfile = new File("C:\\Users\\M.ELHADAF\\IdeaProjects\\MagentoProject\\ScreenShots\\FlatRate.png");
        sourcefile.renameTo(targetfile); // copy sourcefile to target file
        try {
            InputStream is = new FileInputStream(targetfile);
            Allure.addAttachment("FlatRate.png",is);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String flat = purchasePageObject.getFlat_price().getText().replace("$", "").replace(",", "").trim();
        double f = Double.parseDouble(flat);

        String price = driver.findElement(By.xpath("//td[@class='product-cart-total']//span[@class='price'][normalize-space()='$615.00']")).getText().replace("$", "").replace(",", "").trim();
        double p = Double.parseDouble(price);

        driver.findElement(By.xpath("//input[@id='s_method_flatrate_flatrate']")).click();
        purchasePageObject.click_on_update_button();
        Thread.sleep(2000);

        element = driver.findElement(By.id("shopping-cart-totals-table"));
        sourcefile = element.getScreenshotAs(OutputType.FILE);
        targetfile = new File("C:\\Users\\M.ELHADAF\\IdeaProjects\\MagentoProject\\ScreenShots\\TotalCost.png");
        sourcefile.renameTo(targetfile); // copy sourcefile to target file
        try {
            InputStream is = new FileInputStream(targetfile);
            Allure.addAttachment("TotalCost.png",is);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String total = driver.findElement(By.xpath("//span[normalize-space()='$620.00']")).getText().replace("$", "").replace(",", "").trim();
        double t = Double.parseDouble(total);

        softAssert.assertEquals(t,p+f);

        purchasePageObject.click_on_checkout_button();


        checkoutPageObject = new CheckoutPageObject(driver);

        Thread.sleep(2000);
        purchasePageObject.getData();


        checkoutPageObject.enterShippingAddress(purchasePageObject.fn, purchasePageObject.mn, purchasePageObject.ln, purchasePageObject.co, purchasePageObject.tp,
                purchasePageObject.fx, purchasePageObject.ad1, purchasePageObject.ad2, purchasePageObject.cit, purchasePageObject.stat
                , purchasePageObject.zi, purchasePageObject.count);

        checkoutPageObject.click_on_save_address_button();
        Thread.sleep(2000);

        checkoutPageObject.click_on_continue_to_shipping();
        Thread.sleep(2000);

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
        Thread.sleep(2000);

        checkoutPageObject.click_on_check_money_order();
        checkoutPageObject.click_on_continue_to_review();
        Thread.sleep(2000);

        checkoutPageObject.click_on_place_order();
        Thread.sleep(2000);

         // Take Screen Shot For Actual Result.
         TakesScreenshot ts = (TakesScreenshot) driver;
         sourcefile = ts.getScreenshotAs(OutputType.FILE);
         targetfile = new File("C:\\Users\\M.ELHADAF\\IdeaProjects\\MagentoProject\\ScreenShots\\PurshasedOrder.png");
         sourcefile.renameTo(targetfile); // copy sourcefile to target file
         try {
            InputStream is = new FileInputStream(targetfile);
            Allure.addAttachment("PurshasedOrder.png",is);
             } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
         }

        String s = driver.findElement(By.xpath("//div[@class='page-title']")).getText();
        softAssert.assertEquals(s,"ORDER SUCCESS");

        String s2 = driver.findElement(By.xpath("//p[@data-role='order-numbers']")).getText();
        softAssert.assertTrue(s2.contains("Your order number is"));

        driver.findElement(By.xpath("//span[contains(text(),'Continue Shopping')]")).click();

        softAssert.assertAll();

        checkoutPageObject.click_on_account();
        checkoutPageObject.click_on_logout();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
    }




}
