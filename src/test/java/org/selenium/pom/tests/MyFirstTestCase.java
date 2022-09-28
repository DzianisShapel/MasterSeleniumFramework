package org.selenium.pom.tests;


import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.CartPage;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.pages.HomePage;
import org.selenium.pom.pages.StorePage;
import org.selenium.pom.utils.ConfigLoader;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.io.IOException;


public class MyFirstTestCase extends BaseTest {

//    @Test
    public void guestCheckoutUsingDirectBankTransfer() throws  IOException {
        String searchFor = "Blue";
        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
        Product product = new Product(1215);

        StorePage storePage = new HomePage(getDriver()).
                load().
                getMyHeader().navigateToStoreUsingMenu().
                search(searchFor);
       Assert.assertTrue(storePage.getTitle().contains("Search results: "));

        storePage.getProductThumbnail().clickAddToCartBtn(product.getName());

        CartPage cartPage = storePage.getProductThumbnail().clickViewCart();
        Assert.assertEquals(cartPage.getProductName(), product.getName());

        CheckoutPage checkoutPage = cartPage.
                checkout().
                setBillingAddress(billingAddress).
                selectDirectBankTransfer().
                placeOrder();

        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");

    }

    //@Test
    public void loginAndCheckoutUsingDirectBankTransfer() throws InterruptedException, IOException {

        String searchFor = "Blue";
        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
        Product product = new Product(1215);
        User user = new User(ConfigLoader.getInstance().getUsername(), ConfigLoader.getInstance().getPassword());

        StorePage storePage = new HomePage(getDriver()).
                load().getMyHeader().
                navigateToStoreUsingMenu();
        Thread.sleep(2000);
        storePage.search(searchFor);
        Thread.sleep(2000);
        Assert.assertTrue(storePage.getTitle().contains("Search results: "));


        storePage.getProductThumbnail().clickAddToCartBtn("Blue Shoes");
        Thread.sleep(5000);
        CartPage cartPage = storePage.getProductThumbnail().clickViewCart();
        Assert.assertEquals(cartPage.getProductName(), "Blue Shoes");

        CheckoutPage checkoutPage = cartPage.checkout();
        checkoutPage.clickHereToLoginLink();
        Thread.sleep(3000);

        checkoutPage.
                login(user).
                setFirstNameFld("demo").
                setLastNameFld("user").
                setBillingAddressFld("Grodno").
                setBillingCityFld("Grodno").
                setBillingPostCodeFld("94188").
                setBillingEmailFld("dzianis283@tut.by");
        Thread.sleep(2000);
        checkoutPage.placeOrder();
        Thread.sleep(5000);
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }
}
