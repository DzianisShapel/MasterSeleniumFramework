package org.askomdch;


import org.askomdch.base.BaseTest;
import org.askomdch.objects.Product;
import org.askomdch.objects.User;
import org.askomdch.pages.CartPage;
import org.askomdch.pages.CheckoutPage;
import org.askomdch.pages.HomePage;
import org.askomdch.pages.StorePage;
import org.askomdch.utils.ConfigLoader;
import org.askomdch.utils.JacksonUtils;
import org.askomdch.objects.BillingAddress;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.io.IOException;


public class MyFirstTestCase extends BaseTest {

    @Test
    public void guestCheckoutUsingDirectBankTransfer() throws IOException, InterruptedException {
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

    /*@Test
    public void loginAndCheckoutUsingDirectBankTransfer() throws InterruptedException, IOException {

        String searchFor = "Blue";
        editBillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json", editBillingAddress.class);
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
                login(user, ).
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
    }*/
}
