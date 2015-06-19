package org.thenin.google.apps;

import org.openqa.selenium.WebDriver;

/**
 * Created by kguryanov on 6/18/2015.
 */
public class GoogleGmail extends GenericGoogleApp {

    public GoogleGmail(WebDriver driver) {
        super(driver);
        this.TITLE = "Inbox";
        this.LOGIN_TITLE = "Gmail";
        driver.get("http://www.gmail.com/");
    }
}
