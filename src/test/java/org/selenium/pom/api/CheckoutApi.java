package org.selenium.pom.api;

import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.selenium.pom.api.actions.ApiRequest;

import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.utils.ConfigLoader;
import org.selenium.pom.utils.FakerUtils;
import org.selenium.pom.utils.JacksonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CheckoutApi {

    private Cookies cookies;

    public Cookies getCookies() {
        return cookies;
    }

    public CheckoutApi() {
    }

    public CheckoutApi(Cookies cookies) {
        this.cookies = cookies;
    }

    public Response checkout() throws IOException {

        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
        String username = "demouser" + new FakerUtils().generateRandomNumber();
        User user = new User().
                setPassword("demopwd").
                setUsername(username).
                setEmail(username + "@fake.com");
        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);
        Cookie loggedInsignUpApiCookie = signUpApi.getCookies().get("wordpress_logged_in_e39c30ea93030240fdcfed1ddf385ba1");

        System.out.println("signUpApi cookies: " + signUpApi.getCookies() +
                "logged_in cokie: " + signUpApi.getCookies().getValue("wordpress_logged_in_e39c30ea93030240fdcfed1ddf385ba1"));

        CartApi cartApi = new CartApi(signUpApi.getCookies());
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1);
        cookies = cartApi.getCookies();

        Cookie myCartApiCookie = cartApi.getCookies().get("wp_woocommerce_session_e39c30ea93030240fdcfed1ddf385ba1");
        System.out.println("CARTAPI cookies: " + cartApi.getCookies());

        List<Cookie> cookiesForAccountPage = new ArrayList<>();
        cookiesForAccountPage.add(loggedInsignUpApiCookie);
        cookiesForAccountPage.add(myCartApiCookie);

        Response checkoutPage = ApiRequest.get("/checkout", cookies);

        Header header = new Header("content-type", "application/x-www-form-urlencoded");
        Headers headers = new Headers(header);

        Map<String, Object> formParams = new HashMap<>();
        formParams.put("billing_first_name", new FakerUtils().generateFirstName());
        formParams.put("billing_last_name", new FakerUtils().generateLastName());
        formParams.put("billing_country", billingAddress.getCountryCode());
        formParams.put("billing_address_1", billingAddress.getAddressLineOne());
        formParams.put("billing_city", billingAddress.getCity());
        formParams.put("billing_state", billingAddress.getStateCode());
        formParams.put("billing_postcode", billingAddress.getPostalCode());
        formParams.put("billing_email", billingAddress.getEmail());
        formParams.put("shipping_first_name", new FakerUtils().generateFirstName());
        formParams.put("shipping_last_name", new FakerUtils().generateLastName());
        formParams.put("shipping_address_1", billingAddress.getAddressLineOne());
        formParams.put("shipping_city", billingAddress.getCity());
        formParams.put("shipping_postcode", billingAddress.getPostalCode());
        formParams.put("shipping_method[0]", "flat_rate:3");
        formParams.put("payment_method", "bacs");
        formParams.put("woocommerce-process-checkout-nonce", checkoutPage.htmlPath().getString("**.findAll { it.@name == 'woocommerce-process-checkout-nonce' }.@value"));
        formParams.put("_wp_http_referer", "/?wc-ajax=update_order_review");

        if (cookies == null) {
            cookies = new Cookies();
        }

        Response response = given().
                baseUri(ConfigLoader.getInstance().getBaseUrl()).
                headers(headers).
                formParams(formParams).
                cookies(cookies).
                log().all().
                when().
                post("/?wc-ajax=checkout").
                then().
                log().all().
                extract().response();

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Failed to add product, HTTP Status Code: " + response.getStatusCode());
        }

        cookiesForAccountPage.add(response.getDetailedCookie("woocommerce_items_in_cart"));
        cookiesForAccountPage.add(response.getDetailedCookie("woocommerce_cart_hash"));

        Cookies cookiesForAccountPage2 = new Cookies(cookiesForAccountPage);
        this.cookies = cookiesForAccountPage2;
        System.out.println("cookiesForAccountPage2 COOKIES: " + cookiesForAccountPage2 + " Checkout detailed cookies: " + response.getDetailedCookies());
        return response;
    }

    private Response getCheckoutPage() {
        Cookies cookies = new Cookies();

        Response response = ApiRequest.get("/checkout", cookies);
        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Failed to fetch the account, HTTP Status Code: " + response.getStatusCode());
        }
        return response;
    }

       /* public Response getOrderReceivedPage() throws IOException {
        checkout();

        Header header = new Header("content-type", "text/html");
        Headers headers = new Headers(header);
        Response response = given().
                baseUri(ConfigLoader.getInstance().getBaseUrl()).
                headers(headers).
                cookies(cookies).
                log().all().
        when().
                get(redirect).
                then().
                log().all().
                extract().response();

        String orderNumber = response.htmlPath().getString("//strong[normalize-space()='36412']");
        System.out.println("ORDERNUMBER IS: " + orderNumber);
        return response;
    }*/
}
