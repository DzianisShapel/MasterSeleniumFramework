package org.selenium.pom.tests;

import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.Product;
import org.selenium.pom.pages.HomePage;
import org.selenium.pom.pages.ProductPage;
import org.selenium.pom.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class NavigationTest extends BaseTest {

    @Test
    public void NavigateFromHomeToStoreUsingMainMenu() throws InterruptedException {
        StorePage storePage = new HomePage(getDriver()).
                load().getMyHeader().
                navigateToStoreUsingMenu();
        Assert.assertTrue(storePage.getTitle().contains("Store"));
    }

    @Test
    public void NavigateFromStoreToProductPage() throws IOException {
        ProductPage productPage = new StorePage(getDriver()).
                load().
                navigateToProductPage(1215);
        Assert.assertTrue(productPage.getProductTitle().equals(new Product(1215).getName()));
    }

    @Test
    public void NavigateFromHomeToFeaturedProductPage() throws IOException {
        ProductPage productPage = new HomePage(getDriver()).
                load().
                navigateToProductPage(1198);
        Assert.assertTrue(productPage.getProductTitle().equals(new Product(1198).getName()));
    }
}
