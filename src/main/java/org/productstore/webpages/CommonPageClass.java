package org.productstore.webpages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class CommonPageClass {

    private static final Logger logger = LogManager.getLogger(CommonPageClass.class);
    private static WebDriver driver = null;


    public CommonPageClass(WebDriver driver) {
        CommonPageClass.driver = driver;
    }


    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }


    public static WebElement findElement(By locator) {
        List<WebElement> webElements = driver.findElements(locator);
        return webElements.isEmpty() ? null : webElements.getFirst();
    }


    public static WebElement findElement(By locator, int timeout) {
        while (timeout != 0) {
            WebElement el = findElement(locator);

            if (el != null) {
                return el;
            } else {
                sleep(2);
            }
            timeout--;
        }
        logger.warn("Element '{}' is not found. Null returned.", locator);
        return null;
    }


    private static WebDriverWait getWebDriverWait(int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }


    public static void waitForElementVisible(By locator, int timeout) {
        WebDriverWait webDriverWait = getWebDriverWait(timeout);

        boolean visible = false;
        while (timeout != 0) {
            try {
                WebElement el = findElement(locator, 1);
                webDriverWait.until(ExpectedConditions.visibilityOf(el));
                visible = true;
                break;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            timeout--;
        }

        if (!visible) {
            throw new RuntimeException("Element located by locator: '" + locator.toString() + "' is not visible.");
        }
    }


    public static void handleAlert(String expectedAlertText, int timeout) {
        WebDriverWait webDriverWait = getWebDriverWait(timeout);

        try {
            webDriverWait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            Assert.assertEquals(alert.getText(), expectedAlertText, "Alert Text is unexpected");

            alert.accept();
        } catch (NoAlertPresentException e) {
            throw new NoAlertPresentException();
        }
    }


}
