package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.User;

public class CheckoutPage extends BasePage {

    private final By firstNameFld = By.id("billing_first_name");
    private final By lastNameFld = By.id("billing_last_name");
    private final By billingAddressFld = By.id("billing_address_1");
    private final By billingCityFld = By.id("billing_city");
    private final By billingPostCodeFld = By.id("billing_postcode");
    private final By billingEmail = By.id("billing_email");
    private final By placeOrderBtn = By.id("place_order");
    private final By successNotice = By.cssSelector(".woocommerce-notice");

    private final By clickHereToLoginLink = By.className("showlogin");
    private final By usernameFld = By.id("username");
    private final By passwordFld = By.id("password");
    private final By loginBtn = By.name("login");
    private final By ovarlay = By.cssSelector(".blockUI.blockOverlay");

    private final By countryDropDown = By.id("billing_country");
    private final By stateDropDown = By.id("billing_state");
    private final By alternateCountryDropdown = By.id("select2-billing_country-container");
    private final By alternateStateDropdown = By.id("select2-billing_state-container");

    private final By directBankTransferRadioBtn = By.id("payment_method_bacs");
    private final By cashOnDeliveryTransferRadioBtn = By.id("payment_method_cod");

    private final By productName = By.cssSelector("td[class='product-name']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage load(){
        load("/checkout/");
        return this;
    }

    public CheckoutPage setFirstNameFld(String firstName){
        WebElement e = waitForElementToBeVisible(firstNameFld);
        e.clear();
        e.sendKeys(firstName);
        return this;
    }

    public CheckoutPage setLastNameFld(String lastName){
        driver.findElement(lastNameFld).clear();
        driver.findElement(lastNameFld).sendKeys(lastName);
        return this;
    }

    public CheckoutPage setBillingAddressFld(String billingAddress){
        driver.findElement(billingAddressFld).clear();
        driver.findElement(billingAddressFld).sendKeys(billingAddress);
        return this;
    }

    public CheckoutPage setBillingCityFld(String billingCity){
        driver.findElement(billingCityFld).clear();
        driver.findElement(billingCityFld).sendKeys(billingCity);
        return this;
    }

    public CheckoutPage setBillingPostCodeFld(String postCode){
        driver.findElement(billingPostCodeFld).clear();
        driver.findElement(billingPostCodeFld).sendKeys(postCode);
        return this;
    }

    public CheckoutPage setBillingEmailFld(String email){
        driver.findElement(billingEmail).clear();
        driver.findElement(billingEmail).sendKeys(email);
        return this;
    }

    public CheckoutPage placeOrder(){
        waitForOverlaysToDisappear(ovarlay);
        driver.findElement(placeOrderBtn).click();
        return this;
    }

    public String getNotice(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successNotice)).getText();
    }

    public CheckoutPage clickHereToLoginLink(){
        wait.until(ExpectedConditions.elementToBeClickable(clickHereToLoginLink)).click();
        return this;
    }

    public CheckoutPage enterUserName(String username){
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameFld)).sendKeys(username);
        return this;
    }

    public CheckoutPage enterPassword(String password){
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordFld)).sendKeys(password);
        return this;
    }

    public CheckoutPage clickLoginBtn(){
       wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
        return this;
    }

    private CheckoutPage waitForLoginBtnToDisappear() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loginBtn));
        return this;
    }

    public CheckoutPage login(User user){
       return enterUserName(user.getUsername()).
               enterPassword(user.getPassword()).
               clickLoginBtn().waitForLoginBtnToDisappear();
    }

    public CheckoutPage setBillingAddress(BillingAddress billingAddress){
        return  setFirstNameFld(billingAddress.getFirstName()).
                setLastNameFld(billingAddress.getLastName()).
                selectCountry(billingAddress.getCountry()).
                setBillingAddressFld(billingAddress.getAddressLineOne()).
                setBillingCityFld(billingAddress.getCity()).
                selectState(billingAddress.getState()).
                setBillingPostCodeFld(billingAddress.getPostalCode()).
                setBillingEmailFld(billingAddress.getEmail());
    }


    public CheckoutPage selectCountry(String countryName){
    /* select class didn't work for Firefox
        Select select = new Select(wait.until(ExpectedConditions.elementToBeClickable(countryDropDown)));
        select.selectByVisibleText(countryName);*/

        wait.until(ExpectedConditions.elementToBeClickable(alternateCountryDropdown)).click();
        WebElement e = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//li[text()= '" + countryName + "']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", e);
        e.click();
        return this;
    }

    public CheckoutPage selectState(String stateName){
      /*  Select select = new Select(driver.findElement(stateDropDown));
        select.selectByVisibleText(stateName);*/

        wait.until(ExpectedConditions.elementToBeClickable(alternateStateDropdown)).click();
        WebElement e = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[text() = '" + stateName + "']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", e);
        e.click();
        return this;
    }

    public CheckoutPage selectDirectBankTransfer(){
     WebElement e = wait.until(ExpectedConditions.elementToBeClickable(directBankTransferRadioBtn));
     if (!e.isSelected()) {
            e.click();
     }
     return this;
    }

    public CheckoutPage selectCashOnDeliveryTransfer(){
        WebElement e = wait.until(ExpectedConditions.elementToBeClickable(cashOnDeliveryTransferRadioBtn));
        if (!e.isSelected()) {
            e.click();
        }
        return this;
    }

    public String getProductName(){
       return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
    }

    /*additional solution to handle StaleElementReferenceException
    * public String getProductName() throws Exception {
         int i = 5;
       * int timeout = 30;
       * while(i > 0) {
       * try {
       * return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
       * } catch (StaleElementReferenceException e) {
       * System.out.println("NOT FOUND. TRYING AGAIN" + e);
       * }
       * Thread.sleep(5000);
       * i--;
  }
  * throw new Exception("Element not found")
  * }
    * */

}
