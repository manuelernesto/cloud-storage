package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.NotePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoteTest {

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

        new SignupPage(driver, baseURL).signup();
        new LoginPage(driver, baseURL).login();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    public void addNote() {
        HomePage homePage = new HomePage(driver);
        homePage.openNote();

        var title = "Note title";
        var description = "Note description";

        NotePage notePage = new NotePage(driver);
        notePage.add(title, description);

        homePage.backToHome("Result");

        assertNotNull(notePage.get(title, description));
    }


    @Test
    @Order(2)
    public void updateNote() {
        HomePage homePage = new HomePage(driver);
        homePage.openNote();

        var title = "Note title";
        var description = "Note description";

        String newTitle = "new title";
        String newDescription = "new description";

        NotePage notePage = new NotePage(driver);
        notePage.add(title, description);

        homePage.backToHome("Result");

        notePage.update(title, description, newTitle, newDescription);
        homePage.backToHome("Result");

        assertNotNull(notePage.get(newTitle, newDescription));
    }

    @Test
    @Order(2)
    public void deleteNote() {
        //homePage.openNote();

        String title = "Note title";
        String description = "Note description";

//        notePage.add(title, description);
//        homePage.backToHome();
//
//        notePage.delete(title, description);
//
//        assertNull(notePage.get(title, description));
    }

    private void insert() {

    }
}
