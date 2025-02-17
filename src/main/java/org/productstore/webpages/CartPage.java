package org.productstore.webpages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends CommonPageClass {

    private static WebDriver driver;
    private static final Logger logger = LogManager.getLogger(CartPage.class);

    // locators

    private static final String cartUrl = "https://www.demoblaze.com/cart.html";
    private static final By headerLabel = By.cssSelector("h2");
    protected static By placeOrderButton = By.xpath("//button[text()='Place Order']");
    protected static By productsTableRow = By.xpath("//tr[@class='success']");
    protected static By productName = By.className("name");
    protected static String deleteButton = "//td//a[text()='Delete'][%s]";


    // web driver

    public CartPage(WebDriver driver) {
        super(driver);
        CartPage.driver = driver;
    }


    // navigation

    public static CartPage navigateToPage(WebDriver driver) {
        driver.get(cartUrl);

        try {
            WebElement el = driver.findElement(headerLabel);
            Assert.assertEquals(el.getText(), "Products", "Cart Page is expected.");
        } catch (Exception e) {
            logger.fatal(e.getMessage(), e);
        }

        return new CartPage(driver);
    }


    // actions

    public CartPage clickPlaceOrderButton() {
        driver.findElement(placeOrderButton).click();
        return this;
    }


    public List<String> getListOfProductsAdded(boolean isEmpty) {
        if (!isEmpty) {
            CommonPageClass.waitForElementVisible(productsTableRow, 10);
        }
        List<WebElement> elements = driver.findElements(productsTableRow);
        List<String> rows = new ArrayList<>();

        for (WebElement element : elements) {
            rows.add(element.getText());
        }

        logger.info("List of Products added returned: {}", rows);
        return rows;
    }


    public List<String> getListOfProductsAdded() {
        return getListOfProductsAdded(false);
    }


    public void deleteAllProductsAdded() {
        CommonPageClass.waitForElementVisible(productsTableRow, 10);
        List<WebElement> elements = driver.findElements(productsTableRow);

        int i = 0;
        while (i < elements.size()) {
            driver.findElements(By.xpath(String.format(deleteButton, i)));
            sleep(1);
            i++;
        }

        logger.info("All added products were deleted..");
    }

}
