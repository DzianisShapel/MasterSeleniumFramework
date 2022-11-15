package org.askomdch.base;

import org.askomdch.utils.ConfigLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver){
            this.driver = driver;
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }


    public void waitForOverlaysToDisappear(By ovarlay){
        List<WebElement> overlays = driver.findElements(ovarlay);
        System.out.println("OVERLAY SIZE: " + overlays.size());
        if(overlays.size() > 0){
            wait.until(
                    ExpectedConditions.invisibilityOfAllElements(overlays)
            );
            System.out.println("OVERLAYS ARE INVISIBLE: ");
        } else {
            System.out.println("OVERLAY IS NOT FOUND");
        }
    }

    public void load(String endPoint){
        driver.get(ConfigLoader.getInstance().getBaseUrl() + endPoint);
    }

    public WebElement waitForElementToBeVisible(By element){
       return  wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }


}
