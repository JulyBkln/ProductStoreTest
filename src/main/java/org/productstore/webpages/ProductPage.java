package org.productstore.webpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class ProductPage extends CommonPageClass {

    private static WebDriver driver;


    // locators

    private static final String productUrl = "https://www.demoblaze.com/prod.html?idp_=%s";
    protected static By addToCartButton = By.xpath("//a[contains(@class, 'btn')]");
    protected static By productName = By.className("name");


    // web driver

    public ProductPage(WebDriver driver) {
        super(driver);
        ProductPage.driver = driver;
    }


    // navigation

    public static ProductPage navigateToPage(int productId, WebDriver driver) {
        driver.get(String.format(productUrl, productId));

        String url = driver.getCurrentUrl();

        Assert.assertTrue(url.contains(String.valueOf(productId)), "Current page is invalid. Product Page [id='" + productId + "'] expected");
        return new ProductPage(driver);
    }


    // actions

    public ProductPage clickAddToCartButton() {
        driver.findElement(addToCartButton).click();
        return this;
    }


    public void acceptAlert(boolean isAuthorizedUser) {
        String alertMsg = "Product added";
        if (isAuthorizedUser) alertMsg = "Product added.";
        CommonPageClass.handleAlert(alertMsg, 10);
    }


    public static String getProductName() {
        CommonPageClass.waitForElementVisible(productName, 10);
        return driver.findElement(productName).getText();
    }


}
