package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement usernameInput;

    @FindBy(id = "inputPassword")
    private WebElement passwordInput;

    @FindBy(xpath = "/html/body/div/form/button")
    private WebElement submitLogin;

    @FindBy(id = "loginError")
    private WebElement loginError;

    @FindBy(id = "logout")
    private WebElement logout;

    private WebDriverWait wait;

    public LoginPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 100);
    }

    public void login (String username, String password) {
        this.usernameInput.sendKeys(username);
        this.passwordInput.sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(submitLogin)).click();
    }

}
