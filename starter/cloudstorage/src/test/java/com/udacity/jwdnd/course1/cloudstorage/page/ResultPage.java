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

public class ResultPage {

    @FindBy(id = "goto-home")
    private WebElement returnHome;

    public ResultPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void successBack () {
        returnHome.click();
    }
}
