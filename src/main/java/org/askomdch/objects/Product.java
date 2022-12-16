package org.askomdch.objects;

import org.askomdch.utils.JacksonUtils;


import java.io.IOException;

public class Product {

    private int id;
    private String name;
    private Boolean featured;
    private String apiName;

    public Product() {
    }
    /*with this we got a JSON array extracted as array of type Product
    We can loop through this array to extract our desired product*/
    public Product(int id) throws IOException {
        Product[] products = JacksonUtils.deserializeJson("products.json", Product[].class);
        /*
        * with this we are setting the id and name field for the product object and then we can use  it in our test class
        * */
        for (Product product: products) {
            if(product.getId() == id) {
                this.id = id;
                this.name = product.getName();
                this.featured = product.getFeatured();
                this.apiName = product.getApiName();
            }
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiName() {
        return apiName;
    }

    public Boolean getFeatured() {
        return featured;
    }

}
