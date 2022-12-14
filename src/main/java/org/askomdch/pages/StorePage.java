package org.askomdch.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.askomdch.base.BasePage;
import org.askomdch.objects.Product;
import org.askomdch.pages.components.ProductThumbnail;

import java.io.IOException;

public class StorePage extends BasePage {

    private final By searchFld = By.id("woocommerce-product-search-field-0");
    private final By searchBtn = By.cssSelector("button[value='Search']");
    private final By title = By.cssSelector(".woocommerce-products-header__title.page-title");
    private final By info = By.cssSelector(".woocommerce-info");
    private ProductThumbnail productThumbnail;



    public StorePage(WebDriver driver) {
        super(driver);
        productThumbnail = new ProductThumbnail(driver);
    }

    public ProductThumbnail getProductThumbnail() {
        return productThumbnail;
    }

    public StorePage enterTextInSearchFld(String txt) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchFld)).sendKeys(txt);
        return this;
    }

    public StorePage load(){
        load("/store");
        return this;
    }


    public StorePage search(String txt){
        enterTextInSearchFld(txt).clickSearchBtn();
        return this;
    }

    public ProductPage searchExactProduct(String txt){
        enterTextInSearchFld(txt).clickSearchBtn();
        return new ProductPage(driver);
    }

   public StorePage clickSearchBtn(){
       wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
        return this;
    }

    public String getTitle() throws InterruptedException {
//        wait.until(ExpectedConditions.urlContains("post_type=product"));
        Thread.sleep(2000);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).getText();
    }

    public ProductPage navigateToProductPage(int id) throws IOException {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2[normalize-space()='"+ new Product(id).getName() + "']"))).click();
//        driver.findElement(By.xpath("//h2[normalize-space()='"+ new Product(id).getName() + "']")).click();
        return new ProductPage(driver);
    }

    public String getInfo(){
//        wait.until(ExpectedConditions.urlContains("post_type=product"));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(info)).getText();
    }


}
