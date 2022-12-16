package org.askomdch.api;


import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.askomdch.api.actions.ApiRequest;
import org.askomdch.dataprovider.DataProviderForTests;
import org.askomdch.objects.BillingAddress;
import org.askomdch.objects.User;
import org.askomdch.utils.ConfigLoader;
import org.askomdch.utils.FakerUtils;
import org.askomdch.utils.JacksonUtils;
import java.io.IOException;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class BillingAddressApi {
    private Cookies cookies;

    public Cookies getCookies() {
        return cookies;
    }


    public Response editBillingAddress() throws IOException {
        User user = DataProviderForTests.createUser();
        BillingAddress billingAddress = DataProviderForTests.getRandomElement(JacksonUtils.deserializeJson("billingAddresses.json", BillingAddress[].class));
        Cookies cookies = DataProviderForTests.signUpUser(user);

        Header header = new Header("Content-Type", "application/x-www-form-urlencoded");
        Headers headers = new Headers(header);
        HashMap<String, String> formParams = new HashMap<>();
        formParams.put("billing_first_name", new FakerUtils().generateFirstName());
        formParams.put("billing_last_name", new FakerUtils().generateLastName());
        formParams.put("billing_country", billingAddress.getCountryCode());
        formParams.put("billing_address_1", billingAddress.getAddressLineOne());
        formParams.put("billing_city", billingAddress.getCity());
        formParams.put("billing_state", billingAddress.getStateCode());
        formParams.put("billing_postcode", billingAddress.getPostalCode());
        formParams.put("billing_email", user.getEmail());
        formParams.put("billing_company", "TDC");
        formParams.put("billing_phone", "80152455265");
        formParams.put("save_address", "Save address");
        formParams.put("woocommerce-edit-address-nonce", fetchEditAddressNonceValue());
        formParams.put("action", "edit_address");
        formParams.put("_wp_http_referer","/account/edit-address/billing/");

        Response response =
                given().
                        baseUri(ConfigLoader.getInstance().getBaseUrl()).
                        headers(headers).
                        formParams(formParams).
                        cookies(cookies).
                        log().all().
                        when().
                post("/account/edit-address/billing/").
                        then().
                        log().all().
                extract().
                        response();
        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Failed to edit the address of the account -" + response.getStatusCode());
        }
        this.cookies = response.getDetailedCookies();
        return response;
    }

    private String fetchEditAddressNonceValue(){
        Response response = getEditAddressPage();
        return response.htmlPath().getString("**.findAll { it.@name == 'woocommerce-edit-address-nonce' }.@value");
    }

    private Response getEditAddressPage(){
        Cookies cookies = new Cookies();
        Response response = ApiRequest.get("/account/edit-address/billing/", cookies);
        if(response.getStatusCode() != 200){
            throw new RuntimeException("Failed to fetch the account, HTTP Status Code: " + response.getStatusCode());
        }
        return response;
    }

}
