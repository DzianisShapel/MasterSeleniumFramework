package org.selenium.pom.tests;

import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.dataprovider.MyDataProvider;
import org.selenium.pom.objects.Coupon;
import org.selenium.pom.pages.CartPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    @Test(dataProvider = "getCoupons", dataProviderClass = MyDataProvider.class)
    public void applyCouponsCodes(Coupon coupon) {
        double totalBeforeCoupon;
        double totalAfterCoupon;

        CartPage cartPage = new CartPage(getDriver()).load();
        CartApi cartApi = new CartApi();
        cartApi.addToCart(1215,1);
        injectCookiesToBrowser(cartApi.getCookies());
        totalBeforeCoupon = Double.parseDouble(cartPage.load().getTotal());

        cartPage.applyCoupon(coupon.getName());

        switch (coupon.getName()) {
            case "freeship" : totalAfterCoupon = totalBeforeCoupon - Double.parseDouble(cartPage.getShippingAmount());
                break;
            case "offcart5" : totalAfterCoupon = totalBeforeCoupon - 5;
                break;
            case "off25" : totalAfterCoupon = Math.round((totalBeforeCoupon * 0.75)*100) / 100;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + coupon.getName());
        }

        Assert.assertEquals(cartPage.getCouponName(coupon.getName()), "Coupon: " + coupon.getName());
        Assert.assertEquals(cartPage.getAlertFld(), "Coupon code applied successfully.");
        Assert.assertEquals(Double.parseDouble(cartPage.getTotal()), totalAfterCoupon);
    }




}
