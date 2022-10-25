package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.objects.Product;
import java.io.IOException;

public class OrdersPage extends BasePage {

    private final By viewOrdersBtn = By.cssSelector(".woocommerce-button.button.view");
    private final By orderDetails = By.cssSelector(".woocommerce-order-details__title");
    private final By orderString = By.cssSelector("body > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > main:nth-child(1) > article:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > p:nth-child(2)");


    public OrdersPage(WebDriver driver) {
        super(driver);
    }
    public  OrdersPage viewOrder(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewOrdersBtn)).click();
        return this;
    }

    public String getOrderDetailsTitle(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderDetails)).getText();
    }

    public  String getOrderedProductName(Product product) throws IOException {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='"+ product.getName() + "']"))).getText();
    }

    public String getOrderString() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderString)).getText();
    }

    public OrdersPage load(){
        load("/account/orders");
        return this;
    }
}
