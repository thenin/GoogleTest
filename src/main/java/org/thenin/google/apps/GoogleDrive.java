package org.thenin.google.apps;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kguryanov on 6/18/2015.
 * PageObject class for Google Drive application
 *
 */
public class GoogleDrive extends GenericGoogleApp {
    final By folderList = By.xpath("//*[@id=\":1j.folder0\"]/div/div[2]/div/div");

    public GoogleDrive(WebDriver driver) {
        super(driver);
        this.TITLE = "My Drive - Google Drive";
        this.LOGIN_TITLE = "Meet Google Drive � One place for all your files";
        driver.get("https://drive.google.com");
    }

    private void openNewFolder() {
        String selectAll = Keys.chord(Keys.SHIFT, "f");
        Actions action = new Actions(driver);
        action.sendKeys(selectAll).perform();
    }

    private void typeFolderName(String name) {
        String selectAll = Keys.chord(name);
        Actions action = new Actions(driver);
        action.sendKeys(selectAll).perform();
    }

    private void confirmFolderCreation() {
        String selectAll = Keys.chord(Keys.ENTER);
        Actions action = new Actions(driver);
        action.sendKeys(selectAll).perform();
    }

    public void deleteFolders(List<WebElement> folderList) {
        String selectAll = Keys.chord(Keys.DELETE);
        Actions action = new Actions(driver);

        for (WebElement folder: folderList){
            folder.click();
            action.sendKeys(selectAll).perform();
        }
    }


    public List<WebElement> createFolder(String name) {
        List<WebElement> oldList = driver.findElements(folderList);
        int folderNumber = oldList.size();
        int timeout = 0;

        openNewFolder();
        typeFolderName(name);
        confirmFolderCreation();

        List<WebElement> newList = driver.findElements(folderList);

        while (!(newList.size() > folderNumber) && timeout < 10000) {
            try {
                Thread.sleep(10);
                newList = driver.findElements(folderList);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timeout += 10;
        }
        List<WebElement> finalList = new ArrayList<WebElement>(newList);


        for (WebElement oldItem : oldList) {
            for (WebElement newItem : newList) {
                if (oldItem.getAttribute("data-id").equals(newItem.getAttribute("data-id"))) {
                    finalList.remove(newItem);
                }
            }
        }

        return  finalList;
    }

}
