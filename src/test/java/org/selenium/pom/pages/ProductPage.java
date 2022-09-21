package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;

public class ProductPage extends BasePage {

    private final By addToCartBtn = By.cssSelector("button[value='1210']");

    private final By productTitle = By.cssSelector(".product_title.entry-title");
    private final By alert = By.cssSelector("div[role='alert']");


    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String getProductTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productTitle)).getText();
    }

    public ProductPage load(){
        load("/product/dark-grey-jeans/");
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
