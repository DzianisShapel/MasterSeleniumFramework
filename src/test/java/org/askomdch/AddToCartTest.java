package org.askomdch;

import org.askomdch.base.BaseTest;
import org.askomdch.dataprovider.MyDataProvider;
import org.askomdch.objects.Product;
import org.askomdch.pages.CartPage;
import org.askomdch.pages.HomePage;
import org.askomdch.pages.ProductPage;
import org.askomdch.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Iterator;

public class AddToCartTest extends BaseTest {

    @Test
    public void addOneProductToCartFromStorePage() throws IOException {
        Product product = new Product(1215);
        CartPage cartPage = new StorePage(getDriver()).load().
                getProductThumbnail().clickAddToCartBtn(product.getName()).
                clickViewCart();
        Assert.assertEquals(cartPage.getProductName(), product.getName());
    }

    @Test
    public void addMultipleProductsToCartFromStorePage() throws IOException {
        Iterator<Product> iterator = MyDataProvider.getStoreProducts();
        StorePage storePage = new StorePage(getDriver()).load();
        List<String> expectedProducts = new ArrayList<>();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            storePage.getProductThumbnail().clickAddToCartBtn(product.getName());
            expectedProducts.add(product.getName());
        }
        CartPage cartPage = storePage.getProductThumbnail().clickViewCart();
        List<String> actualProducts = cartPage.getProductsFromCart();
        Assert.assertEquals(actualProducts, expectedProducts);
    }

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