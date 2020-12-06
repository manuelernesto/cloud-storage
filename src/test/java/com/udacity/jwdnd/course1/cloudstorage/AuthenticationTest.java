package com.udacity.jwdnd.course1.cloudstorage;


import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait driverWait;
    private String baseURL;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        this.baseURL = "http://localhost:" + this.port;
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }


    @Test
    @Order(1)
    public void homeNotAccessibleTest() {
        driver.get(baseURL + "/home");
        assertEquals("Login", driver.getTitle());
    }

    @Test
    @Order(2)
    public void userLoginInvalidCredentialsTest() {

        new LoginPage(driver, baseURL).login();

        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    @Order(3)
    public void userSignupLoginTest() {

        var signupPage = new SignupPage(driver, baseURL);
        signupPage.signup();
        signupPage.backToLogin();

        new LoginPage(driver, baseURL).login();

        Assertions.assertEquals("Home", driver.getTitle());

        new HomePage(driver).logout();

    }


}
