package org.selenium.pom.tests;

import org.selenium.pom.api.CartApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.dataprovider.MyDataProvider;
import org.selenium.pom.objects.Coupon;
import org.selenium.pom.pages.CartPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CartTest extends BaseTest {

    @Test(dataProvider = "getCoupons", dataProviderClass = MyDataProvider.class)
    public void applyCouponCodes(Coupon coupon) {

        CartPage cartPage = new CartPage(getDriver()).load();
        CartApi cartApi = new CartApi();
        cartApi.addToCart(1215, 1);
        injectCookiesToBrowser(cartApi.getCookies());
        BigDecimal totalAfterCoupon;
        BigDecimal subtotalBeforeCoupon = new BigDecimal(cartPage.load().getSubTotal());
        BigDecimal totalBeforeCoupon = new BigDecimal(cartPage.getTotal());
        BigDecimal stateTax = new BigDecimal(cartPage.getStateTax());

        cartPage.applyCoupon(coupon.getName());

        switch (coupon.getName()) {
            case "freeship":
                totalAfterCoupon = totalBeforeCoupon.subtract(new BigDecimal(cartPage.getShippingAmount()));
                break;
            case "offcart5":
                totalAfterCoupon = totalBeforeCoupon.subtract(new BigDecimal(5.00));
                break;
            case "off25":
                totalAfterCoupon = totalBeforeCoupon.subtract(subtotalBeforeCoupon.multiply(new BigDecimal(0.25), new MathContext(4)))
                        .subtract(stateTax.multiply(new BigDecimal(0.25), new MathContext(2, RoundingMode.CEILING)));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + coupon.getName());
        }
        Assert.assertEquals(cartPage.getCouponName(coupon.getName()), "Coupon: " + coupon.getName());
        Assert.assertEquals(cartPage.getAlertFld(), "Coupon code applied successfully.");
        Assert.assertEquals(new BigDecimal(cartPage.getTotal()), totalAfterCoupon);
    }
}
