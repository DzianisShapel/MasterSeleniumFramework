package org.selenium.pom.api.actions;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.selenium.pom.utils.ConfigLoader;

public class SpecBuilder {

    public static RequestSpecification getRequestSpecification(){

        return new RequestSpecBuilder().
                setBaseUri(ConfigLoader.getInstance().getBaseUrl()).
                addFilter(new AllureRestAssured()).
//                log(LogDetail.ALL).
                build();
    }

    public static ResponseSpecification getResponseSpecification(){
        return  new ResponseSpecBuilder().
//                log(LogDetail.ALL).
                build();
    }
}
