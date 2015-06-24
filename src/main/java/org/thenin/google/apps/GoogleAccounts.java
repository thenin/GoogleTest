package org.thenin.google.apps;

import org.openqa.selenium.WebDriver;

/**
 * Created by kguryanov on 6/19/2015.
 * PageObject class for Google Accounts application
 */
public class GoogleAccounts extends GenericGoogleApp {
    public GoogleAccounts(WebDriver driver) {
        super(driver);
        this.TITLE = "My Account";
        this.LOGIN_TITLE = "Sign-In";
        driver.get("https://drive.google.com");
    }
}
