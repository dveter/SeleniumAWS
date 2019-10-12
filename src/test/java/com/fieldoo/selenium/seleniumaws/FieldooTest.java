package com.fieldoo.selenium.seleniumaws;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by Dejan Veternik on 12/10/2019.
 */
public class FieldooTest {
    private static final String FIELDOO_PAGE_URL = "http://www.fieldoo.com";
    private static WebDriver driver;

    private static FieldooPage fieldooPage;
    private static LoginPage loginPage;

    @BeforeClass
    public static void setUp(){
        System.setProperty("webdriver.chrome.driver", "/opt/selenium/chromedriver");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(FIELDOO_PAGE_URL);
    }

    @Test
    public void isSiteAvailable() {
        fieldooPage = new FieldooPage(driver);
        assertTrue(fieldooPage.isInitialized());
    }

    @Test
    public void isLoginPageAccessible() {
        loginPage = fieldooPage.goToLoginPage();
        assertTrue(loginPage.isInitialized());
    }

    @Test
    public void someFailingTest() {
        loginPage.login("", "");
        assertTrue("Empty password message is mistyped or missing.",
                loginPage.getErrorMessages().contains("Password cannot be empty."));
    }

    @AfterClass
    public static void tearDown(){
        driver.close();
    }
}
