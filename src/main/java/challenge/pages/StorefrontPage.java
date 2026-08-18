package challenge.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import challenge.support.Timeouts;

public class StorefrontPage {

    public static final String URL = "https://qainterviewstore.test.qa7.onfastspring.com/digital-product";

    private final Page page;

    public StorefrontPage(Page page) {
        this.page = page;
    }

    public StorefrontPage open() {
        page.navigate(URL);
        return this;
    }

    public Locator productTitle() {
        return page.locator(".product-title").first();
    }

    public StorefrontPage waitForProductTitleVisible() {
        productTitle().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(Timeouts.DEFAULT_TIMEOUT_MS));
        return this;
    }
}
