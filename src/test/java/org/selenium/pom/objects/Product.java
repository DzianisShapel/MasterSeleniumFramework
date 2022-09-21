package org.selenium.pom.objects;

import org.selenium.pom.utils.JacksonUtils;

import java.io.IOException;

public class Product {

    private int id;
    private String name;
    private Boolean featured;

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
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getFeatured() {
        return featured;
    }
}
