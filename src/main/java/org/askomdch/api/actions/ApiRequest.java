package org.askomdch.api.actions;

import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ApiRequest extends SpecBuilder {

    public static Response post(String endPoint, Headers headers,
                                HashMap<String, Object> formParams, Cookies cookies) {
        return  RestAssured.given(getRequestSpecification()).
                headers(headers).
                formParams(formParams).
                cookies(cookies).
        when().
                post(endPoint).
        then().spec(getResponseSpecification()).
                extract().
                response();
    }

    public static Response get(String endPoint, Cookies cookies) {
        return RestAssured.given(getRequestSpecification()).
                cookies(cookies).
        when().
                get(endPoint).
        then().spec(getResponseSpecification()).
                extract().
                response();
    }



}
