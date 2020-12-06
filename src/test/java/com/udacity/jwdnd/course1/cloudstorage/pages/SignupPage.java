package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignupPage {
    @FindBy(css = "#inputFirstName")
    private WebElement firstNameField;

    @FindBy(css = "#inputLastName")
    private WebElement lastNameField;

    @FindBy(css = "#inputUsername")
    private WebElement usernameField;

    @FindBy(css = "#inputPassword")
    private WebElement passwordField;

    @FindBy(css = "#submit-button")
    private WebElement submitButton;

    @FindBy(id = "login-link")
    private WebElement toLoginButton;

    private final WebDriver webDriver;
    private WebDriverWait driverWait;
    private final String url;

    public SignupPage(WebDriver webDriver, String url) {
        this.webDriver = webDriver;
        this.url = url;
        PageFactory.initElements(this.webDriver, this);
        this.driverWait = new WebDriverWait(webDriver, 5);
    }

    public void signup() {
        var username = "gaara";
        var password = "madara";
        var firstName = "Manuel";
        var lastName = "Ernesto";

        String signupUrl = "/signup";
        String signupText = "Sign Up";

        this.webDriver.get(this.url + signupUrl);

        assertEquals(signupText, webDriver.getTitle());

        this.firstNameField.sendKeys(firstName);
        this.lastNameField.sendKeys(lastName);
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.submitButton.click();


    }

    public void backToLogin(){
        assertEquals("login", toLoginButton.getText());
        toLoginButton.click();
        //this.driverWait.until(ExpectedConditions.titleContains("Login"));
    }

}
