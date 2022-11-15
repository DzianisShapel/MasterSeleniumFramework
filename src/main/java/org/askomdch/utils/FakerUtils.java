package org.askomdch.utils;

import com.github.javafaker.Faker;

public class FakerUtils {

    public Long generateRandomNumber(){
        Faker faker = new Faker();
        return faker.number().randomNumber(10, true);
    }
    public String generateRandomString(){
        Faker faker = new Faker();
        return faker.food().fruit();
    }

    public String generateRandomUsername(){
        Faker faker = new Faker();
        return faker.name().username();
    }

    public String generateRandomPassword(){
        Faker faker = new Faker();
        return faker.internet().password();
    }

    public String generateFirstName(){
        Faker faker = new Faker();
        return faker.name().firstName();
    }

    public String generateLastName(){
        Faker faker = new Faker();
        return faker.name().lastName();
    }




}
