package org.askomdch.pages;

import org.askomdch.base.BasePage;
import org.askomdch.objects.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;

public class OrdersPage extends BasePage {

    private final By viewOrdersBtn = By.cssSelector(".woocommerce-button.button.view");
    private final By orderDetails = By.cssSelector(".woocommerce-order-details__title");
    private final By orderString = By.xpath("//p[./mark]");

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
