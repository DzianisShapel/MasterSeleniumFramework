package org.selenium.pom.tests;

import org.selenium.pom.base.BaseTest;
import org.selenium.pom.dataprovider.MyDataProvider;
import org.selenium.pom.objects.Product;
import org.selenium.pom.pages.CartPage;
import org.selenium.pom.pages.HomePage;
import org.selenium.pom.pages.ProductPage;
import org.selenium.pom.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddToCartTest extends BaseTest {

    @Test
    public void addOneProductToCartFromStorePage() throws IOException {
        Product product = new Product(1215);
        CartPage cartPage = new StorePage(getDriver()).load().
                getProductThumbnail().clickAddToCartBtn(product.getName()).
                clickViewCart();
        Assert.assertEquals(cartPage.getProductName(), product.getName());
    }


    /*@Test(dataProvider = "getStoreProducts", dataProviderClass = MyDataProvider.class)
    public void addMultipleProductsToCartFromStorePage(Product product) throws IOException {
        StorePage storePage = new StorePage(getDriver()).
                load();
        for (Product storeProduct: MyDataProvider.getStoreProducts()) {
            storePage.clickAddToCartBtn(storeProduct.getName());
        }
        storePage.clickAddToCartBtn(product.getName()).
                clickViewCart();
        //Assert.assertEquals(cartPage.getProductName(), product.getName());
    }*/


    @Test
    public void AddToCartFromProductPage() {
        ProductPage productPage = new ProductPage(getDriver()).
                load().
                clickAddToCartBtn();
        Assert.assertTrue(productPage.getAlert().contains("has been added to your cart."));
    }

    //Section 28
    @Test(dataProvider = "getFeaturedProducts", dataProviderClass = MyDataProvider.class)
    public void addFeaturedProductToCart(Product product) {
        CartPage cartPage = new HomePage(getDriver()).load().
                getProductThumbnail().clickAddToCartBtn(product.getName()).
                clickViewCart();
        Assert.assertEquals(cartPage.getProductName(), product.getName());
    }



}
