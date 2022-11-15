package org.askomdch.pages.components;

import org.askomdch.base.BasePage;
import org.askomdch.pages.StorePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyHeader extends BasePage {

    public MyHeader(WebDriver driver) {
        super(driver);
    }

    private final By storeMenuLink = By.cssSelector("#menu-item-1227 > a");

    public StorePage navigateToStoreUsingMenu(){
        driver.findElement(storeMenuLink).click();
        return new StorePage(driver);
    }
}
