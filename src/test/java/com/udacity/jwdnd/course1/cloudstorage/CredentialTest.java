package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.Duration;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTest {
    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        signup();
        login();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            this.driver.quit();
        }

    }

    @Test
    public void createCredentialTest() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));
        //add credential
        addCredential(jse, wait, credentialTab);

        //check for credential
        getNewCredential(jse, credentialTab);
    }

    @Test
    public void updateCredentialTest() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        String newCredUsername = "newUser";
        WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));

        //add credential
        addCredential(jse, wait, credentialTab);

        //update credential
        credentialTab = driver.findElement(By.id("nav-credentials-tab"));
        jse.executeScript("arguments[0].click()", credentialTab);
        WebElement credsTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> credsList = credsTable.findElements(By.tagName("td"));
        WebElement editElement = null;
        for (WebElement element : credsList) {
            editElement = element.findElement(By.name("editCred"));
            if (editElement != null) {
                break;
            }
        }
        wait.until(ExpectedConditions.elementToBeClickable(editElement)).click();
        WebElement credUsername = driver.findElement(By.id("credential-username"));
        wait.until(ExpectedConditions.elementToBeClickable(credUsername));
        credUsername.clear();
        credUsername.sendKeys(newCredUsername);
        WebElement savechanges = driver.findElement(By.id("save-credential"));
        savechanges.click();
        Assertions.assertEquals("Result", driver.getTitle());

        WebElement backHome = driver.findElement(By.id("back-to-home-from-result"));
        backHome.click();

        //check the updated note
        credentialTab = driver.findElement(By.id("nav-credentials-tab"));
        jse.executeScript("arguments[0].click()", credentialTab);
        credsTable = driver.findElement(By.id("credentialTable"));
        credsList = credsTable.findElements(By.tagName("td"));
        boolean edited = false;
        for (WebElement element : credsList) {
            if (element.getAttribute("innerHTML").equals(newCredUsername)) {
                edited = true;
                break;
            }
        }
        Assertions.assertTrue(edited);
    }

    @Test
    public void credentialDeletionTest() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));
        addCredential(jse,wait,credentialTab);
        credentialTab = driver.findElement(By.id("nav-credentials-tab"));
        jse.executeScript("arguments[0].click()", credentialTab);
        WebElement credsTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> credsList = credsTable.findElements(By.tagName("td"));
        WebElement deleteElement = null;
        for (WebElement element : credsList) {
            deleteElement = element.findElement(By.name("delete"));
            if (deleteElement != null) {
                break;
            }
        }
        wait.until(ExpectedConditions.elementToBeClickable(deleteElement)).click();
        Assertions.assertEquals("Result", driver.getTitle());
    }


    /*Private methods*/

    private void addCredential(JavascriptExecutor jse, WebDriverWait wait, WebElement credentialTab) {
        jse.executeScript("arguments[0].click()", credentialTab);
        wait.withTimeout(Duration.ofSeconds(30));
        WebElement newCred = driver.findElement(By.id("new-credential-btn"));
        wait.until(ExpectedConditions.elementToBeClickable(newCred)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys("http://udacity.com");
        WebElement credUsername = driver.findElement(By.id("credential-username"));
        credUsername.sendKeys("manuelernest0");
        WebElement credPassword = driver.findElement(By.id("credential-password"));
        credPassword.sendKeys("password");
        WebElement submit = driver.findElement(By.id("save-credential"));
        submit.click();
        Assertions.assertEquals("Result", driver.getTitle());

        WebElement backHome = driver.findElement(By.id("back-to-home-from-result"));
        backHome.click();
    }

    private void getNewCredential(JavascriptExecutor jse, WebElement credentialTab) {
        credentialTab = driver.findElement(By.id("nav-credentials-tab"));
        jse.executeScript("arguments[0].click()", credentialTab);
        WebElement credsTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> credsList = credsTable.findElements(By.tagName("td"));
        boolean created = false;
        for (WebElement element : credsList) {
            if (element.getAttribute("innerHTML").equals("manuelernest0")) {
                created = true;
                break;
            }
        }
        Assertions.assertTrue(created);
    }

    private void login() {
        driver.get("http://localhost:" + this.port + "/login");
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.sendKeys("manuelernest0");
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.sendKeys("password");
        WebElement loginButton = driver.findElement(By.id("submit-button"));
        loginButton.click();
        Assertions.assertEquals("Home", driver.getTitle());
    }

    private void signup() {
        driver.get("http://localhost:" + this.port + "/signup");
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.sendKeys("Manuel");
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.sendKeys("Ernesto");
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.sendKeys("manuelernest0");
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.sendKeys("password");
        WebElement signUpButton = driver.findElement(By.id("submit-button"));
        signUpButton.click();
    }
}
