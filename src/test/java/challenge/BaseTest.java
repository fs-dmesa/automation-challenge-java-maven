package challenge;

import challenge.support.BrowserDefaults;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

/**
 * Shared browser across the class, fresh context and page per test.
 * Pass -Dheadless=false to watch the browser while iterating locally.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    private Playwright playwright;
    private Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    void launchBrowser() {
        playwright = Playwright.create();
        boolean headless = !"false".equals(System.getProperty("headless"));
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
    }

    @BeforeEach
    void newContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions()
                .setUserAgent(BrowserDefaults.DESKTOP_CHROME_USER_AGENT));
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @AfterAll
    void closeBrowser() {
        browser.close();
        playwright.close();
    }
}
