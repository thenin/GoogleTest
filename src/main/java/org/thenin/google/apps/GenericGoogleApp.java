package org.thenin.google.apps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by kguryanov on 6/18/2015.
 * Generic class for a Google Application
 */
public abstract class GenericGoogleApp {
    String TITLE;
    String LOGIN_TITLE;
    private String email;
    final WebDriver driver;

    private final By accountName = By.xpath("//*[@id=\"gb\"]/div[1]/div[1]/div[1]/div/span");
    private final By apps = By.xpath("//*[@id=\"gbwa\"]/div[1]/a");
    private final By notifications = By.xpath("//*[@id=\"gb\"]/div[1]/div[1]/div[2]/div[3]/div[1]/a");
    private final By settings = By.xpath("//*[@id=\"gb\"]/div[1]/div[1]/div[2]/div[4]/div[1]/a");
    private final By logOut = By.id("gb_71");

    private final By switchDrive = By.xpath("//*[@id=\"ogbkddg:6\"]/a");

//    protected By switchGPlus = By.xpath("//*[@id=\"ogbkddg:0\"]/a");
//    protected By switchSearch = By.xpath("//*[@id=\"ogbkddg:1\"]/a");
//    protected By switchYouTube = By.xpath("//*[@id=\"ogbkddg:2\"]/a");
//    protected By switchMaps = By.xpath("//*[@id=\"ogbkddg:3\"]/a");
//    protected By switchPlay = By.xpath("//*[@id=\"ogbkddg:4\"]/a");
//    protected By switchGmail = By.xpath("//*[@id=\"ogbkddg:5\"]/a");
//    protected By switchCalendar = By.xpath("//*[@id=\"ogbkddg:7\"]/a");


    public GenericGoogleApp(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        this.driver = driver;
    }

    public LoginPage getLoginPage() {
        return new LoginPage();
    }

    public void selfTestTitle() {
        String theTitle = driver.getTitle();
        Assert.assertTrue(theTitle.contains(this.TITLE),
                String.format("Expecting page title: %s; Actual page title: %s", this.TITLE, theTitle));
    }

    public void selfTestUser(String name) {
        String firstname = driver.findElement(accountName).getText();
        Assert.assertEquals(firstname, name, String.format("Expecting account name: %s, Actual user name: %s", name, firstname));
    }


    public LoginPage doLogOut() {
        driver.findElement(settings).click();
        driver.findElement(logOut).click();
        return getLoginPage();

    }

    public GoogleDrive doOpenDrive() {
        driver.findElement(apps).click();
        driver.findElement(switchDrive).click();

        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        for (String tab : tabs) {
            driver.switchTo().window(tab);
            System.out.println(driver.getTitle());
        }
        driver.switchTo().window(tabs.get(1));

        WebDriverWait wait = new WebDriverWait(driver, 90, 30);
        wait.until(ExpectedConditions.titleContains("My Drive"));

        System.out.println(driver.getTitle());
        return new GoogleDrive(driver);
    }

    public class LoginPage {
        final By email = By.id("Email");
        final By passwd = By.id("Passwd");
        final By nextButton = By.id("next");
        final By signinButton = By.id("signIn");

        protected void enterLogin(String login) {
            driver.findElement(email).sendKeys(login);
        }

        protected void enterPassword(String pwd) {
            driver.findElement(passwd).sendKeys(pwd);
        }

        protected void pressNext() {
            driver.findElement(nextButton).click();
        }

        protected void doSubmit() {
            driver.findElement(signinButton).click();
        }

        public void selfTestTitle(){
            String theTitle = driver.getTitle();
            Assert.assertTrue(theTitle.contains(GenericGoogleApp.this.LOGIN_TITLE),
                    String.format("Expecting page title: %s; Actual page title: %s", GenericGoogleApp.this.LOGIN_TITLE, theTitle));
        }

        public GenericGoogleApp loginAs(String login, String password) {
            GenericGoogleApp.this.email = login;
            enterLogin(login);
            pressNext();
            enterPassword(password);
            doSubmit();
            WebDriverWait wait = new WebDriverWait(driver, 90, 30);
            wait.until(ExpectedConditions.titleContains(TITLE));
            return GenericGoogleApp.this;
        }

        private void clickNotifications(){
            driver.findElement(notifications).click();
        }
    }

}
