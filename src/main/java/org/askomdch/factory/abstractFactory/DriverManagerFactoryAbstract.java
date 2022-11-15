package org.askomdch.factory.abstractFactory;

import org.askomdch.constants.DriverType;

public class DriverManagerFactoryAbstract {

    public static DriverManagerAbstract getManager(DriverType driverType) {
        //based on driverType we are sending it is returning a new object of the corresponding class
        // which is implementing DriverManager interface
        switch (driverType) {
            case CHROME -> {
                return new ChromeDriverManagerAbstract();
            }
            case FIREFOX -> {
                return new FirefoxDriverManagerAbstract();
            }
            default -> throw new IllegalStateException("Invalid driver: " + driverType);
        }
    }
}
