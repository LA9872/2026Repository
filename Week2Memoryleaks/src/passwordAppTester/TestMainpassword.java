package passwordAppTester;

import passwordApp.Passwordutil;

/**
 * Simple, dependency-free test harness for {@link PasswordUtil}.
 * This class does not use a testing framework (JUnit) so that it can be run
 * as a plain Java application directly from Eclipse's Debug perspective &mdash;
 * set breakpoints inside any {@code testXxx()} method below, then Debug As
 * &gt; Java Application to step through validation and block-detection logic.
 *
 * @author  Leo A
 * @version 2.0
 */
public class TestMainpassword {

    /** Running count of assertions that failed, printed at the end. */
    private static int failures = 0;

    /**
     * Runs all test methods in this class and prints a summary.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        testValidPasswordPasses();
        testTooShortThrows();
        testTooLongThrows();
        testSpaceThrows();
        testDecentPasswordExample();
        testWeakPasswordExample();
        testAllSameCharacterBlock();
        testNoRepeatsBlockOfOne();

        if (failures == 0) {
            System.out.println("\nALL TESTS PASSED");
        } else {
            System.out.println("\n" + failures + " TEST(S) FAILED");
        }
    }

    /** Confirms an 8-12 character, space-free password passes validation. */
    private static void testValidPasswordPasses() {
        try {
            Passwordutil.validatePassword("7hoopla1");
            System.out.println("PASS: valid password accepted");
        } catch (IllegalArgumentException ex) {
            fail("valid password was rejected: " + ex.getMessage());
        }
    }

    /** Confirms a password shorter than 8 characters is rejected. */
    private static void testTooShortThrows() {
        try {
            Passwordutil.validatePassword("short1");
            fail("password under 8 characters should have thrown an exception");
        } catch (IllegalArgumentException ex) {
            System.out.println("PASS: too-short error -> " + ex.getMessage());
        }
    }

    /** Confirms a password longer than 12 characters is rejected. */
    private static void testTooLongThrows() {
        try {
            Passwordutil.validatePassword("waytoolongpassword1");
            fail("password over 12 characters should have thrown an exception");
        } catch (IllegalArgumentException ex) {
            System.out.println("PASS: too-long error -> " + ex.getMessage());
        }
    }

    /** Confirms a password containing a space is rejected. */
    private static void testSpaceThrows() {
        try {
            Passwordutil.validatePassword("has a space");
            fail("password with a space should have thrown an exception");
        } catch (IllegalArgumentException ex) {
            System.out.println("PASS: space error -> " + ex.getMessage());
        }
    }

    /** Matches the assignment's first example: "7hoopla" -> block of 2, decent. */
    private static void testDecentPasswordExample() {
        int block = Passwordutil.largestBlockLength("7hoopla");
        check(block == 2, "expected largest block 2 for '7hoopla', got " + block);
        String message = Passwordutil.describeStrength("7hoopla");
        check(message.equals("The largest block in the password is 2. This is a decent password."),
                "unexpected message for '7hoopla': " + message);
    }

    /** Matches the assignment's second example: "xyyyyyyy2" -> block of 7, reduce by 5. */
    private static void testWeakPasswordExample() {
        int block = Passwordutil.largestBlockLength("xyyyyyyy2");
        check(block == 7, "expected largest block 7 for 'xyyyyyyy2', got " + block);
        String message = Passwordutil.describeStrength("xyyyyyyy2");
        check(message.equals("The largest block in the password is 7. "
                + "This password can be made stronger by reducing this block by 5;"),
                "unexpected message for 'xyyyyyyy2': " + message);
    }

    /** A password that is one repeated character should report a block equal to its length. */
    private static void testAllSameCharacterBlock() {
        int block = Passwordutil.largestBlockLength("aaaaaaaa");
        check(block == 8, "expected largest block 8 for 'aaaaaaaa', got " + block);
    }

    /** A password with no repeated adjacent characters should report a block of 1. */
    private static void testNoRepeatsBlockOfOne() {
        int block = Passwordutil.largestBlockLength("abcdefgh");
        check(block == 1, "expected largest block 1 for 'abcdefgh', got " + block);
    }

    /**
     * Records a test failure and prints a message, without stopping the
     * remaining tests from running.
     *
     * @param message description of what went wrong
     */
    private static void fail(String message) {
        failures++;
        System.out.println("FAIL: " + message);
    }

    /**
     * Records a failure if the given condition is false.
     *
     * @param condition the condition expected to be true
     * @param message   description printed if the condition is false
     */
    private static void check(boolean condition, String message) {
        if (!condition) {
            fail(message);
        } else {
            System.out.println("PASS: ok - " + message);
        }
    }
}