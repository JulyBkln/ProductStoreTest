package org.productstore.process;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.productstore.webpages.HomePage;

public class LoginManagement {

    private static final Logger logger = LogManager.getLogger(LoginManagement.class);


    /*    Login   */

    /**
     * Login
     *
     * @param username  Username
     * @param password  Password
     * @param webDriver Web Driver
     */
    public static void login(String username, String password, WebDriver webDriver) {
        logger.info("Login is starting.. ");

        // navigate to Log In
        HomePage homePage = HomePage.navigateToPage(webDriver);
        homePage.clickLogInNavigationLink();

        // enter username and password
        homePage.enterUser(username).enterPassword(password).clickLogInButton();

        // check login is success
        homePage.waitForSuccessLogin(10);

        logger.info("Login is finished.");
    }


    /**
     * Logout
     *
     * @param webDriver Web Driver
     */
    public static void logout(WebDriver webDriver) {
        logger.info("Logout is starting.. ");

        // navigate to Log Out
        HomePage homePage = HomePage.navigateToPage(webDriver);
        boolean isAlreadyLogin = homePage.checkIsSuccessLogin(10);

        if (isAlreadyLogin) {
            // logout
            homePage.clickLogOutNavigationLink();
        } else {
            throw new RuntimeException("Impossible to Logout, because Login was not executed. ");
        }
        logger.info("Logout is finished.");
    }

}
