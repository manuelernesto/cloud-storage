package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class LoginPage {
    @FindBy(css = "#inputUsername")
    private WebElement usernameField;

    @FindBy(css = "#inputPassword")
    private WebElement passwordField;

    @FindBy(css = "#submit-button")
    private WebElement submitButton;

    private WebDriver driver;
    private final String url;

    public LoginPage(WebDriver driver, String url) {
        this.driver = driver;
        this.url = url;
        PageFactory.initElements(this.driver, this);
    }

    public void login() {
        var username = "gaara";
        var password = "madara";

        String loginText = "Login";
        String loginUrl = "/login";

        this.driver.get(this.url + loginUrl);

        Assertions.assertEquals(loginText, driver.getTitle());

        assertDoesNotThrow(() -> {
            this.usernameField.sendKeys(username);
            this.passwordField.sendKeys(password);
            this.submitButton.click();
        });

    }


}
