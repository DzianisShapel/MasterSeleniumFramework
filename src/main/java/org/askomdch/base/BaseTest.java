package org.askomdch.base;

import org.askomdch.factory.DriverManagerOriginal;
import org.askomdch.factory.abstractFactory.DriverManagerAbstract;
import io.restassured.http.Cookies;
import org.apache.commons.io.FileUtils;
import org.askomdch.utils.CookieUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class BaseTest {
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private final ThreadLocal<DriverManagerAbstract> driverManager = new ThreadLocal<>();

    private void setDriverManager(DriverManagerAbstract driverManager) {
        this.driverManager.set(driverManager);
    }

    protected DriverManagerAbstract getDriverManager() {
        return this.driverManager.get();
    }

    //only that class can set the driver
    private void setDriver(WebDriver driver) {
        this.driver.set(driver);
    }

   //getDriver can be used only by classes that inherits BaseTest class. (encapsulation)
    protected WebDriver getDriver() {
        return this.driver.get();
    }

    @Parameters({"browser"})
    @BeforeMethod
    public synchronized void startDriver(@Optional String browser) {
        //will take browser from TestNG.xml
        browser = System.getProperty("browser", browser);
//        String browser = "FIREFOX";
//        driver = new DriverManager().initializeDriver(browser);
//        if(browser == null) browser = "CHROME";
//*      first implementation
        setDriver(new DriverManagerOriginal().initializeDriver(browser));
        // used with DriverManage interface
  //      setDriver(DriverManagerFactory.getManager(DriverType.valueOf(browser)).createDriver());
        //implementation of abstract class (abstractFactory package)
//        setDriverManager(DriverManagerFactoryAbstract.
//                getManager(DriverType.valueOf(browser)));
//        setDriver(getDriverManager().getDriver());
        System.out.println("CURRENT THREAD: " + Thread.currentThread().getId() + ", " +
                "DRIVER = " + getDriver());
    }

    @Parameters("browser")
    @AfterMethod
    public synchronized void quitDriver(@Optional String browser, ITestResult result) throws InterruptedException, IOException {
        Thread.sleep(100);
        System.out.println("CURRENT THREAD: " + Thread.currentThread().getId() + ", " +
                "DRIVER = " + getDriver());
//        getDriverManager().getDriver().quit();
        if (result.getStatus() == ITestResult.FAILURE) {
            File destFile = new File("scr" + File.separator + browser + File.separator +
                    result.getTestClass().getRealClass().getSimpleName() + "_" +
                    result.getMethod().getMethodName() + ".png");
//            takeScreenshot(destFile);
            takeScreenshotUsingAShot(destFile);
        }
        getDriver().quit();
    }

    public void injectCookiesToBrowser(Cookies cookies) {
        List<Cookie> seleniumCookies = new CookieUtils().convertRestAssuredCookiesToSeleniumCookies(cookies);
        for (Cookie cookie : seleniumCookies) {
            getDriver().manage().addCookie(cookie);
        }
    }

    //Section 32
    private void takeScreenshot(File destFile) throws IOException {
        TakesScreenshot takeScreenshot = (TakesScreenshot) getDriver();
        File srcFile = takeScreenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, destFile);
    }

    private void takeScreenshotUsingAShot(File destFile) {
        Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100))
                .takeScreenshot(getDriver());
        try {
            ImageIO.write(screenshot.getImage(), "PNG", destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
