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
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.time.Duration;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTest {

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
    public void createNoteTest() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        //added note
        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        addNote(jse, wait, notesTab);

        //check for note
        getNewNote(jse, notesTab);
    }


    @Test
    public void updateNoteTest() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        String newNoteTitle = "new note title";
        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));

        //add note
        addNote(jse, wait, notesTab);

        WebElement notesTable = driver.findElement(By.id("userTable"));
        List<WebElement> notesList = notesTable.findElements(By.tagName("td"));

        //update note
        updateNote(jse, wait, notesTab, newNoteTitle, notesTable, notesList);

        //check the updated note
        getUpdatedNote(jse, notesTab, newNoteTitle, notesTable, notesList);
    }


    @Test
    public void deleteNoteTest() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        addNote(jse, wait, notesTab);

        notesTab = driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
        WebElement notesTable = driver.findElement(By.id("userTable"));
        List<WebElement> notesList = notesTable.findElements(By.tagName("td"));
        WebElement deleteElement = null;
        for (WebElement element : notesList) {
            deleteElement = element.findElement(By.name("delete"));
            if (deleteElement != null) {
                break;
            }
        }
        wait.until(ExpectedConditions.elementToBeClickable(deleteElement)).click();
        Assertions.assertEquals("Result", driver.getTitle());
    }

    /*Private methods*/

    private void addNote(JavascriptExecutor jse, WebDriverWait wait, WebElement notesTab) {
        jse.executeScript("arguments[0].click()", notesTab);
        wait.withTimeout(Duration.ofSeconds(30));
        WebElement newNote = driver.findElement(By.id("new-note-btn"));
        wait.until(ExpectedConditions.elementToBeClickable(newNote)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys("note Title");
        WebElement description = driver.findElement(By.id("note-description"));
        description.sendKeys("note Description");
        WebElement save = driver.findElement(By.id("noteSubmit"));
        save.submit();
        Assertions.assertEquals("Result", driver.getTitle());

        WebElement backHome = driver.findElement(By.id("back-to-home-from-result"));
        backHome.click();
    }

    private void getNewNote(JavascriptExecutor jse, WebElement notesTab) {
        notesTab = driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
        WebElement notesTable = driver.findElement(By.id("userTable"));
        List<WebElement> notesList = notesTable.findElements(By.tagName("th"));
        boolean created = false;
        for (WebElement element : notesList) {
            if (element.getAttribute("innerHTML").equals("note Title")) {
                created = true;
                break;
            }
        }
        Assertions.assertTrue(created);
    }

    private void getUpdatedNote(JavascriptExecutor jse, WebElement notesTab, String newNoteTitle, WebElement notesTable, List<WebElement> notesList) {
        notesTab = driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
        notesTable = driver.findElement(By.id("userTable"));
        notesList = notesTable.findElements(By.tagName("th"));
        boolean edited = false;
        for (WebElement element : notesList) {
            if (element.getAttribute("innerHTML").equals(newNoteTitle)) {
                edited = true;
                break;
            }
        }
        Assertions.assertTrue(edited);
    }

    private void updateNote(JavascriptExecutor jse, WebDriverWait wait, WebElement notesTab, String newNoteTitle, WebElement notesTable, List<WebElement> notesList) {
        notesTab = driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
        notesTable = driver.findElement(By.id("userTable"));
        notesList = notesTable.findElements(By.tagName("td"));
        WebElement editElement = null;
        for (WebElement element : notesList) {
            editElement = element.findElement(By.name("edit"));
            if (editElement != null) {
                break;
            }
        }
        wait.until(ExpectedConditions.elementToBeClickable(editElement)).click();
        WebElement notetitle = driver.findElement(By.id("note-title"));
        wait.until(ExpectedConditions.elementToBeClickable(notetitle));
        notetitle.clear();
        notetitle.sendKeys(newNoteTitle);
        WebElement save = driver.findElement(By.id("noteSubmit"));
        save.submit();
        Assertions.assertEquals("Result", driver.getTitle());
        WebElement backHome = driver.findElement(By.id("back-to-home-from-result"));
        backHome.click();
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
