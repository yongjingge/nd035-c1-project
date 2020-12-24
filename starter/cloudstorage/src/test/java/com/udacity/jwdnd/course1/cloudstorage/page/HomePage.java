package com.udacity.jwdnd.course1.cloudstorage.page;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Selenium Page Object for signup.html
 * -- define element selectors
 * -- initialising web elements
 * -- creating helper methods
 */

public class HomePage {

    // NAVIGATION -- FILES, NOTES, CREDENTIALS
    @FindBy(id = "nav-files-tab")
    private WebElement fileNav;

    @FindBy(id = "nav-notes-tab")
    private WebElement noteNav;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialNav;

    @FindBy(id = "logout-button")
    private WebElement logout;

    // NOTE RELEVANT
    @FindBy(id = "add-note-button")
    private WebElement addNote;

    @FindBy(id = "note-title")
    private WebElement notetitleInput;

    @FindBy(id = "note-description")
    private WebElement notedescriptionInput;

    @FindBy(id="save-button-note")
    private WebElement saveNoteButton;

    @FindBy(id = "noteTable")
    private WebElement noteTable;

    // CREDENTIAL RELEVANT
    @FindBy(id = "add-credential-button")
    private WebElement addCredential;

    @FindBy(id = "credentialTable")
    private WebElement credentialTable;

    @FindBy(id = "credential-url")
    private WebElement credentialurlInput;

    @FindBy(id = "credential-username")
    private WebElement credentialusernameInput;

    @FindBy(id = "credential-password")
    private WebElement credentialpasswordInput;

    @FindBy(id = "save-button-credential")
    private WebElement saveCredentialButton;

    @FindBy(id = "close-credential-button")
    private WebElement closeCredentialButton;







    // WAIT
    private WebDriverWait wait;

    public HomePage (WebDriver driver) {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 100);
    }

    public void logout () {
        logout.click();
    }



    // NOTE METHODS
    public void addNote (String notetitle, String notedescription) throws InterruptedException {
        noteNav.click();
        addNote.click();
        Thread.sleep(3000);

        notetitleInput.sendKeys(notetitle);
        Thread.sleep(1000);
        notedescriptionInput.sendKeys(notedescription);
        Thread.sleep(1000);

        saveNoteButton.click();
    }

    public void deleteNote (String notetitle) throws InterruptedException {
        noteNav.click();
        wait.until(ExpectedConditions.visibilityOf(noteTable));
        WebElement deleteLink = noteTable.findElement(By.id("delete" + notetitle));
        wait.until(ExpectedConditions.visibilityOf(deleteLink));
        deleteLink.click();
    }

    public boolean checkIfNoteExists (String notetitle) {
        noteNav.click();
        wait.until(ExpectedConditions.visibilityOf(noteTable));

        try {
            return noteTable.findElement(By.id(notetitle)) != null;
        } catch (org.openqa.selenium.NoSuchElementException err) {
            return false;
        }
    }

    public void editNote (String oldnotetitle, String newnotetitle, String newnotedescription) throws InterruptedException {

        wait.until(ExpectedConditions.visibilityOf(noteNav));
        noteNav.click();
        wait.until(ExpectedConditions.visibilityOf(noteTable));
        WebElement editButton = noteTable.findElement(By.id("edit" + oldnotetitle));
        wait.until(ExpectedConditions.visibilityOf(editButton));

        editButton.click();
        Thread.sleep(3000);

        notetitleInput.clear();
        notetitleInput.sendKeys(newnotetitle);
        Thread.sleep(1000);
        notedescriptionInput.clear();
        notedescriptionInput.sendKeys(newnotedescription);
        Thread.sleep(1000);

        saveNoteButton.click();
    }


    // CREDENTIAL METHODS
    public void addCredential (String credentialurl, String credentialusername, String credentialpassword) throws InterruptedException {
        credentialNav.click();
        addCredential.click();
        Thread.sleep(3000);

        credentialurlInput.sendKeys(credentialurl);
        Thread.sleep(1000);
        credentialusernameInput.sendKeys(credentialusername);
        Thread.sleep(1000);
        credentialpasswordInput.sendKeys(credentialpassword);
        Thread.sleep(1000);

        saveCredentialButton.click();
    }

    public boolean checkIfCredentialExists (String url) throws InterruptedException {
        credentialNav.click();
        wait.until(ExpectedConditions.visibilityOf(credentialTable));

        try {
            if (credentialTable.findElement(By.id(url)) != null) {
                return true;
            }
        } catch (org.openqa.selenium.NoSuchElementException err) {
            return false;
        }
        return false;
    }

    public boolean checkIfPasswordIsNotEncrypted (String password) {
        credentialNav.click();
        wait.until(ExpectedConditions.visibilityOf(credentialTable));

        try {
            return credentialTable.findElement(By.id(password)) != null;
            // above should be false if the password is actually encrypted
        } catch (org.openqa.selenium.NoSuchElementException err) {
            return false;
        }
    }

    public void editCredential (String oldurl, String newurl, String newusername, String newpassword) throws InterruptedException {

        wait.until(ExpectedConditions.visibilityOf(credentialNav));
        credentialNav.click();

        WebElement editButton = credentialTable.findElement(By.id("edit" + oldurl));
        wait.until(ExpectedConditions.visibilityOf(editButton));

        Thread.sleep(3000);
        editButton.click();

        Thread.sleep(3000);

        credentialurlInput.clear();
        credentialurlInput.sendKeys(newurl);
        Thread.sleep(1000);
        credentialusernameInput.clear();
        credentialusernameInput.sendKeys(newusername);
        Thread.sleep(1000);
        credentialpasswordInput.clear();
        credentialpasswordInput.sendKeys(newpassword);
        Thread.sleep(1000);

        saveCredentialButton.click();

    }

    // cannot get text from input, will be '' empty
    public String passwordViewDuringEditing (String oldurl) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(credentialNav));
        credentialNav.click();

        WebElement editButton = credentialTable.findElement(By.id("edit" + oldurl));
        wait.until(ExpectedConditions.visibilityOf(editButton));

        editButton.click();
        Thread.sleep(3000);

        String passwordView = credentialpasswordInput.getText();
        return passwordView;
    }

    public void deleteCredential (String url) throws InterruptedException {
        credentialNav.click();
        wait.until(ExpectedConditions.visibilityOf(credentialTable));
        WebElement deleteLink = credentialTable.findElement(By.id("delete" + url));
        wait.until(ExpectedConditions.visibilityOf(deleteLink));
        deleteLink.click();
    }
}
