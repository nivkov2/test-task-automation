package tests.ui;

import com.microsoft.playwright.*;
import org.interview.task.ui.page.GoogleSearchPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Execution(ExecutionMode.CONCURRENT)
public class SearchTest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    private GoogleSearchPage googleSearchPage;

    @BeforeEach
    void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        page.evaluate("() => { navigator.webdriver = false; }");
        googleSearchPage = new GoogleSearchPage(page);
    }


    @AfterEach
    void tearDown() {
        context.close();
        browser.close();
        playwright.close();
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    void testMethod(String valueToSearch) {
        googleSearchPage.navigateToGoogle();
        googleSearchPage.acceptCookie();
        googleSearchPage.enterSearchText(valueToSearch);
        googleSearchPage.submitSearch();

        //TODO:
        // here I run into bot detection captcha mechanism form google
        // so it will take additional efforts to overcome this

//        googleSearchPage.printHeaderValue();
    }

    private static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of("wyden"),
                Arguments.of("bitcoin"),
                Arguments.of("ethereum")
        );
    }
}
