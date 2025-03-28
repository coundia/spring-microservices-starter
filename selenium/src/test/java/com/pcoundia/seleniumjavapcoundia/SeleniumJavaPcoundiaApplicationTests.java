package com.pcoundia.seleniumjavapcoundia;

import com.pcoundia.seleniumjavapcoundia.pages.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Profile("test")
class SeleniumJavaPcoundiaApplicationTests extends BaseTest {

    @Value("${app.title}")
    private  String title;

    @Test
    void testPcoundiaHomePage() {
        homePage.navigateToHomePage();
        assertTrue(homePage.isTitleCorrect(title),  String.format("%s title is not present", title));
    }

}
