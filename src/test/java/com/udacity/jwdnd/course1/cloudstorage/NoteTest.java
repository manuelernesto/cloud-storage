package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.NotePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoteTest {

    @LocalServerPort
    private int port;

    private static WebDriver driver;

    private String baseURL;

    private static HomePage homePage;
    private static NotePage notePage;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        homePage = new HomePage(driver);
        notePage = new NotePage(driver);
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
        driver = null;
    }

    @BeforeEach
    public void beforeEach() {
        baseURL = baseURL = "http://localhost:" + port;
    }

    @Test
    @Order(1)
    public void loginAndSignUp() {
        var username = "gaara";
        var password = "madara";

        driver.get(baseURL + "/signup");

        var signupPage = new SignupPage(driver);
        signupPage.signup("Manuel", "Ernesto", username, password);

        driver.get(baseURL + "/login");

        var loginPage = new LoginPage(driver);
        loginPage.login(username, password);
    }

    @Test
    @Order(2)
    public void addNote() {

        homePage.openNote();

        var title = "Note title";
        var description = "Note description";
        notePage.add(title, description);

        homePage.backToHome();

        assertNotNull(notePage.get(title, description));
    }

//    @Test
//    @Order(3)
//    public void updateNote() {
//
//    }
//
//    @Test
//    @Order(4)
//    public void deleteNote() {
//
//    }
}
