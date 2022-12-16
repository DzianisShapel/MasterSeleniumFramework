package org.askomdch.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.askomdch.base.BasePage;

public class AccountPage extends BasePage {

    private final By usernameFld =  By.cssSelector("#username");
    private final By passwordFld =  By.cssSelector("#password");
    private final By loginBtn =  By.cssSelector("button[value='Log in']");
    private final By helloMessage = By.xpath("//div[@class='woocommerce-notices-wrapper']/following-sibling::p");
    private final By errorMessage = By.xpath("//ul[@class='woocommerce-error']/child::li");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public AccountPage login(String username, String password){
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameFld)).sendKeys(username);
        driver.findElement(passwordFld).sendKeys(password);
        driver.findElement(loginBtn).click();
        return this;
    }

    /*
    public AccountPage navigateToOrders(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(orders)).click();
        return this;
    }*/

    public AccountPage load(){
        load("/account/");
        return this;
    }

    public String getHelloMessage() {
        return driver.findElement(helloMessage).getText();
    }

    public String getErrorMessage(){
        return driver.findElement(errorMessage).getText();
    }
}
