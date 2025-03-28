package com.pcoundia.seleniumjavapcoundia.pages;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;

public class HomePage {

    private final WebDriver driver;
    private final String url;

    public HomePage(WebDriver driver, String url) {
        this.driver = driver;
        this.url = url;
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void navigateToHomePage() {
        driver.get(this.url);
    }

    public boolean isTitleCorrect(String title) {
        return getPageTitle().contains(title);
    }
}
