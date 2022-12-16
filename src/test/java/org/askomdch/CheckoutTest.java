package org.askomdch;

import io.restassured.http.Cookies;
import org.askomdch.api.BillingAddressApi;
import org.askomdch.api.SignUpApi;
import org.askomdch.utils.FakerUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.askomdch.api.CartApi;
import org.askomdch.base.BaseTest;
import org.askomdch.dataprovider.DataProviderForTests;
import org.askomdch.objects.BillingAddress;
import org.askomdch.objects.Product;
import org.askomdch.objects.User;
import org.askomdch.pages.CheckoutPage;
import org.askomdch.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

@Epic("Checkout")
public class CheckoutTest extends BaseTest {

    @Story("Guest Checkout Using DirectBank Transfer")
    @Description("User perform checkout selecting directBank transfer radiobutton")
    @Test(description = "Checkout Using Direct Bank Transfer", dataProvider = "getDataForCheckout",  dataProviderClass = DataProviderForTests.class)
    public void CheckoutUsingDirectBankTransfer(BillingAddress billingAddress, Cookies cookies) {

        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        injectCookiesToBrowser(cookies);
        checkoutPage.load().
                setBillingAddress(billingAddress).
                selectDirectBankTransfer().
                placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }

    /*@Story("Login checkout")
    @Test
    public void LoginAndCheckoutUsingDirectBankTransfer() throws IOException {
        editBillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json", editBillingAddress.class);
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
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        injectCookiesToBrowser(signUpApi.getCookies());
        checkoutPage.
                load().
                setBillingAddress(billingAddress).
                selectDirectBankTransfer().
                placeOrder();

        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }*/

    @Story("Checkout Order")
    @Test(dataProvider = "getDataForCheckout",  dataProviderClass = DataProviderForTests.class)
    public void CheckoutUsingCashOnDelivery(BillingAddress billingAddress, Cookies cookies) {

        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        injectCookiesToBrowser(cookies);
        checkoutPage.load().
                setBillingAddress(billingAddress).
                selectCashOnDeliveryTransfer().
                placeOrder();

        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }

    @Story("Guest Checkout")
    @Description("Guest user perform checkout from different countries")
    @Test(dataProvider = "getBillingAddresses", dataProviderClass = DataProviderForTests.class)
    public void GuestCheckoutUsingDirectBankTransferForSeveralCountries(BillingAddress billingAddress) {
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        CartApi cartApi = new CartApi();
        cartApi.addToCart(1215,1);
        injectCookiesToBrowser(cartApi.getCookies());

        checkoutPage.load().
                setBillingAddress(billingAddress).
                selectDirectBankTransfer().
                placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }

    @Test
    public void LoginAndCheckoutUsingDirectBankTransfer() throws IOException {
        BillingAddressApi billingAddressApi = new BillingAddressApi();
        billingAddressApi.editBillingAddress();
        CartApi cartApi = new CartApi();
        cartApi.addToCart(1215,1);
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        injectCookiesToBrowser(billingAddressApi.getCookies());
        checkoutPage.
                load().
                selectDirectBankTransfer().
                placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }



    /*@Story("Login checkout")
    @Test(dataProvider = "getDataForCheckout",  dataProviderClass = DataProviderForTests.class)
    public void LoginAndCheckoutUsingCashOnDelivery(editBillingAddress billingAddress, Cookies cookies) {

        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        injectCookiesToBrowser(cookies);
        checkoutPage.
                load().
                setBillingAddress(billingAddress).
                selectCashOnDeliveryTransfer().
                placeOrder();

        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }*/
}
