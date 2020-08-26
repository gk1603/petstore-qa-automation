package test.java.uitestcases;
import main.java.ui.utilmethods.CommonMethods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestPurchaseOfLaptop {
    private WebDriver driver;
    private Properties properties = new Properties();
    CommonMethods commonMethods = new CommonMethods();
    /*
    This method will setup the webdriver.
     */
    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/gaurav/Documents/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }
    /*
    This method will close all the windows , popUps , tabs etc. which will be opened in every test
     */
    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    /*
    This test case will verify the functionality of Purchasing a laptop from the website.
    Steps to be performed :
        1.Open https://www.demoblaze.com/index.htm .
        2.Click on laptop tabs.
        3.Click on Sony Vaio laptop and add it to cart.
        4.Accept the popup and click on Home.
        5.Click on Dell I7 laptop and add it to cart.
        6.Accept the popup and click on Cart.
        7.Delete Dell laptop from the cart.
        8.Get total amount visible on the cart.
        9.Click on Place Order and fill all the details.
        10.Click Purchase.
        11.Capture the screenshot of the Purchased order Id
        12.Validate that the purchased amount is correct.
     */
    @Test
    public void testPurchaseOfLaptop() throws Exception {
        String URL="https://www.demoblaze.com/index.html";
        commonMethods.OpenSite(driver,URL);
        commonMethods.clickOnLaptopsTab(driver);
        commonMethods.clickOnSonyVaio(driver);
        commonMethods.clickOnAddToCart(driver);
        Thread.sleep(2000);
        commonMethods.handlePopUp(driver,"ACCEPT");
        commonMethods.clickOnHome(driver);
        commonMethods.clickOnLaptopsTab(driver);
        commonMethods.clickOnDellI7(driver);
        commonMethods.clickOnAddToCart(driver);
        Thread.sleep(2000);
        commonMethods.handlePopUp(driver,"ACCEPT");
        commonMethods.clickCart(driver);
        commonMethods.deleteDellLaptop(driver);
        Thread.sleep(2000);
        String actualAmountBeforePurchase = commonMethods.getTotalAmountBeforePurchase(driver);
        commonMethods.clickOnPlaceOrder(driver);
        commonMethods.enterName(driver,"Jack");
        commonMethods.enterCountry(driver,"India");
        commonMethods.enterCity(driver,"Delhi");
        commonMethods.enterCreditCard(driver,"344353453434534");
        commonMethods.enterMonth(driver,"Jan");
        commonMethods.enterYear(driver,"2020");
        commonMethods.clickOnPurchase(driver);
        String actualAmountAfterPurchase=commonMethods.getTotalAmountAfterPurchase(driver);
        commonMethods.assertTotalAmount(driver,actualAmountBeforePurchase,actualAmountAfterPurchase);
        commonMethods.takeSnapShot(driver,"src/reports/capturedscreenshots/CapturedScreenShot.png");
        commonMethods.clickOnOk(driver);
        String [] purchaseInfoArray=commonMethods.savePurchaseInfo(actualAmountAfterPurchase);
        commonMethods.logPurchaseInfo(purchaseInfoArray,"src/reports/purchaseinfo/purchaseinfo.csv");
    }
}
