package org.askomdch.factory;

import org.askomdch.constants.DriverType;

public class DriverManagerFactory {

    public static DriverManager getManager(DriverType driverType) {
        //based on driverType we are sending it is returning a new object of the corresponding class
        // which is implementing DriverManager interface
        DriverManager manager;
        switch (driverType) {
            case CHROME: manager = new ChromeDriverManager();
            break;
            case FIREFOX:
                manager =  new FirefoxDriverManager();
            break;
            default:
                throw new IllegalStateException("Invalid driver: " + driverType);
        }
        return manager;
    }
}
