package org.productstore.process;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.productstore.models.Product;
import org.productstore.webpages.CartPage;
import org.productstore.webpages.ProductPage;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartManagement {

    private static final Logger logger = LogManager.getLogger(CartManagement.class);


    /*    Add to cart    */

    /**
     * Add to Cart product
     *
     * @param expectedProduct Product Object
     * @param webDriver       Web Driver
     */
    public static void addToCartProduct(Product expectedProduct, boolean isAuthorizedUser, WebDriver webDriver) {
        logger.info("Add to Cart Product is starting.. ");

        // navigate to product
        ProductPage productPage = ProductPage.navigateToPage(expectedProduct.getProductId(), webDriver);

        // check product header
        String productName = ProductPage.getProductName();
        Assert.assertEquals(productName, expectedProduct.getProductName(), "Unexpected Product Name");

        // add to cart and accept alert
        productPage.clickAddToCartButton().acceptAlert(isAuthorizedUser);

        logger.info("Add to Cart Product is finished.");
    }


    /**
     * Add to Cart product list
     *
     * @param expectedProductList Product Object List
     * @param webDriver           Web Driver
     */
    public static void addToCartProduct(List<Product> expectedProductList, boolean isAuthorizedUser, WebDriver webDriver) {
        for (Product product : expectedProductList) {
            addToCartProduct(product, isAuthorizedUser, webDriver);
        }
    }


    /*     Cart validation   */

    /**
     * Validate Cart with added products
     *
     * @param productList Product Object List
     * @param webDriver   Web Driver
     */
    public static void validateCart(List<Product> productList, boolean isAuthorizedUser, WebDriver webDriver) {
        logger.info("Cart validation is starting.. ");

        // collect product list to be checked
        List<String> expProductList = new ArrayList<>();
        for (Product product : productList) {
            expProductList.add(product.getProductName() + " " + product.getProductPrice() + " Delete");
        }
        Collections.sort(expProductList);
        logger.info("Product list created: {}", expProductList);

        // navigate to cart
        CartPage cartPage = CartPage.navigateToPage(webDriver);

        // get actual product list
        webDriver.navigate().refresh();
        List<String> el = cartPage.getListOfProductsAdded();

        // compare actual and expected list of products
        if (isAuthorizedUser) {
            Assert.assertEquals(el, expProductList, "Products list is unexpected.");
        } else {
            //todo if figure out with cleanse the Cart before test then validation will be more stable
            Assert.assertFalse(el.isEmpty(), "Some products expected to be present");
        }
        logger.info("Cart validation is finished.");
    }


    public static void validateCart(Product product, boolean isAuthorizedUser, WebDriver webDriver) {
        validateCart(Collections.singletonList(product), isAuthorizedUser, webDriver);
    }


    // Cart Cleansing //

    /**
     * Validate Cart with added products
     *
     * @param webDriver Web Driver
     */
    public static void clearCart(WebDriver webDriver) {
        logger.info("Cart cleansing is starting.. ");

        // navigate to cart
        CartPage cartPage = CartPage.navigateToPage(webDriver);

        // delete all products
        cartPage.deleteAllProductsAdded();

        // refresh
        webDriver.navigate().refresh();
        List<String> list = cartPage.getListOfProductsAdded(true);
        Assert.assertTrue(list.isEmpty(), "All products expected to be deleted");

        logger.info("Cart cleansing is finished.");
    }


}
