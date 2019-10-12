package com.fieldoo.selenium.seleniumaws;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dejan Veternik on 12/10/2019.
 */
public class LoginPage extends PageObject {

    @FindBy(id = "newLoginForm")
    private WebElement loginForm;

    @FindBy(id = "usernameFocus")
    private WebElement usernameTextbox;

    @FindBy(name = "j_password")
    private WebElement passwordTextbox;

    @FindBy(id = "sendButton")
    private WebElement loginButton;

    @FindBy(xpath = "//span[@style='color: red;']")
    private List<WebElement> errorMsgs;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isInitialized() {
        return loginForm.isDisplayed();
    }

    public void login(String username, String password) {
        usernameTextbox.sendKeys(username);
        passwordTextbox.sendKeys(password);
        loginButton.click();
    }

    public List<String> getErrorMessages() {
        return errorMsgs.stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
