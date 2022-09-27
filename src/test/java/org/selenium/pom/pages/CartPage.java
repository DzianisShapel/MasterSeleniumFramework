package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    private final By productName = By.cssSelector("td[class='product-name'] a");
    private final By checkoutBtn = By.cssSelector(".checkout-button");
    private final By applyCouponBtn = By.cssSelector("button[value='Apply coupon']");
    private final By couponCodeFld = By.cssSelector("#coupon_code");

    /*// PageFactory sample
    @FindBy(css = "td[class='product-name'] a")
    private WebElement productName;

    @FindBy(css = ".checkout-button")
    @CacheLookup
    private WebElement checkoutBtn;*/

    //With pageFactory we need to initialize the elements
    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public CartPage load(){
        load("/cart/");
        return this;
    }

    public String getProductName(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
    }

    public List<String> getProductsFromCart(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(productName));
        List<WebElement> orderProducts = driver.findElements(productName);
        List<String> orderProductsName = new ArrayList<>();
        for (WebElement  orderProduct: orderProducts) {
            orderProductsName.add(orderProduct.getText());
        }
        return orderProductsName;
    }

    public CartPage applyCoupon(String coupon) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(couponCodeFld)).sendKeys(coupon);
        driver.findElement(applyCouponBtn).click();
        return this;
    }

    public CheckoutPage checkout(){
        wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
        return new CheckoutPage(driver);
    }
}
