package com.fieldoo.selenium.seleniumaws;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by Dejan Veternik on 12/10/2019.
 */
public class FieldooTest {
    private static final boolean IS_REMOTE = Boolean.parseBoolean(System.getProperty("REMOTE"));
    private static final String SELENIUM_SERVER_HUB_URL = "http://" + System.getProperty("HOST") + ":5555/wd/hub";
    private static final String PATH_TO_CHROMEDRIVER = "/opt/selenium/chromedriver";
    private static final String FIELDOO_PAGE_URL = "http://www.fieldoo.com";

    private static WebDriver driver;

    private static FieldooPage fieldooPage;
    private static LoginPage loginPage;

    @BeforeClass
    public static void setUp() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        if (IS_REMOTE) {
            DesiredCapabilities desiredCaps = DesiredCapabilities.chrome();
            desiredCaps.setCapability(ChromeOptions.CAPABILITY, options);
            driver = new RemoteWebDriver(new URL(SELENIUM_SERVER_HUB_URL), desiredCaps);
        } else {
            System.setProperty("webdriver.chrome.driver", PATH_TO_CHROMEDRIVER);
            driver = new ChromeDriver(options);
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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
