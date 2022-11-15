package org.askomdch;

import org.askomdch.api.CartApi;
import org.askomdch.base.BaseTest;
import org.askomdch.dataprovider.MyDataProvider;
import org.askomdch.objects.BillingAddress;
import org.askomdch.pages.CheckoutPage;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class SalesTaxTest extends BaseTest {

    private static final Map<String, String> myMap = Map.of(
            "Colorado", "1.31",
            "Connecticut", "2.86",
            "Mississippi", "3.15"
    );

    @Story("Calculate Sales tax")
    @Description("Guest user perform checkout from different USA states")
    @Test(dataProvider = "getUSABillingAddresses", dataProviderClass = MyDataProvider.class)
    public void GuestCheckoutFromDifferentUSAStates(BillingAddress billingAddress) {
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        CartApi cartApi = new CartApi();
        cartApi.addToCart(1215,1);
        injectCookiesToBrowser(cartApi.getCookies());

        checkoutPage.load().
                setBillingAddress(billingAddress);
        Assert.assertEquals(checkoutPage.getStateTax(), myMap.get(billingAddress.getState()));
    }

}
