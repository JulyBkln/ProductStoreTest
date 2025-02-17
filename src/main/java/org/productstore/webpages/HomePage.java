package org.productstore.webpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends CommonPageClass {

    private static WebDriver driver;

    // locators
    protected static String homeUrl = "https://www.demoblaze.com/index.html";
    protected static By logInNavLink = By.id("login2");
    protected static By userLabel = By.id("nameofuser");
    protected static By logOutNavLink = By.id("logout2");
    //login dialog
    protected static By userInput = By.id("loginusername");
    protected static By passwordInput = By.id("loginpassword");
    protected static By logInButton = By.xpath("//button[text()='Log in']");

    // web driver

    public HomePage(WebDriver driver) {
        super(driver);
        HomePage.driver = driver;
    }


    // navigation

    public static HomePage navigateToPage(WebDriver driver) {
        driver.get(homeUrl);
        return new HomePage(driver);
    }


    // actions

    public void clickLogInNavigationLink() {
        driver.findElement(logInNavLink).click();
        CommonPageClass.waitForElementVisible(userInput, 5);
    }


    public void waitForSuccessLogin(int timeout) {
        CommonPageClass.waitForElementVisible(userLabel, timeout);
    }


    public boolean checkIsSuccessLogin(int timeout) {
        boolean isSuccess = false;

        for (int t = 0; t <= timeout; t++) {
            WebElement el = driver.findElement(userLabel);
            if (el != null) {
                isSuccess = true;
            }
            sleep(1);
        }
        return isSuccess;
    }


    public void clickLogOutNavigationLink() {
        driver.findElement(logOutNavLink).click();
    }


    // Login Dialog

    public HomePage enterUser(String user) {
        driver.findElement(userInput).sendKeys(user);
        return this;
    }

    public HomePage enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
        return this;
    }

    public void clickLogInButton() {
        driver.findElement(logInButton).click();
    }

}
