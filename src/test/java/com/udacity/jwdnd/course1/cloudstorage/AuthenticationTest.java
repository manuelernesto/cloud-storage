package com.udacity.jwdnd.course1.cloudstorage;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationTest {

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
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPageTest() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void getSignupPageTest() {
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }

    @Test
    public void homeNotAccessibleWithoutLoginTest() {
        this.driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void newUserAccessTest() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        // signup
        signup();

        //login
        login();

        //logout
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-btn")));
        WebElement logoutButton = driver.findElement(By.id("logout-btn"));
        logoutButton.submit();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup-link")));
        Assertions.assertEquals("Login", driver.getTitle());

        //Try to access homepage
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
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
