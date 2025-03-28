package com.pcoundia.seleniumjavapcoundia.pages;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;

public class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;

    @Value("${app.url}")
    private String url;

    @Value("${app.selenium.url}")
    private String seleniumUrl;

    @BeforeEach
    void setup() throws Exception {
        driver = WebDriverFactory.createWebDriver(seleniumUrl);
        System.out.println("Driver created");
        System.out.println("url: " + url);
        homePage = new HomePage(driver,url);
    }

    @AfterEach
    void teardown() {
        WebDriverFactory.closeWebDriver(driver);
    }
}
