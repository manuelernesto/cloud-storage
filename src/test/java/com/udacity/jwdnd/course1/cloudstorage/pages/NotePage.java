package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NotePage {

    @FindBy(id = "new-note-btn")
    private WebElement newNoteBtn;

    @FindBy(className = "note-row")
    private List<WebElement> notes;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "note-save")
    private WebElement saveBtn;

    private WebDriver webDriver;

    public NotePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }


    public void add(String title, String description) {

        new WebDriverWait(this.webDriver, 2).until(ExpectedConditions.elementToBeClickable(newNoteBtn)).click();

        new WebDriverWait(this.webDriver, 2).until(ExpectedConditions.elementToBeClickable(saveBtn));

        this.noteTitle.sendKeys(title);
        this.noteDescription.sendKeys(description);
        this.saveBtn.click();
    }

    public boolean update(String oldTitle, String oldDescription, String newTitle, String newDescription) {

        WebElement noteRow = this.get(oldTitle, oldDescription);

        if (noteRow == null)
            return false;


        noteRow.findElement(By.className("note-edit")).click();

        new WebDriverWait(this.webDriver, 500).until(ExpectedConditions.elementToBeClickable(saveBtn));

        noteTitle.clear();
        noteDescription.clear();

        noteTitle.sendKeys(newTitle);
        noteDescription.sendKeys(newDescription);
        saveBtn.click();

        return true;
    }

    public boolean delete(String title, String description) {
        WebElement noteRow = this.get(title, description);
        if (noteRow == null) return false;

        noteRow.findElement(By.className("note-delete")).click();

        try {
            WebDriverWait wait = new WebDriverWait(this.webDriver, 1);
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());

            alert.accept();
        } catch (Throwable e) {
            System.err.println("Error came while waiting for the alert popup. " + e.getMessage());
            return false;
        }

        return true;
    }


    public WebElement get(String noteTitle, String noteDescription) {

        for (WebElement note : notes) {
            var title = note.findElement(By.className("note-title")).getAttribute("innerHTML");
            var description = note.findElement(By.className("note-description")).getAttribute("innerHTML");

            if (title.equals(noteTitle) && description.equals(noteDescription))
                return note;
        }

        return null;
    }
}
