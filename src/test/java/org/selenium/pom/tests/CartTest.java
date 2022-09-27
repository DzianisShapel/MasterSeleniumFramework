package org.selenium.pom.tests;

import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.dataprovider.MyDataProvider;
import org.selenium.pom.objects.Coupon;
import org.selenium.pom.pages.CartPage;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    @Test(dataProvider = "getCoupons", dataProviderClass = MyDataProvider.class)
    public void applyCouponsCodes(Coupon coupon) {
        CartPage cartPage = new CartPage(getDriver()).load();
        CartApi cartApi = new CartApi();
        cartApi.addToCart(1215,1);
        injectCookiesToBrowser(cartApi.getCookies());

        cartPage.load().applyCoupon(coupon.getName());

    }
}
