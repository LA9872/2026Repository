package scrabbleAppTester;

import java.util.Set;

import scrabbleApp.Scrabbleutil;

/**
 * Simple, dependency-free test harness for {@link ScrabbleUtil}.
 * <p>
 * This class does not use a testing framework (JUnit) so that it can be run
 * as a plain Java application directly from Eclipse's Debug perspective &mdash;
 * set breakpoints inside any {@code testXxx()} method below, then Debug As
 * &gt; Java Application to step through validation and permutation logic.
 *
 * @author  Leo
 * @version 2.0
 */
public class TestMainScrabble {

    /** Running count of assertions that failed, printed at the end. */
    private static int failures = 0;

    /**
     * Runs all test methods in this class and prints a summary.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        testValidTilesPasses();
        testTooManyTilesThrows();
        testNonLetterThrows();
        testEmptyTilesThrows();
        testPermutationCountNoDuplicates();
        testPermutationCountWithDuplicates();
        testSingleLetter();

        if (failures == 0) {
            System.out.println("\nALL TESTS PASSED");
        } else {
            System.out.println("\n" + failures + " TEST(S) FAILED");
        }
    }

    /** Confirms that a normal 7-letter, all-alphabetic input passes validation. */
    private static void testValidTilesPasses() {
        try {
            Scrabbleutil.validateTiles("CATDOGS");
            System.out.println("PASS: valid tiles accepted");
        } catch (IllegalArgumentException ex) {
            fail("valid tiles were rejected: " + ex.getMessage());
        }
    }

    /** Confirms that more than 7 tiles is rejected with an error message. */
    private static void testTooManyTilesThrows() {
        try {
            Scrabbleutil.validateTiles("TOOMANYTILES");
            fail("8+ tiles should have thrown an exception");
        } catch (IllegalArgumentException ex) {
            System.out.println("PASS: too-many-tiles error -> " + ex.getMessage());
        }
    }

    /** Confirms that a non-letter character is rejected with an error message. */
    private static void testNonLetterThrows() {
        try {
            Scrabbleutil.validateTiles("CAT5OG");
            fail("non-letter character should have thrown an exception");
        } catch (IllegalArgumentException ex) {
            System.out.println("PASS: non-letter error -> " + ex.getMessage());
        }
    }

    /** Confirms that empty input is rejected. */
    private static void testEmptyTilesThrows() {
        try {
            Scrabbleutil.validateTiles("");
            fail("empty input should have thrown an exception");
        } catch (IllegalArgumentException ex) {
            System.out.println("PASS: empty-input error -> " + ex.getMessage());
        }
    }

    /** For 4 distinct letters there should be exactly 4! = 24 arrangements. */
    private static void testPermutationCountNoDuplicates() {
        Set<String> results = Scrabbleutil.generateArrangements("WORD");
        check(results.size() == 24,
                "expected 24 arrangements of WORD, got " + results.size());
    }

    /** With a repeated letter, duplicate arrangements must be collapsed. */
    private static void testPermutationCountWithDuplicates() {
        // "AAB" has 3!/2! = 3 unique arrangements: AAB, ABA, BAA
        Set<String> results = Scrabbleutil.generateArrangements("AAB");
        check(results.size() == 3,
                "expected 3 unique arrangements of AAB, got " + results.size());
        check(results.contains("AAB") && results.contains("ABA") && results.contains("BAA"),
                "AAB arrangements missing an expected value: " + results);
    }

    /** A single tile should produce exactly one "arrangement": itself. */
    private static void testSingleLetter() {
        Set<String> results = Scrabbleutil.generateArrangements("Q");
        check(results.size() == 1 && results.contains("Q"),
                "expected single arrangement 'Q', got " + results);
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