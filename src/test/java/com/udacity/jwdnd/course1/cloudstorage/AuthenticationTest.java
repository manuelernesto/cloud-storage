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
    public int port;

    public static WebDriver driver;

    public String baseURL;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
        driver = null;
    }


    @BeforeEach
    public void beforeEach() {
        baseURL = "http://localhost:" + port;
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
        var username = "gaara";
        var password = "madara";

        driver.get(baseURL + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        assertEquals("Login", loginPage.submitButton.getText());
    }

    @Test
    @Order(3)
    public void userSignupLoginTest() {
        var username = "gaara";
        var password = "madara";


        driver.get(baseURL + "/signup");

        var signupPage = new SignupPage(driver);
        signupPage.signup("Manuel", "Ernesto", username, password);

        driver.get(baseURL + "/login");

        var loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        assertEquals("Home", driver.getTitle());
    }

    @Test
    @Order(4)
    public void userLogoutTest() {
        new WebDriverWait(driver, 5);
        var homePage = new HomePage(driver);
        homePage.logout();
        assertEquals("Login", driver.getTitle());
    }

}
