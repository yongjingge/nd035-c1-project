package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Selenium Page Object for signup.html
 * -- define element selectors
 * -- initialising web elements
 * -- creating helper methods
 */

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement usernameInput;

    @FindBy(id = "inputPassword")
    private WebElement passwordInput;

    @FindBy(id = "submitLogin")
    private WebElement submitLogin;

    @FindBy(id = "loginError")
    private WebElement loginError;

    @FindBy(id = "logout")
    private WebElement logout;

    public LoginPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void login (String username, String password) {
        this.usernameInput.sendKeys(username);
        this.passwordInput.sendKeys(password);
        this.submitLogin.click();
    }

}
