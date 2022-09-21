package org.selenium.pom.dataprovider;

import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.annotations.DataProvider;
import java.io.IOException;

public class MyDataProvider {

    /*
    need work!)))))
    @DataProvider(name = "getStoreProducts")
    public static Object[] getStoreProducts() throws IOException {
        Product[] products = JacksonUtils.deserializeJson("products.json", Product[].class);
        Product[] storeProducts = new Product[2];
        int currentIndex = 0;
        for (Product product : products) {
            if (product.getId() == 1215 || product.getId() == 1198) {
                storeProducts[currentIndex] = product;
                currentIndex++;
            }
        }
        return storeProducts;
    }*/


    //, parallel = true
    @DataProvider(name = "getFeaturedProducts", parallel = true)
    public Object[] getFeaturedProducts() throws IOException {
        Product[] products = JacksonUtils.deserializeJson("products.json", Product[].class);
        Product[] featuredProducts = new Product[calculateFeaturedProductSize(products)];
        int currentIndex = 0;
        for (Product product : products) {
            if (product.getFeatured() == true) {
                featuredProducts[currentIndex] = product;
                currentIndex++;
            }
        }
        return featuredProducts;
    }

    private static int calculateFeaturedProductSize(Product[] products) {
        int size = 0;
        for (Product product : products) {
            if (product.getFeatured() == true) {
                size++;
            }
        }
        return size;
    }

    @DataProvider(name = "getBillingAddresses")
    public Object[] getBillingAddresses() throws IOException {
        return JacksonUtils.deserializeJson("multipleBillingAddresses.json", BillingAddress[].class);
    }



}