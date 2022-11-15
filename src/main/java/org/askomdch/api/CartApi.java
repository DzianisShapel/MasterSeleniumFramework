package org.askomdch.api;

import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.askomdch.utils.ConfigLoader;

import java.util.HashMap;

public class CartApi {

    private Cookies cookies;

    public CartApi(){};

    public CartApi(Cookies cookies){
        this.cookies = cookies;
    }

    public Cookies getCookies() {
        return cookies;
    }


    public Response addToCart(int productId, int quantity){

        Header header = new Header("content-type", "application/x-www-form-urlencoded");
        Headers headers = new Headers(header);

        HashMap<String, Object> formParams = new HashMap<>();
        formParams.put("product_sku", "");
        formParams.put("product_id", productId);
        formParams.put("quantity", quantity);

        if (cookies == null){
            cookies = new Cookies();
        }

        Response response = RestAssured.given().
                baseUri(ConfigLoader.getInstance().getBaseUrl()).
                headers(headers).
                formParams(formParams).
                cookies(cookies).
                log().all().
        when().
                post("/?wc-ajax=add_to_cart").
                then().
                log().all().
                extract().response();

        if(response.getStatusCode() != 200){
            throw new RuntimeException("Failed to add product, HTTP Status Code: " + response.getStatusCode());
        }
        this.cookies = response.getDetailedCookies();
        return response;
    }
}
