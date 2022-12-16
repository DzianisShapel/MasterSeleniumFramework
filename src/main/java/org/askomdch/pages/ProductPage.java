package org.askomdch.pages;

import org.askomdch.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductPage extends BasePage {

    private final By addToCartBtn = By.xpath("//button[@class='single_add_to_cart_button button alt']");

    private final By productTitle = By.cssSelector(".product_title.entry-title");
    private final By alert = By.cssSelector("div[role='alert']");


    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String getProductTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productTitle)).getText();
    }

    public ProductPage loadProductPage(String apiName){
        load("/product/" + apiName + "/");
        return this;
    }

    public ProductPage clickAddToCartBtn(){
        driver.findElement(addToCartBtn).click();
        return this;
    }

    public String getAlert(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(alert)).getText();
    }


}
