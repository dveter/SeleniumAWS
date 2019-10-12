package com.fieldoo.selenium.seleniumaws;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Dejan Veternik on 12/10/2019.
 */
public class LoginPage extends PageObject {

    @FindBy(id = "newLoginForm")
    private WebElement loginForm;

    @FindBy(id = "usernameFocus")
    private WebElement usernameTextbox;

    @FindBy(id = "j_password")
    private WebElement passwordTextbox;

    @FindBy(id = "sendButton")
    private WebElement loginButton;

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
}
