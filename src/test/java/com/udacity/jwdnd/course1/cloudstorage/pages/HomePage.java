package com.udacity.jwdnd.course1.cloudstorage.pages;

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

    private WebDriver webDriver;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    public void logout() {
        this.logoutButton.click();
    }

    public void openNote() {
        tabNote.click();
    }

    public void openCredential() {
        tabCredential.click();
    }

    public void backToHome() {
        assertEquals("Result", webDriver.getTitle());

        WebElement backToHomeBtn = this.webDriver.findElement(By.id("back-to-home-from-result"));

        backToHomeBtn.click();

        new WebDriverWait(this.webDriver, 1).until(ExpectedConditions.titleContains("Home"));

        assertEquals("Home", webDriver.getTitle());
        openNote();
    }

}
