package org.thenin.google.apps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

/**
 * Created by kguryanov on 6/18/2015.
 */
public class GmailTest {
    WebDriver driver;


    @BeforeTest
    @Parameters({"driverExec"})
    public void startUp(String driverExecPath){
        driver = new FirefoxDriver();
    }

    @Test
    @Parameters({"email","password","firstName","lastName"})
    public void LoginGmail(String email, String password, String firstName, String lastname){

        GoogleGmail gmailApp = (GoogleGmail) new GoogleGmail(driver).getLoginPage().loginAs(email, password);
        String newString  = gmailApp.TITLE;

        gmailApp.selfTestTitle();
        gmailApp.selfTestUser(firstName);

        GoogleAccounts.LoginPage logoutResult = gmailApp.doLogOut();
        logoutResult.selfTestTitle();

    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
