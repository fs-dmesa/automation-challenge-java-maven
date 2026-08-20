package challenge.support;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;

/**
 * Thin wrapper around JUnit 5's assertions that also logs each one as an
 * Allure step, so passed assertions show up in the report timeline instead
 * of disappearing once the test goes green.
 */
public final class ChallengeAssertions {

    private ChallengeAssertions() {
    }

    public static void assertTrue(boolean condition, String message) {
        Allure.step(message, () -> Assertions.assertTrue(condition, message));
    }

    public static void assertFalse(boolean condition, String message) {
        Allure.step(message, () -> Assertions.assertFalse(condition, message));
    }

    public static void assertEquals(Object expected, Object actual, String message) {
        Allure.step(message, () -> Assertions.assertEquals(expected, actual, message));
    }

    public static void assertNotEquals(Object expected, Object actual, String message) {
        Allure.step(message, () -> Assertions.assertNotEquals(expected, actual, message));
    }
}
