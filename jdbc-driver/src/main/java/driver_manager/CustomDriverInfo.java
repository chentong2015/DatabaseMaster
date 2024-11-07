package driver_manager;

import java.sql.Driver;
import java.sql.DriverAction;

public class CustomDriverInfo {

    final Driver driver;
    DriverAction driverAction;

    public CustomDriverInfo(Driver driver, DriverAction action) {
        this.driver = driver;
        this.driverAction = action;
    }

    public Driver getDriver() {
        return driver;
    }

    public DriverAction getDriverAction() {
        return driverAction;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof CustomDriverInfo) && this.driver == ((CustomDriverInfo) other).driver;
    }

    @Override
    public int hashCode() {
        return driver.hashCode();
    }

    @Override
    public String toString() {
        return ("driver[className=" + driver + "]");
    }
}
