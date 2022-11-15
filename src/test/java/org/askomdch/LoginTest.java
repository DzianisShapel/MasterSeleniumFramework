package org.askomdch;

import org.askomdch.api.CartApi;
import org.askomdch.api.SignUpApi;
import org.askomdch.pages.AccountPage;
import org.askomdch.utils.FakerUtils;
import org.askomdch.base.BaseTest;
import org.askomdch.objects.Product;
import org.askomdch.objects.User;
import org.askomdch.pages.CheckoutPage;
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
