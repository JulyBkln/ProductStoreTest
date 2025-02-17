import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.productstore.models.Product;
import org.productstore.process.CartManagement;
import org.productstore.process.LoginManagement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class ProductStoreTests {

    // variables
    private final WebDriver webDriver = new ChromeDriver();


    @Test
    public void test01_loginTest() {
        String username = "test";
        String password = "test";
        webDriver.manage().window().maximize();

        LoginManagement.login(username, password, webDriver);

        LoginManagement.logout(webDriver);
    }


    @Test
    public void test02_addToCartProductTest() {
        // testing data
        Product expProduct = new Product();
        expProduct.setProductId(1);
        expProduct.setProductName("Samsung galaxy s6");
        expProduct.setProductPrice(360);

        webDriver.manage().window().maximize();

        // add to cart product
        CartManagement.addToCartProduct(expProduct, false, webDriver);

        // check products in the cart
        CartManagement.validateCart(expProduct, false, webDriver);

    }


    // Test data
    private static ArrayList<Product> getProducts() {
        Product expProduct1 = new Product();
        expProduct1.setProductId(1);
        expProduct1.setProductName("Samsung galaxy s6");
        expProduct1.setProductPrice(360);

        Product expProduct2 = new Product();
        expProduct2.setProductId(5);
        expProduct2.setProductName("Iphone 6 32gb");
        expProduct2.setProductPrice(790);
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(expProduct1);
        productList.add(expProduct2);
        return productList;
    }


    @Test
    public void test03_clearCartTest() {
        ArrayList<Product> productList = getProducts();

        webDriver.manage().window().maximize();
        LoginManagement.login("test", "test", webDriver);

        // add to cart products
        CartManagement.addToCartProduct(productList, true, webDriver);

        // check products in the cart
        CartManagement.validateCart(productList, true, webDriver);

        // clear cart
        CartManagement.clearCart(webDriver);
    }


    @AfterTest
    private void closeSession() {
        webDriver.quit();
    }
}
