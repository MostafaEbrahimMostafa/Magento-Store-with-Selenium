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
import ui.pageobjects.RegisterPageObject;
import ui.pageobjects.TvPageObject;
import ui.pageobjects.WishListPageObject;
import utils.ConfigurationUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Duration;

public class TC005_VerifyAccountCreationAndWishlistSharingViaEmailTest extends BaseTest {

    HomePageObject homePageObject;
    RegisterPageObject registerPageObject;
    TvPageObject tvPageObject;
    WishListPageObject wishListPageObject;
    SoftAssert softAssert;
    String Fn , Mn , Ln , Ue , Up , Ucp ;

    /**
     * Setup method to prepare the test environment.
     * Logs in the user and initializes the necessary page objects.
     *
     */
    @BeforeClass
    public void initializeTestEnvironment() {
        homePageObject = new HomePageObject(driver); // Use the shared driver
        homePageObject.click_on_account();
        driver.findElement(By.xpath("//a[normalize-space()='Log Out']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
        homePageObject.click_on_account();
        homePageObject.click_on_my_account();
        registerPageObject = new RegisterPageObject(driver);
        registerPageObject.click_on_createAccount();
        softAssert = new SoftAssert();
    }

    @Story("Verify That You Can Create An Account On The E-Commerce Site And Share Wishlist To Others Using Email")
    @Description("Test Steps:\n\n" +
            "1- Goto http://live.techpanda.org/\n" +
            "2- Click On My Account Link\n" +
            "3- Click Create Account Link And Fill New User Information Except Email Id\n" +
            "4- Click Register\n" +
            "5- Verify Registration Is Done\n" +
            "6- Go To TV Menu\n" +
            "7- Add Product In Your Wishlist\n" +
            "8- Click Share Wishlist\n" +
            "9- In Next Page Enter Email And A Message And Click Share Wishlist\n" +
            "10- Check Wishlist Is Shared.\n\n" +
            "Expected Result:\n\n" +
            "-> Account Registration Functionality IS Working Correctly\n" +
            "-> Wishlist Shared Functionality IS Working Correctly\n\n" +
            "Actual Result:\n\n" +
            "-> Account Registration Successfully \n" +
            "-> Wishlist Shared Successfully\n\n" +
            "Priority:\n\n" +
            "-> High"
    )
    @Test
    public void Validate_You_Can_Create_Account_In_Ecommerce_Site_And_Can_Share_Wishlist_To_Other_People_Using_Email() throws InterruptedException {
        Fn = ConfigurationUtils.getInstance().get_firstName();
        Ln = ConfigurationUtils.getInstance().get_lastName();
        Mn = ConfigurationUtils.getInstance().get_middleName();
        Ue = ConfigurationUtils.getInstance().getUserEmail();
        Up = ConfigurationUtils.getInstance().getUserPassword();
        Ucp = ConfigurationUtils.getInstance().getUserConfirmPassword();

        registerPageObject.EnterNewUserInformation(Fn, Mn, Ln,
                    Ue, Up, Ucp);

        registerPageObject.click_on_register();

            // Wait for the page to load with explicit wait
            Thread.sleep(2000);


        // Take Screen Shot For Actual Result.
        TakesScreenshot ts = (TakesScreenshot) driver;
        File sourcefile = ts.getScreenshotAs(OutputType.FILE);
        File targetfile = new File("C:\\Users\\M.ELHADAF\\IdeaProjects\\MagentoProject\\ScreenShots\\RegistrationSuccessfully.png");
        sourcefile.renameTo(targetfile); // copy sourcefile to target file
        try {
            InputStream is = new FileInputStream(targetfile);
            Allure.addAttachment("RegistrationSuccessfully.png",is);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

            String actual = driver.findElement(By.xpath("//span[normalize-space()='Thank you for registering with Main Website Store.']")).getText();
            softAssert.assertEquals(actual, "Thank you for registering with Main Website Store.");

            registerPageObject.click_on_tv_category();
            tvPageObject = new TvPageObject(driver);

            tvPageObject.click_on_add_to_wishlist();
            wishListPageObject = new WishListPageObject(driver);

            String act = driver.findElement(By.xpath("//span[contains(text(),'LG LCD has been added to your wishlist. Click')]")).getText();
            softAssert.assertEquals(act, "LG LCD has been added to your wishlist. Click here to continue shopping.");

            wishListPageObject.click_on_share();

        // Wait for the page to load with explicit wait
        Thread.sleep(2000);

            wishListPageObject.enterEmail("mostafakady@gmail.com");
            wishListPageObject.enterMessage("this product is very good with few price , so I recommend it for you");

            wishListPageObject.click_on_shareButton();

        // Wait for the page to load with explicit wait
        Thread.sleep(2000);

        // Take Screen Shot For Actual Result.
        TakesScreenshot ts1 = (TakesScreenshot) driver;
        File sourcefile1 = ts1.getScreenshotAs(OutputType.FILE);
        File targetfile1 = new File("C:\\Users\\M.ELHADAF\\IdeaProjects\\MagentoProject\\ScreenShots\\WishlistSharedSuccessfully.png");
        sourcefile1.renameTo(targetfile1); // copy sourcefile to target file
        try {
            InputStream is1 = new FileInputStream(targetfile1);
            Allure.addAttachment("WishlistSharedSuccessfully.png",is1);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String str = driver.findElement(By.xpath("//span[normalize-space()='Your Wishlist has been shared.']")).getText();
        softAssert.assertEquals(str, "Your Wishlist has been shared.");

        softAssert.assertAll();

        wishListPageObject.click_on_account();
        wishListPageObject.click_on_logout();
        driver.findElement(By.xpath("//img[@class='large']")).click();

    }







}
