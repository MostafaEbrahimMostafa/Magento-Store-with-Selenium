package testcases;


import base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pageobjects.DetailsPageObject;
import ui.pageobjects.MobilePageObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TC002_VerifyProductPriceConsistencyBetweenListAndDetailsPageTest extends BaseTest {


    MobilePageObject mobilePageObject;
    DetailsPageObject detailsPage;


    @Story("Verify That The Cost Of The Product In List Page And Details Page Are Equal")
    @Description("Test Steps:\n\n" +
            "1- Go To ( http://live.techpanda.org/ )\n" +
            "2- Click On 'MOBILE' Menu\n" +
            "3- In The List Of All Mobiles, Read The Cost Of Sony Xperia Mobile. Note This Value.\n" +
            "4- Click On Sony Xperia Mobile.\n" +
            "5- Read The Sony Xperia Mobile From The Details Page.\n" +
            "6- Compare The Value In Step 3 & Step 5.\n\n" +
            "Expected Result:\n\n" +
            "- Product Value In List And Details Page Should Be Equal ($100)\n\n" +
            "Actual Result:\n\n" +
            "- Product Value In List And Details Page Are Equal.\n\n" +
            "Priority:\n\n" +
            "- High"
    )

    @Test
    public void Validate_That_Cost_Of_Product_In_List_Page_And_Details_Page_Are_Equal()
    {
        mobilePageObject = new MobilePageObject(driver);
        WebElement el = driver.findElement(By.xpath("//li[@class='item last'][3]//div[@class='product-info']"));
        File sourcefile = el.getScreenshotAs(OutputType.FILE);
        String price =  mobilePageObject.getProduct_price().getText();

        mobilePageObject.click_on_mobile_product();
        detailsPage = new DetailsPageObject(driver);
        String actual_price =  detailsPage.getPrice().getText();
        Assert.assertEquals(actual_price,price);

        // Take Screen Shot For Actual Result.
        WebElement element = driver.findElement(By.xpath("//div[@class='product-view']"));

        File targetfile = new File("C:\\Users\\M.ELHADAF\\IdeaProjects\\MagentoProject\\ScreenShots\\ProductCostInListPage.png");
        sourcefile.renameTo(targetfile); // copy source file to target file
        try {
            InputStream is = new FileInputStream(targetfile);
            Allure.addAttachment("ProductCostInListPage.png",is);
            sourcefile = element.getScreenshotAs(OutputType.FILE);
            targetfile = new File("C:\\Users\\M.ELHADAF\\IdeaProjects\\MagentoProject\\ScreenShots\\ProductCostInDetailsPage.png");
            sourcefile.renameTo(targetfile);
            is = new FileInputStream(targetfile);
            Allure.addAttachment("ProductCostInDetailsPage.png",is);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
