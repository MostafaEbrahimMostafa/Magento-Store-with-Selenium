package testcases;

import base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.manager.SeleniumManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ui.pageobjects.HomePageObject;
import ui.pageobjects.LoginPageObject;
import ui.pageobjects.MobilePageObject;
import utils.ConfigurationUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Test case to validate sorting functionality of items on the Mobile page by Name.
 * This test ensures that products are correctly sorted when the "Name" sorting option is selected.
 */
public class TC001_ValidateItemSortingByNameInMobilePageObjectTest extends BaseTest {

    private HomePageObject homePageObject;
    private LoginPageObject loginPageObject;
    private MobilePageObject mobilePageObject;
    private String email, password;
    private List<String> sortedNames;
    private SoftAssert softAssert;

    /**
     * Setup method to prepare the test environment.
     * Logs in the user and initializes the necessary page objects.
     *
     * @throws InterruptedException if the thread sleep is interrupted
     */
    public void initializeTestEnvironment() throws InterruptedException {
        // Initialize the page object for the home page and navigate to the login page
        homePageObject = new HomePageObject(driver);
        homePageObject.click_on_account();
        homePageObject.click_on_my_account();

        // Initialize the login page object and retrieve login credentials from configuration
        loginPageObject = new LoginPageObject(driver);
        email = ConfigurationUtils.getInstance().getEmail();
        password = ConfigurationUtils.getInstance().getPassword();

        // Perform login using the credentials
        loginPageObject.Login(email, password);

        // Adding a wait for the login to complete this for SaveAny Button.
        Thread.sleep(1000);

        
        // Initialize the mobile page object for further interactions
        mobilePageObject = new MobilePageObject(driver);
        // Initialize SoftAssert for partial assertions
        softAssert = new SoftAssert();
    }


    /**
     * Test to validate sorting of products by name on the Mobile page.
     * The test checks that the list of products is correctly sorted when the "Name" sorting option is selected.
     */

    @Story("Verify Item Sorting by Name on the Mobile Page")
    @Description("Test Steps:\n\n" +
            "1- Go To Url ( http://live.techpanda.org/ ) \n" +
            "2- Verify Title Of The Page Is THIS IS DEMO SITE. \n" +
            "3- Click On Mobile Category.\n" +
            "4- Verify Title Of The Page Is MOBILE.\n" +
            "5- In List Of All Mobiles, Select 'SORT BY' Dropdown As 'Name'.\n\n" +
            "Expected Result:\n\n" +
            "- Verify All Products Are Sorted By Name Correctly \n\n" +
            "Actual Result:\n\n" +
            "- All Products Sorted By Name \n\n" +
            "Priority:\n\n" +
            "- High"
    )
    @Test
    public void Validate_Item_In_Mobile_List_Page_Can_Be_Sorted_By_Name() throws InterruptedException {

        initializeTestEnvironment();
        // Click on the mobile category and open the sort dropdown
        mobilePageObject.click_on_mobile_category();
        mobilePageObject.click_on_sort_box();
        mobilePageObject.select_name_sort();

        // Collect product names into the productNames list
        List<String> productNames = new ArrayList<>();
        for (WebElement product : mobilePageObject.locateProductNameElements()) {
            productNames.add(product.getText().trim());
        }

        // Create a copy of productNames and sort it for comparison
        sortedNames = new ArrayList<>(productNames);
        Collections.sort(sortedNames);

        // Verify that the list of product names is correctly sorted
        Assert.assertEquals(productNames, sortedNames, "The products are not sorted by name correctly.");

        // Take Screen Shot For Actual Result.
        WebElement element = driver.findElement(By.xpath("//div[@class='category-products']"));
        File sourcefile = element.getScreenshotAs(OutputType.FILE);
        File targetfile = new File("C:\\Users\\M.ELHADAF\\IdeaProjects\\MagentoProject\\ScreenShots\\SortedItems.png");
        sourcefile.renameTo(targetfile); // copy sourcefile to target file
        try {
            InputStream is = new FileInputStream(targetfile);
            Allure.addAttachment("SortedItems.png",is);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }








}
