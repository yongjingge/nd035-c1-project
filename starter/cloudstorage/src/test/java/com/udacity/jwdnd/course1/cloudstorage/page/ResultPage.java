package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultPage {

    @FindBy(id = "goto-home")
    private WebElement returnHome;
    private WebDriverWait wait;

    public ResultPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 100);
    }

    public void successBack () {
        wait.until(ExpectedConditions.elementToBeClickable(returnHome)).click();
    }
}
