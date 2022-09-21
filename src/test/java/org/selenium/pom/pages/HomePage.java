package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.objects.Product;
import org.selenium.pom.pages.components.MyHeader;
import org.selenium.pom.pages.components.ProductThumbnail;

import java.io.IOException;

public class HomePage extends BasePage {

    private MyHeader myHeader;
    private ProductThumbnail productThumbnail;


    /*it has created the parametrized constructor with this argument and using the super keyword and passing this driver to the constructor of the BasePage.
    So this way, the driver in the BasePage is getting set by the homepage.*/
    public HomePage(WebDriver driver) {
        super(driver);
        myHeader= new MyHeader(driver);
        productThumbnail = new ProductThumbnail(driver);
    }

    public ProductPage navigateToProductPage(int id) throws IOException {
        driver.findElement(By.xpath("//h2[normalize-space()='"+ new Product(id).getName() + "']")).click();
        return new ProductPage(driver);
    }


    public HomePage load(){
        load("/");
        return this;
    }

    public MyHeader getMyHeader() {
        return myHeader;
    }

    public ProductThumbnail getProductThumbnail() {
        return productThumbnail;
    }
}
