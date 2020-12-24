package com.udacity.jwdnd.course1.cloudstorage.page;

/**
 * Selenium Page Object for signup.html
 * -- define element selectors
 * -- initialising web elements
 * -- creating helper methods
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement firstNameInput;

    @FindBy(id = "inputLastName")
    private WebElement lastNameInput;

    @FindBy(id = "inputUsername")
    private WebElement usernameInput;

    @FindBy(id = "inputPassword")
    private WebElement passwordInput;

    @FindBy(id = "submitSignup")
    private WebElement submitSignup;

    public SignupPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signup (String firstname, String lastname, String username, String password) {
        this.firstNameInput.sendKeys(firstname);
        this.lastNameInput.sendKeys(lastname);
        this.usernameInput.sendKeys(username);
        this.passwordInput.sendKeys(password);
        this.submitSignup.click();
    }
}
