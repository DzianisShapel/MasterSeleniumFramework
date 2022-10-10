package org.selenium.pom.tests;

import org.selenium.pom.api.CartApi;
import org.selenium.pom.api.SignUpApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.AccountPage;
import org.selenium.pom.utils.FakerUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AccountTest extends BaseTest {

    @Test
    public void viewOrder() throws IOException {
        String username = "demouser" + new FakerUtils().generateRandomNumber();
        User user = new User().
                setPassword("demopwd").
                setUsername(username).
                setEmail(username + "@fake.com");

        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);

        CartApi cartApi = new CartApi(signUpApi.getCookies());
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1);
        AccountPage accountPage = new AccountPage(getDriver()).load();
        injectCookiesToBrowser(signUpApi.getCookies());
        accountPage.load()
                .navigateToOrders()
                .viewOrder();

        Assert.assertEquals(accountPage.getOrderDetailsTitle(), "Order details");
        Assert.assertEquals(accountPage.getOrderedProductName(), product.getName());
    }
}
