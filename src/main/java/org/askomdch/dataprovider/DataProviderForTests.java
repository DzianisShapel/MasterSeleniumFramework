package org.askomdch.dataprovider;

import io.restassured.http.Cookies;
import org.askomdch.api.CartApi;
import org.askomdch.api.SignUpApi;
import org.askomdch.objects.BillingAddress;
import org.askomdch.objects.Coupon;
import org.askomdch.objects.Product;
import org.askomdch.objects.User;
import org.askomdch.utils.FakerUtils;
import org.askomdch.utils.JacksonUtils;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class DataProviderForTests {

    public static FakerUtils fakeData = new FakerUtils();

    @DataProvider(name = "getStoreProducts")
    public static Iterator<Product> getStoreProducts() throws IOException {

        List<Product> productsList = new LinkedList<>(Arrays.asList(JacksonUtils.deserializeJson("products.json", Product[].class)));
        Iterator<Product> iterator = productsList.iterator();
        while (iterator.hasNext()) {
            Product pr = iterator.next();
            if (pr.getId() != 1215 && pr.getId() != 1198) {
                iterator.remove();
            }
        }
        //   productsList = productsList.stream().filter(product -> product.getId() != 1215 && product.getId() != 1198).collect(Collectors.toList());
        return productsList.iterator();
    }

    @DataProvider(name = "getUSABillingAddresses")
    public static Iterator<BillingAddress> getUSABillingAddresses() throws IOException {
        BillingAddress[] usaBillingAddresses = JacksonUtils.deserializeJson("billingAddresses.json", BillingAddress[].class);
        List<BillingAddress> usaBillingAddressesList = new ArrayList<>(Arrays.asList(usaBillingAddresses));
        Iterator<BillingAddress> iterator = usaBillingAddressesList.iterator();
        while (iterator.hasNext()) {
            BillingAddress bl = iterator.next();
            if (!"United States (US)".equals(bl.getCountry())) {
                iterator.remove();
            }
        }
        return usaBillingAddressesList.iterator();
    }

    //should be updated
    @DataProvider(name = "getBillingAddresses")
    public static Object[] getBillingAddresses() throws IOException {
        return JacksonUtils.deserializeJson("billingAddresses.json", BillingAddress[].class);
    }

    @DataProvider(name = "getDataForCheckout")
    public static Object[][] getDataForCheckout(Method m) throws IOException {
        User user = createUser();
        //Object[][] checkoutData;

        return new Object[][] {
                {getRandomElement(JacksonUtils.deserializeJson("billingAddresses.json", BillingAddress[].class)), generateCartApiCookie()},
                {getRandomElement(JacksonUtils.deserializeJson("billingAddresses.json", BillingAddress[].class)), generateCartApiCookie(signUpUser(user))}
        };


        /*switch (m.getName()) {
            case "CheckoutUsingCashOnDelivery":
                checkoutData = new Object[][] {
                        {getRandomElement(JacksonUtils.deserializeJson("billingAddresses.json", editBillingAddress[].class)), generateCartApiCookie()},
                        {getRandomElement(JacksonUtils.deserializeJson("billingAddresses.json", editBillingAddress[].class)), generateCartApiCookie(signUpUser(user))}
                };
                break;
            case "CheckoutUsingDirectBankTransfer":
                checkoutData = new Object[][] {
                        {getRandomElement(JacksonUtils.deserializeJson("billingAddresses.json", editBillingAddress[].class)), generateCartApiCookie()},
                        {getRandomElement(JacksonUtils.deserializeJson("billingAddresses.json", editBillingAddress[].class)), generateCartApiCookie(signUpUser(user))}
                };
                break;
            default:
                checkoutData = new Object[][]{
                        {getRandomElement(JacksonUtils.deserializeJson("billingAddresses.json", editBillingAddress[].class))}
                };
        }*/
       //return checkoutData;
    }

    @DataProvider(name = "getOneProduct", parallel = true)
    public static Object[][] getOneProduct() throws IOException {
        return new Object[][] {
                {getRandomElement(JacksonUtils.deserializeJson("products.json", Product[].class))}
        };

    }

    @DataProvider(name = "getFeaturedProducts", parallel = true)
    public static Iterator<Product> getFeaturedProducts() throws IOException {

        List<Product> productsList = new ArrayList<>(Arrays.asList(JacksonUtils.deserializeJson("products.json", Product[].class)));
        productsList = productsList.stream().filter(product -> product.getFeatured() == true).collect(Collectors.toList());
        return productsList.iterator();
    }

    @DataProvider(name = "getCoupons")
    public static Object[] getCoupons() throws IOException {
        return JacksonUtils.deserializeJson("coupons.json", Coupon[].class);
    }

    @DataProvider(name = "generateLoginData")
    public static Object[][] generateLoginData(Method m) {
        User user = createUser();
        signUpUser(user);
        Object[][] loginData;

        switch (m.getName()) {
            case "invalidLogin":
                String incorrectUsername = fakeData.generateRandomUsername();
                loginData = new Object[][]{
                        {user.getUsername(), fakeData.generateRandomPassword(), "Error: The password you entered for the username " + user.getUsername() +
                                " is incorrect. Lost your password?"},
                        {incorrectUsername, user.getPassword(), "Error: The username " + incorrectUsername + " is not registered on this site." +
                                " If you are unsure of your username, try your email address instead."}
                };
                break;
            default:
                loginData = new Object[][]{
                        {user.getUsername(), user.getPassword(), "Hello " + user.getUsername() + " (not " + user.getUsername() + "? Log out)"}
                };
        }
        return loginData;
    }

    public static User createUser() {

        String username = "demouser" + fakeData.generateRandomNumber();
        String password = fakeData.generateRandomPassword();
        User user = new User().
                setPassword(password).
                setUsername(username).
                setEmail(username + "@fake.com");
        return user;
    }

    public static Cookies signUpUser(User user) {
        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);
        return signUpApi.getCookies();
    }

    public static Cookies generateCartApiCookie() throws IOException {
        Product product = getRandomElement(JacksonUtils.deserializeJson("products.json", Product[].class));
        CartApi cartApi = new CartApi();
        cartApi.addToCart(product.getId(),1);
        return cartApi.getCookies();
    }

    public static Cookies generateCartApiCookie(Cookies cookies) throws IOException {
        Product product = getRandomElement(JacksonUtils.deserializeJson("products.json", Product[].class));
        CartApi cartApi = new CartApi(cookies);
        cartApi.addToCart(product.getId(),1);
        return cartApi.getCookies();
    }
    public static <T> T getRandomElement(T[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }
}