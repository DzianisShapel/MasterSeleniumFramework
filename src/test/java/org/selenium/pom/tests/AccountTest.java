package org.selenium.pom.tests;

import org.selenium.pom.api.CartApi;
import org.selenium.pom.api.CheckoutApi;
import org.selenium.pom.api.SignUpApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.AccountPage;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.utils.FakerUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AccountTest extends BaseTest {

    @Test
    public void viewOrder() throws IOException {

        Product product = new Product(1215);
        CheckoutApi checkoutApi = new CheckoutApi();
        checkoutApi.checkout();

        AccountPage accountPage = new AccountPage(getDriver()).load();
        injectCookiesToBrowser(checkoutApi.getCookies());
        accountPage.loadOrders().
                viewOrder();

        Assert.assertEquals(accountPage.getOrderDetailsTitle(), "Order details");
        Assert.assertEquals(accountPage.getOrderedProductName(), product.getName());
    }
}
