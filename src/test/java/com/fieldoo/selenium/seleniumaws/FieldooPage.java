package com.fieldoo.selenium.seleniumaws;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Dejan Veternik on 12/10/2019.
 */
public class FieldooPage extends PageObject{

    @FindBy(xpath = "//img[@src='https://www.fieldoo.com/resources/img/logo1.png']")
    private WebElement fieldooLogo;

    @FindBy(xpath = "//a[@href='/p/login']")
    private WebElement loginPageButton;

    public FieldooPage(WebDriver driver) {
        super(driver);
    }

    public boolean isInitialized() {
        return fieldooLogo.isDisplayed();
    }

    public LoginPage goToLoginPage() {
        loginPageButton.click();
        return new LoginPage(driver);
    }
}
