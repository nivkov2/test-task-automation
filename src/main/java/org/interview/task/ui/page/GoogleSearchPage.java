package org.interview.task.ui.page;

import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;

import static org.interview.task.config.ConfigurationManager.config;

@Slf4j
public class GoogleSearchPage {
    private final Page page;
    private final String searchBox = "textarea[name='q']";
    private final String searchButton = "//input[@name='btnK']";
    private final String acceptCookieButton = "//button//*[contains(text(), 'Accept all')]";
    private final String firstHeader = "(//h3)[0]";

    public GoogleSearchPage(Page page) {
        this.page = page;
    }

    public void navigateToGoogle() {
        log.info("Navigating to {} url", config().baseSearchUrl());
        page.navigate(config().baseSearchUrl());
    }

    public void acceptCookie() {
        page.locator(acceptCookieButton).click();
    }

    public void enterSearchText(String valueToSearch) {
        page.locator(searchBox).fill(valueToSearch);
    }

    public void submitSearch() {
        page.press(searchBox, "Enter");
    }

    public void printHeaderValue() {
        System.out.println(page.locator(firstHeader).textContent());
    }
}
