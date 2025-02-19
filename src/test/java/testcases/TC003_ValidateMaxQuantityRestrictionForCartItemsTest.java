package testcases;

import base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ui.pageobjects.DetailsPageObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TC003_ValidateMaxQuantityRestrictionForCartItemsTest extends BaseTest {


    SoftAssert softAssert;
    DetailsPageObject detailsPage;

    @Story("Verify That You Cannot Add More Products To The Cart Than What Is Available In Store")
    @Description("Test Steps:\n\n" +
            "1- Go To ( http://live.techpanda.org/ )\n" +
            "2- Click On 'Mobile' Menu\n" +
            "3- In The List Of All Mobiles, Click On 'Add To Cart' For Sony Xperia Mobile.\n" +
            "4- Change 'Qty' Value To 1000 And Click 'Update' Button.\n" +
            "5- Verify The Error Message.\n" +
            "6- Then Click On 'Empty Cart' Link In The Footer Of The List Of All Mobiles.\n" +
            "7- Verify The Cart Is Empty.\n\n" +
            "Expected Result:\n\n" +
            "- On Clicking The Update Button, An Error Is Shown 'The Requested Quantity For \"Sony Xperia\" Is Not Available.'\n" +
            "- On Clicking The Empty Cart Button, A Message 'Shopping Cart Is Empty' Is Shown.\n\n" +
            "Actual Result:\n\n" +
            "- An Error Is Shown 'The Requested Quantity For \"Sony Xperia\" Is Not Available.'\n" +
            "- A Message 'Shopping Cart Is Empty' Is Shown.\n\n" +
            "Priority:\n\n" +
            "- High"
    )
    @Test(priority = 3)
    public void Validate_That_You_Cannot_Add_More_Product_In_Cart_Than_The_Product_Available_In_Store() throws InterruptedException {
        detailsPage = new DetailsPageObject(driver);
        softAssert = new SoftAssert();
        detailsPage.click_on_add_to_cart_button();

        Thread.sleep(2000);


        detailsPage.enter_quantity("1000");
        detailsPage.click_on_update_button();


        Thread.sleep(2000);

        try {
            // Take Screen Shot For Actual Result.
            WebElement element = driver.findElement(By.xpath("//tbody//tr[@class='first last odd']"));
            File sourcefile = element.getScreenshotAs(OutputType.FILE);
            File targetfile = new File("C:\\Users\\M.ELHADAF\\IdeaProjects\\MagentoProject\\ScreenShots\\ProductQuantity.png");
            sourcefile.renameTo(targetfile); // copy sourcefile to target file
            InputStream is = new FileInputStream(targetfile);
            Allure.addAttachment("ProductQuantity.png",is);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String error_message = driver.findElement(By.xpath("//p[@class='item-msg error']")).getText();
        softAssert.assertEquals(error_message, "* The maximum quantity allowed for purchase is 500.");

        String error_message1 = driver.findElement(By.xpath("//span[contains(text(),'Some of the products cannot be ordered in requeste')]")).getText();
        softAssert.assertEquals(error_message1,"Some of the products cannot be ordered in requested quantity.");
        detailsPage.click_on_emptyCart_button();

        Thread.sleep(2000);

        String actual = driver.findElement(By.xpath("//h1[normalize-space()='Shopping Cart is Empty']")).getText();
        softAssert.assertEquals(actual,"SHOPPING CART IS EMPTY");

        softAssert.assertAll();
    }
}
