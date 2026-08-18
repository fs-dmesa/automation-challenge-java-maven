package challenge;

import challenge.pages.StorefrontPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Verifies the environment is wired correctly. This test should pass before
 * the interview starts - the actual challenge is given during the session.
 */
public class StorefrontSmokeTest extends BaseTest {

    @Test
    void productTitleIsVisibleOnLoad() {
        StorefrontPage storefront = new StorefrontPage(page).open().waitForProductTitleVisible();

        String title = storefront.productTitle().textContent().trim();

        Assertions.assertFalse(title.isEmpty(), "Expected a non-empty product title");
    }
}
