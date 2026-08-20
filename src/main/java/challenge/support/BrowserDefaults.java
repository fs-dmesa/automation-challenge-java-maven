package challenge.support;

public final class BrowserDefaults {

    /**
     * The storefront's WAF returns 403 for Playwright's default headless UA
     * (it includes the literal string "HeadlessChrome"). A normal desktop UA
     * passes. Applied to every context so this is transparent to test authors.
     */
    public static final String DESKTOP_CHROME_USER_AGENT =
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 "
                    + "(KHTML, like Gecko) Chrome/120.0 Safari/537.36";

    private BrowserDefaults() {
    }
}
