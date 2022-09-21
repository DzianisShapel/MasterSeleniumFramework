package org.selenium.pom.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class FirefoxDriverManager implements  DriverManager {
    private WebDriver driver;

    @Override
    public WebDriver createDriver() {
        WebDriverManager.firefoxdriver().cachePath("Drivers").setup();
        WebDriver driver = new ChromeDriver();
        return driver;
    }
}
