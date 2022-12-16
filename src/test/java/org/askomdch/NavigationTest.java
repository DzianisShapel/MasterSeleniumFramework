package org.askomdch;

import org.askomdch.base.BaseTest;
import org.askomdch.dataprovider.DataProviderForTests;
import org.askomdch.objects.Product;
import org.askomdch.pages.HomePage;
import org.askomdch.pages.StorePage;
import org.askomdch.pages.ProductPage;
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

    @Test(dataProvider = "getFeaturedProducts", dataProviderClass = DataProviderForTests.class)
    public void NavigateFromHomeToFeaturedProductPage(Product product) throws IOException {
        ProductPage productPage = new HomePage(getDriver()).
                load().
                navigateToProductPage(product.getId());
        Assert.assertTrue(productPage.getProductTitle().equals(product.getName()));
    }
}
