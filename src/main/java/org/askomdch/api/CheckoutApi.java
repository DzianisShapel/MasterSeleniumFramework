package org.askomdch.api;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.askomdch.utils.ConfigLoader;
import org.askomdch.utils.FakerUtils;
import org.askomdch.utils.JacksonUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.askomdch.api.actions.ApiRequest;
import org.askomdch.objects.BillingAddress;
import org.askomdch.objects.Product;
import org.askomdch.objects.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CheckoutApi {

    private Cookies cookies;

    public Cookies getCookies() {
        return cookies;
    }

    private Integer orderNumber;
    private String orderDate;

    public CheckoutApi() {
    }

    public CheckoutApi(Cookies cookies) {
        this.cookies = cookies;
    }

    public Response checkout(Product product) throws IOException {

        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
        String username = "demouser" + new FakerUtils().generateRandomNumber();
        User user = new User().
                setPassword("demopwd").
                setUsername(username).
                setEmail(username + "@fake.com");
        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);
        Cookie loggedInsignUpApiCookie = signUpApi.getCookies().get("wordpress_logged_in_e39c30ea93030240fdcfed1ddf385ba1");
        List<Cookie> loggedInCookie = new ArrayList<>();
        loggedInCookie.add(loggedInsignUpApiCookie);
        Cookies cookiesForInjection = new Cookies(loggedInCookie);

        CartApi cartApi = new CartApi(signUpApi.getCookies());
        cartApi.addToCart(product.getId(), 1);
       // cookies = cartApi.getCookies();
        cookies = signUpApi.getCookies();

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

        Response response = RestAssured.given().
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

        orderNumber = response.body().jsonPath().getInt("order_id");
        String redirect = response.body().jsonPath().getString("redirect");

        Response orderReceivedPage = RestAssured.given().
                baseUri(ConfigLoader.getInstance().getBaseUrl()).
                headers(headers).
                cookies(cookies).
                log().all().
        when().
                get(redirect).
        then().
                log().all().
                extract().response();

        Document doc = Jsoup.parse(orderReceivedPage.body().prettyPrint());
        extractDate(doc);

        this.cookies = cookiesForInjection;
        return response;
    }

    private String extractDate(Document doc) {
        Element element  = doc.selectFirst(".woocommerce-order-overview__date.date");
        String receivedOrderDate = element.text();
        return orderDate = formatString(receivedOrderDate);
    }

    private String formatString(String receivedDate){
        String formatedStateTax = receivedDate.replace("Date: ","");
        return formatedStateTax;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

}
