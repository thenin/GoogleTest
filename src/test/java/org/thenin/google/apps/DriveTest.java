package org.thenin.google.apps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

/**
 * Created by kguryanov on 6/19/2015.
 */
public class DriveTest {
    GoogleGmail gmailApp;
    GoogleDrive driveApp;
    List<WebElement> newFolders = null;

    WebDriver driver;

    @BeforeTest
    @Parameters({"driverExec"})
    public void startUp(String driverExecPath){
        System.setProperty("webdriver.chrome.driver", driverExecPath);
        driver = new ChromeDriver();
    }

    @BeforeMethod
    @Parameters({"email", "password", "firstName", "lastName"})
    public void doLogin(String email, String password, String firstName, String lastname){
        gmailApp = (GoogleGmail) new GoogleGmail(driver).getLoginPage().loginAs(email, password);

        gmailApp.selfTestTitle();
        gmailApp.selfTestUser(firstName);

        driveApp = gmailApp.doOpenDrive();
    }

    @Test
    @Parameters({"folderName"})
    public void TestDrive(String folderName) {

        newFolders =  driveApp.createFolder(folderName);
        Assert.assertEquals(newFolders.size(),1);
        Assert.assertEquals(newFolders.get(0).findElement(By.tagName("span")).getText(), folderName);
    }

    @AfterMethod
    public void doLogoff(){
        driveApp.deleteFolders(newFolders);
        driveApp.doLogOut();
    }


    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
