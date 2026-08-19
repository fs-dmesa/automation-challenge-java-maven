package challenge;

import challenge.support.BrowserDefaults;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Shared browser across the class, fresh context and page per test.
 * Pass -Dheadless=false to watch the browser while iterating locally.
 * A trace is recorded for every test but only written to disk when the test
 * doesn't pass - view one with `npx playwright show-trace traces/&lt;name&gt;.zip`.
 *
 * Tracing is stopped (and the context closed) from the TestWatcher callbacks,
 * not from an @AfterEach - JUnit 5 runs @AfterEach BEFORE TestWatcher fires,
 * so the pass/fail outcome isn't known yet at @AfterEach time. Verified with a
 * throwaway ordering test before relying on it.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    private Playwright playwright;
    private Browser browser;
    protected BrowserContext context;
    protected Page page;

    @RegisterExtension
    final TestWatcher traceWatcher = new TestWatcher() {
        @Override
        public void testSuccessful(ExtensionContext extensionContext) {
            stopTracingAndClose(extensionContext, null);
        }

        @Override
        public void testAborted(ExtensionContext extensionContext, Throwable cause) {
            stopTracingAndClose(extensionContext, tracePathFor(extensionContext));
        }

        @Override
        public void testFailed(ExtensionContext extensionContext, Throwable cause) {
            stopTracingAndClose(extensionContext, tracePathFor(extensionContext));
        }
    };

    private Path tracePathFor(ExtensionContext extensionContext) {
        String safeName = extensionContext.getDisplayName().replaceAll("[^a-zA-Z0-9._-]", "_");
        return Paths.get("traces", safeName + ".zip");
    }

    private void stopTracingAndClose(ExtensionContext extensionContext, Path tracePath) {
        context.tracing().stop(new Tracing.StopOptions().setPath(tracePath));
        context.close();
    }

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
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        page = context.newPage();
    }

    @AfterAll
    void closeBrowser() {
        browser.close();
        playwright.close();
    }
}
