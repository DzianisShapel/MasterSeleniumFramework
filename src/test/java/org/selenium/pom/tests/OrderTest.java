package org.selenium.pom.tests;

import org.selenium.pom.api.CheckoutApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.Product;
import org.selenium.pom.pages.OrdersPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OrderTest extends BaseTest {

    @Test
    public void viewOrder() throws IOException {

        Product product = new Product(1198);
        CheckoutApi checkoutApi = new CheckoutApi();
        checkoutApi.checkout(product);

        OrdersPage ordersPage = new OrdersPage(getDriver()).load();
        injectCookiesToBrowser(checkoutApi.getCookies());
        ordersPage.load().
                viewOrder();

        Assert.assertEquals(ordersPage.getOrderDetailsTitle(), "Order details");
        Assert.assertEquals(ordersPage.getOrderedProductName(product), product.getName());
        Assert.assertEquals(ordersPage.getOrderString(), "Order #" + checkoutApi.getOrderNumber() + " was placed on " + checkoutApi.getOrderDate() + " and is currently On hold.");
    }
}
