package org.selenium.pom.factory;

import org.selenium.pom.constants.DriverType;

public class DriverManagerFactory {

    public static DriverManager getManager(DriverType driverType) {
        //based on driverType we are sending it is returning a new object of the corresponding class
        // which is implementing DriverManager interface
        switch (driverType) {
            case CHROME -> {
                return new ChromeDriverManager();
            }
            case FIREFOX -> {
                return new FirefoxDriverManager();
            }
            default -> throw new IllegalStateException("Invalid driver: " + driverType);
        }
    }
}
