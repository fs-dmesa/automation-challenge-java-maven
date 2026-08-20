package challenge;

import challenge.pages.StorefrontPage;
import challenge.support.ChallengeAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Verifies the environment is wired up correctly - the actual challenge is
 * given during the session, not committed to this repo.
 */
public class StorefrontSmokeTest extends BaseTest {

    @Test
    @DisplayName("Product title is visible on load")
    void productTitleIsVisibleOnLoad() {
        StorefrontPage storefront = new StorefrontPage(page).open().waitForProductTitleVisible();

        String title = storefront.productTitle().textContent().trim();

        ChallengeAssertions.assertFalse(title.isEmpty(), "Expected a non-empty product title");
    }
}
