package ui.pageobjects;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ConfigurationUtils;

/**
 * Page Object for the Purchase page in Magento Store application.
 * This class encapsulates the elements and actions related to the purchase process.
 */
public class PurchasePageObject extends BasePage {

    // Fields to store user details for the purchase process
    public String fn , mn , ln , tp , fx , cit , count , stat , zi , ad1 , ad2 , co ;

    /**
     * Constructor that initializes the WebDriver and PageFactory.
     *
     * @param driver the WebDriver instance
     */
    public PurchasePageObject(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    // Web element representing the "Apply" button for applying a coupon code
    @FindBy(xpath = "//span[contains(text(),'Apply')]")
    private WebElement apply_btn;

    // Click on the "Apply" button to apply the entered coupon code.
    public void click_on_apply_btn()
    {
        clickElement(apply_btn);
    }

    // @FindBy(xpath = "//input[@id='coupon_code']")
    @FindBy(xpath = "//input[@id='coupon_code']")
    private WebElement couponCode;

    /**
     * Enter a coupon code into the coupon code field.
     *
     * @param code the coupon code to enter
     */
    public void enter_couponCode(String code)
    {
        clearElement(couponCode);
        sendText(couponCode,code);
    }

    // // Web element representing the country selection dropdown
    @FindBy(xpath = "//select[@id='country']")
    private WebElement country;

    // Web element representing the state selection dropdown
    @FindBy(xpath = "//select[@id='region_id']")
    private WebElement state;

    // Web element representing the zip code input field
    @FindBy(xpath = "//input[@id='postcode']")
    private WebElement zibCode;

    // Web element representing the "Estimate" button
    @FindBy(xpath = "//span[contains(text(),'Estimate')]")
    private WebElement estimate_button;

    // Web element representing the "Update Total" button
    @FindBy(xpath = "//button[@title='Update Total']")
    private WebElement update_button;

    // Web element representing the "Grand Total" price
    @FindBy(xpath = "//strong[normalize-space()='Grand Total']")
    private WebElement total_price;

    // Web element representing the "Checkout with Multiple Addresses" link
    @FindBy(xpath = "//a[normalize-space()='Checkout with Multiple Addresses']")
    private WebElement checkout_button;

    // Web element representing the flat price option
    @FindBy(xpath = "//dl[@class='sp-methods']/dd/ul/li/label/span")
    private WebElement flat_price;


    // Methods to interact with elements

    // Click on the "Checkout" button to proceed with the checkout process.
    public void click_on_checkout_button()
    {
        clickElement(checkout_button);
    }

    // Getter method to return the flat price element.
    public WebElement getFlat_price() {
        return flat_price;
    }

    // Click on the "Update Total" button to update the total price.
    public void click_on_update_button()
    {
        clickElement(update_button);
    }

    // Click on the "Estimate" button to estimate shipping or total.
    public void click_on_estimate_button()
    {
        clickElement(estimate_button);
    }

    /**
     * Enter a zip code into the zip code input field.
     *
     * @param z the zip code to enter
     */
    public void enter_zibCode(String z)
    {
        clearElement(zibCode);
        sendText(zibCode,z);
    }

    /**
     * Select a country from the country dropdown list.
     *
     * @param con the country to select
     */
    public void select_country(String con)
    {
        clickElement(country);
        selectOption(country,con);
    }

    /**
     * Select a state from the state dropdown list.
     *
     * @param sta the state to select
     */
    public void select_state(String sta)
    {
        clickElement(state);
        selectOption(state,sta);
    }

    // Retrieves user data from the configuration and stores it in the class fields.
    public void getData()
    {
        fn = ConfigurationUtils.getInstance().get_first_name();
        mn = ConfigurationUtils.getInstance().get_middle_name();
        ln = ConfigurationUtils.getInstance().get_last_name();
        co = ConfigurationUtils.getInstance().get_company();
        tp = ConfigurationUtils.getInstance().get_telephone();
        fx = ConfigurationUtils.getInstance().get_fax();
        ad1 = ConfigurationUtils.getInstance().get_address1();
        ad2 = ConfigurationUtils.getInstance().get_address2();
        cit = ConfigurationUtils.getInstance().get_city();
        stat = ConfigurationUtils.getInstance().get_state();
        zi = ConfigurationUtils.getInstance().get_zipCode();
        count = ConfigurationUtils.getInstance().get_country();
    }

}
