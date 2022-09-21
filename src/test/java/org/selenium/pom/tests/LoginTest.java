package org.selenium.pom.tests;

import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.api.actions.SignUpApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.AccountPage;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.utils.FakerUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

public class LoginTest extends BaseTest {

    @Test
    public void loginDuringCheckout() throws IOException {
        String username = "demouser" + new FakerUtils().generateRandomNumber();
        User user = new User().
                setPassword("demopwd").
                setUsername(username).
                setEmail(username + "@fake.com");

        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);

        CartApi cartApi = new CartApi();
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1);
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        injectCookiesToBrowser(cartApi.getCookies());

    //    CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load().
    // refresh page after injection cookies
        checkoutPage.load();
        checkoutPage.clickHereToLoginLink().
                login(user);
        Assert.assertTrue(checkoutPage.getProductName().contains(product.getName()));
    }

    @Test
    public void loginFailed(){
        FakerUtils fakeData = new FakerUtils();
        String fakeUsername = fakeData.generateRandomUsername();
        AccountPage accountPage = new AccountPage(getDriver()).load();
        accountPage.login(fakeUsername, fakeData.generateRandomPassword());
      Assert.assertTrue(accountPage.getContentMessage().contains("The username " + fakeUsername + " is not registered on this site."));
    }
}
