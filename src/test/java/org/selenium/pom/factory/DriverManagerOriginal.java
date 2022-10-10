package org.selenium.pom.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.selenium.pom.constants.DriverType;

public class DriverManagerOriginal {

    //the only job of this method is to initialize the driver and return the WebDriver object
    public WebDriver initializeDriver(String browser) {

        WebDriver driver;
        //use JVM argument or maven property
         String localBrowser;
        localBrowser = System.getProperty("browser", "CHROME");

        //use testNG XML. AND do not forget to remove jvm argument
       //localBrowser = browser;
        switch (DriverType.valueOf(browser)) {
            case CHROME:
                WebDriverManager.chromedriver().cachePath("Drivers").setup();
                driver = new ChromeDriver();
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().cachePath("Drivers").setup();
                driver = new FirefoxDriver();
                break;
            default:
                throw new IllegalStateException("Invalid browser name: " + browser);
        }

    return driver;
    }
}

      /*cachePath("Drivers") will create Drivers folder in the root path and save chromedriver to the folder.
      WebDriverManager.chromedriver().cachePath("Drivers").setup();
      //  WebDriverManager.firefoxdriver().cachePath("Drivers").setup();
      WebDriver driver = new ChromeDriver();
      //  WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
 //       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));
        return driver;*/


