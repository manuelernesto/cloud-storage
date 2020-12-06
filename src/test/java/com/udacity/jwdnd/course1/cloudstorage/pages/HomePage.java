package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomePage {

    @FindBy(id = "logout-btn")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement tabNote;

    @FindBy(id = "nav-credentials-tab")
    private WebElement tabCredential;

    @FindBy(id = "back-to-home-from-result")
    private WebElement backToHomeBtn;

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void logout() {
        String loginText = "Login";
        this.logoutButton.click();
        new WebDriverWait(this.driver, 1).until(ExpectedConditions.titleContains(loginText));
        Assertions.assertEquals(loginText, driver.getTitle());
    }

    public void openNote() {
        tabNote.click();
    }

    public void openCredential() {
        tabCredential.click();
    }

    public void backToHome(String expected) {
        String homeText = "Home";

        assertEquals(expected, driver.getTitle());

        backToHomeBtn.click();

        new WebDriverWait(this.driver, 1).until(ExpectedConditions.titleContains(homeText));

        assertEquals(homeText, driver.getTitle());
        openNote();
    }

}
