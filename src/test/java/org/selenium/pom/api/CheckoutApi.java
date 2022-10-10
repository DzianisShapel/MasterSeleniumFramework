package org.selenium.pom.api;

import io.restassured.http.Cookies;

public class CheckoutApi {

    private Cookies cookies;

    public Cookies getCookies() {
        return cookies;
    }

    public CheckoutApi(){}
    public CheckoutApi(Cookies cookies){
        this.cookies = cookies;
    }


}
