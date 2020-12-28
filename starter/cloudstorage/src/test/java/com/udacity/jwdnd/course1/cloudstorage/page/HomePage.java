package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    // NAVIGATION -- FILES, NOTES, CREDENTIALS
    @FindBy(xpath = "/html/body/div/div[2]/nav/div/a[1]")
    private WebElement fileNav;

    @FindBy(xpath = "/html/body/div/div[2]/nav/div/a[2]")
    private WebElement noteNav;

    @FindBy(xpath = "/html/body/div/div[2]/nav/div/a[3]")
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

    @FindBy(xpath = "/html/body/div/div[2]/div/div[2]/div[1]/table")
    private WebElement noteTable;

    // CREDENTIAL RELEVANT
    @FindBy(id = "add-credential-button")
    private WebElement addCredential;

    @FindBy(xpath = "/html/body/div/div[2]/div/div[3]/div[1]/table")
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


    /**
     * HELPER METHODS DEALING WITH 'ELEMENT NOT VISIBLE' ISSUE
     * Happened after we return back to the page, we lost the same Web Element which we have found last time.
     * More importantly, add Thread.sleep after we returned the target page so that time will be enough for target elements to be located.
     * @return target Web Element
     */
    public WebElement getNoteNav () {
        wait.until(ExpectedConditions.elementToBeClickable(noteNav));
        return noteNav;
    }
    public WebElement getFileNav () {
        wait.until(ExpectedConditions.elementToBeClickable(fileNav));
        return fileNav;
    }
    public WebElement getCredentialNav () {
        wait.until(ExpectedConditions.elementToBeClickable(credentialNav));
        return credentialNav;
    }
    public WebElement getNoteTable () {
        wait.until(ExpectedConditions.visibilityOf(noteTable));
        return noteTable;
    }
    public WebElement getCredentialTable () {
        wait.until(ExpectedConditions.visibilityOf(credentialTable));
        return credentialTable;
    }


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
        getNoteNav().click();
        getNoteTable();

        WebElement deleteLink = noteTable.findElement(By.id("delete" + notetitle));
        wait.until(ExpectedConditions.elementToBeClickable(deleteLink));

        deleteLink.click();
    }

    public boolean checkIfNoteExists (String notetitle) throws InterruptedException {
        getNoteNav().click();
        getNoteTable();

        try {
            return noteTable.findElement(By.id(notetitle)) != null;
        } catch (org.openqa.selenium.NoSuchElementException err) {
            return false;
        }
    }

    public void editNote (String oldnotetitle, String newnotetitle, String newnotedescription) throws InterruptedException {
        getNoteNav().click();
        getNoteTable();

        WebElement editButton = noteTable.findElement(By.id("edit" + oldnotetitle));
        wait.until(ExpectedConditions.elementToBeClickable(editButton));

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
        getCredentialNav().click();

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
        getCredentialNav().click();
        getCredentialTable();

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
        getCredentialNav().click();
        getCredentialTable();

        try {
            return credentialTable.findElement(By.id(password)) != null;
            // above should be false if the password is actually encrypted
        } catch (org.openqa.selenium.NoSuchElementException err) {
            return false;
        }
    }

    public void editCredential (String oldurl, String newurl, String newusername, String newpassword) throws InterruptedException {
        getCredentialNav().click();
        getCredentialTable();

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
        wait.until(ExpectedConditions.elementToBeClickable(getCredentialNav())).click();

        WebElement editButton = credentialTable.findElement(By.id("edit" + oldurl));
        wait.until(ExpectedConditions.visibilityOf(editButton));

        editButton.click();
        Thread.sleep(3000);

        String passwordView = credentialpasswordInput.getText();
        return passwordView;
    }

    public void deleteCredential (String url) throws InterruptedException {
        getCredentialNav().click();
        getCredentialTable();

        WebElement deleteLink = credentialTable.findElement(By.id("delete" + url));
        wait.until(ExpectedConditions.visibilityOf(deleteLink));
        deleteLink.click();
    }
}
