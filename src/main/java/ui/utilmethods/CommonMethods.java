package main.java.ui.utilmethods;

import com.opencsv.CSVWriter;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class CommonMethods {

    PageObjects pageObjects = new PageObjects();
    ActionsOnPage actionsOnPage = new ActionsOnPage();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");


    public void OpenSite(WebDriver driver, String url) {
        actionsOnPage.openURL(driver, url);
        driver.manage().window().maximize();
    }


    public void clickOnLaptopsTab(WebDriver driver) {
        actionsOnPage.click(driver, By.linkText(pageObjects.Click_On_Laptops));
    }

    public void clickOnSonyVaio(WebDriver driver) {
        actionsOnPage.click(driver, By.linkText(pageObjects.Click_On_Sony_Vaio));
    }

    public void clickOnDellI7(WebDriver driver) {
        actionsOnPage.click(driver, By.linkText(pageObjects.Click_On_Dell));
    }

    public void clickOnAddToCart(WebDriver driver) {
        actionsOnPage.click(driver, By.linkText(pageObjects.Click_On_Add_To_Cart));
    }

    public void clickCart(WebDriver driver) {
        actionsOnPage.click(driver, By.linkText(pageObjects.Click_On_Cart));
    }

    public void clickOnHome(WebDriver driver) {
        actionsOnPage.click(driver, By.xpath(pageObjects.Click_On_Home));
    }

    public void clickOnPurchase(WebDriver driver) {
        actionsOnPage.click(driver, By.xpath(pageObjects.Click_On_Purchase));
    }

    public void clickOnOk(WebDriver driver) {
        actionsOnPage.click(driver, By.xpath(pageObjects.Click_On_OK));
    }

    public void deleteDellLaptop(WebDriver driver) {
        actionsOnPage.click(driver, By.xpath(pageObjects.Click_On_Delete));
    }

    public void clickOnPlaceOrder(WebDriver driver) {
        actionsOnPage.click(driver, By.xpath(pageObjects.Click_On_Place_Order));
    }

    public void enterName(WebDriver driver, String name) {
        actionsOnPage.sendKeys(driver, By.id(pageObjects.Enter_Name), name);
    }

    public void enterCountry(WebDriver driver, String country) {
        actionsOnPage.sendKeys(driver, By.id(pageObjects.Enter_Country), country);
    }

    public void enterMonth(WebDriver driver, String month) {
        actionsOnPage.sendKeys(driver, By.id(pageObjects.Enter_Month), month);
    }

    public void enterYear(WebDriver driver, String year) {
        actionsOnPage.sendKeys(driver, By.id(pageObjects.Enter_Year), year);
    }

    public void enterCreditCard(WebDriver driver, String creditCard) {
        actionsOnPage.sendKeys(driver, By.id(pageObjects.Enter_Credit_Card), creditCard);
    }

    public String getTotalAmountBeforePurchase(WebDriver driver) {
        return actionsOnPage.getText(driver, By.xpath(pageObjects.Get_Total_Amount));
    }

    public void enterCity(WebDriver driver, String city) {
        actionsOnPage.sendKeys(driver, By.id(pageObjects.Enter_City), city);
    }

    public String getTotalAmountAfterPurchase(WebDriver driver) {
        return actionsOnPage.getText(driver, By.xpath(pageObjects.Get_Total_Amount_After_Purchase));
    }

    public void handlePopUp(WebDriver driver, String actionOnPopUp) {
        actionsOnPage.handlePopUp(driver, actionOnPopUp);
    }

    public static void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {
        TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(SrcFile, DestFile);

    }

    public void assertTotalAmount(WebDriver driver, String expectedTotalAmount, String actualTotalAmount) {
        String actualAmount = "Amount: " + expectedTotalAmount + " USD";
        Assert.assertTrue(actualTotalAmount.contains(actualAmount));

    }

    public String[] savePurchaseInfo(String purchaseInfo) {
        String[] s = purchaseInfo.split("\n");

        String[] purchaseInfoArray = {s[0], s[1], s[2], s[3], s[4]};
        return purchaseInfoArray;
    }

    public static void logPurchaseInfo(String[] data, String filePath) {
        File file = new File(filePath);
        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);
            writer.writeNext(data);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}