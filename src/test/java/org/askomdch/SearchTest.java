package org.askomdch;

import org.askomdch.base.BaseTest;
import org.askomdch.objects.Product;
import org.askomdch.pages.ProductPage;
import org.askomdch.pages.StorePage;
import org.askomdch.utils.FakerUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

public class SearchTest extends BaseTest {

    @Test
    public void searchWithPartialMatch() throws InterruptedException {
        String searchFor = "Blue";
        StorePage storePage = new StorePage(getDriver()).
                load().
                search(searchFor);
        Assert.assertEquals(storePage.getTitle(),"Search results: “" + searchFor + "”");
    }

    @Test
    public void searchWithExactMatch() throws IOException {
        Product product = new Product(1202);
        ProductPage productPage = new StorePage(getDriver()).
                load().
                searchExactProduct(product.getName());
        Assert.assertEquals(productPage.getProductTitle(),product.getName());
    }

    @Test
    public void SearchNonExistingProduct() throws IOException {
        FakerUtils unexistingProduct = new FakerUtils();

        StorePage storePage = new StorePage(getDriver()).
                load().
                search(unexistingProduct.generateRandomString());
        Assert.assertEquals(storePage.getInfo(),"No products were found matching your selection.");
    }
}
