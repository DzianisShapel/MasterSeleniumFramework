package org.askomdch;

import org.askomdch.api.CartApi;
import org.askomdch.dataprovider.DataProviderForTests;
import org.askomdch.pages.AccountPage;
import org.askomdch.base.BaseTest;
import org.askomdch.objects.Product;
import org.askomdch.pages.CheckoutPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "generateLoginData", dataProviderClass = DataProviderForTests.class)
    public void validLogin(String username, String password, String message){

        AccountPage accountPage = new AccountPage(getDriver()).load();
        accountPage.login(username, password);

        Assert.assertEquals(accountPage.getHelloMessage(), message);
    }

    @Test(dataProvider = "generateLoginData", dataProviderClass = DataProviderForTests.class)
    public void invalidPasswordLogin(String username, String password, String message){

        AccountPage accountPage = new AccountPage(getDriver()).load();
        accountPage.login(username, password);
        Assert.assertEquals(accountPage.getErrorMessage(), message);
    }

    @Test(dataProvider = "generateLoginData", dataProviderClass = DataProviderForTests.class)
    public void loginDuringCheckout(String username, String password) throws IOException {

        CartApi cartApi = new CartApi();
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1);
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        injectCookiesToBrowser(cartApi.getCookies());
    // refresh page after injection cookies
        checkoutPage.load();
        checkoutPage.clickHereToLoginLink().
                login(username, password);
        Assert.assertTrue(checkoutPage.getProductName().contains(product.getName()));
    }
}
