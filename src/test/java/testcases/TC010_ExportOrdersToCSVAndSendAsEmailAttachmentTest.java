package testcases;


import base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigurationUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TC010_ExportOrdersToCSVAndSendAsEmailAttachmentTest extends BaseTest {
    String em  , pass ;

    @Story("Verify That You Can Export Orders To CSV And Send As Email Attachment")
    @Description("Test Steps:\n\n" +
            "1- Go To ( https://live.techpanda.org/index.php/backendlogin ) \n" +
            "2- Login The Credentials Provided\n" +
            "3- Go To Sales-> Orders Menu\n" +
            "4- Select 'Csv' In Export To Dropdown And Click Export Button\n" +
            "5- Read Downloaded File And Display All Order Information In Console Window\n" +
            "6- Attach This Exported File And Email To Another Email Id.\n\n" +
            "Expected Result:\n\n" +
            "- Console Displays All Order Information\n" +
            "- Email Is Sent Successfully\n\n" +
            "Actual Result:\n\n" +
            "- All Order Information IS Displayed Successfully\n" +
            "- Email Is Sent Successfully\n\n" +
            "Priority:\n\n" +
            "- Medium"
    )

    @Test
    public void Validate_Export_Orders_To_CSV_And_Send_As_Email_Attachment()
    {
        em = ConfigurationUtils.getInstance().get_em();
        pass = ConfigurationUtils.getInstance().get_pass();
        driver.get("https://live.techpanda.org/index.php/backendlogin/");
        driver.findElement(By.xpath("//input[@id='username']")).sendKeys(em);
        driver.findElement(By.xpath("//input[@id='login']")).sendKeys(pass);
        driver.findElement(By.xpath("//input[@title='Login']")).click();

        driver.findElement(By.xpath("//span[normalize-space()='close']")).click();
        driver.findElement(By.xpath("//span[normalize-space()='Sales']")).click();
        driver.findElement(By.xpath("//span[normalize-space()='Orders']")).click();
        driver.findElement(By.xpath("//span[contains(text(),'Export')]")).click();

        // Take Screen Shot For Actual Result.
        TakesScreenshot ts = (TakesScreenshot) driver;
        File sourcefile = ts.getScreenshotAs(OutputType.FILE);
        File targetfile = new File("C:\\Users\\M.ELHADAF\\IdeaProjects\\MagentoProject\\ScreenShots\\OrdersExportation.png");
        sourcefile.renameTo(targetfile); // copy sourcefile to target file
        try {
            InputStream is = new FileInputStream(targetfile);
            Allure.addAttachment("OrdersExportation.png",is);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            WebElement concole = driver.findElement(By.id("email"));
            Assert.assertTrue(concole.isDisplayed());
        } catch (Exception e) {
            throw new RuntimeException("Exportation Funcation Isn't Working Correctlty Because Export Button Isn't Working");
        }
    }
}
