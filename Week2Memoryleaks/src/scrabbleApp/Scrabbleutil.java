package scrabbleApp;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Utility class containing the core (non-GUI) logic for the Scrabble
 * Arrangement application.
 * <p>
 * This class is deliberately kept separate from {@link MainScrabble} so that
 * it can be unit tested independently (see {@link ScrabbleutilTest}) and so
 * that it can be stepped through cleanly in the Eclipse debugger without
 * GUI event-dispatch noise getting in the way.
 *
 * @author  Leo A
 * @version 2.0
 */
public class Scrabbleutil {

    /** Maximum number of Scrabble tiles the app will accept. */
    public static final int MAX_TILES = 7;

    /**
     * Validates a string of tiles entered by the user.
     * <p>
     * A valid set of tiles must:
     * <ul>
     *   <li>contain between 1 and {@value #MAX_TILES} characters, and</li>
     *   <li>contain only letters of the English alphabet (A&ndash;Z, a&ndash;z).</li>
     * </ul>
     *
     * @param tiles the raw text entered by the user
     * @throws IllegalArgumentException if the tiles are empty, contain more
     *         than {@value #MAX_TILES} characters, or contain any
     *         non-alphabetic character. The exception's message is intended
     *         to be shown directly to the user.
     */
    public static void validateTiles(String tiles) {
        if (tiles == null || tiles.isEmpty()) {
            throw new IllegalArgumentException("Please enter at least one letter.");
        }
        if (tiles.length() > MAX_TILES) {
            throw new IllegalArgumentException(
                    "Too many tiles! You entered " + tiles.length()
                            + " characters, but the maximum is " + MAX_TILES + ".");
        }
        for (int i = 0; i < tiles.length(); i++) {
            char c = tiles.charAt(i);
            if (!Character.isLetter(c)) {
                throw new IllegalArgumentException(
                        "Invalid character '" + c + "' at position " + (i + 1)
                                + ". Only letters A-Z are allowed.");
            }
        }
    }

    /**
     * Generates every distinct arrangement (permutation) of the given tiles,
     * where every arrangement uses <em>all</em> of the tiles exactly once.
     * <p>
     * Duplicate letters (e.g. two "A" tiles) are handled so that duplicate
     * arrangements are not produced twice.
     *
     * @param tiles the letters to arrange; assumed to already be valid
     *              (call {@link #validateTiles(String)} first)
     * @return a {@link Set} containing every unique arrangement of the
     *         tiles, preserving discovery order
     */
    public static Set<String> generateArrangements(String tiles) {
    	char[] letters = tiles.toUpperCase().toCharArray();
        
        java.util.Arrays.sort(letters); // group duplicate letters together
        
        Set<String> results = new LinkedHashSet<>();
        
        boolean[] used = new boolean[letters.length];
       
        StringBuilder current = new StringBuilder();
       
        permute(letters, used, current, results);
        
        return results;
    }

    /**
     * Recursive backtracking helper that builds up permutations one
     * character at a time.
     * <p>
     * This is a good candidate method on which to set a breakpoint and
     * practice Step Into / Step Over / Step Return, since it calls itself.
     *
     * @param letters the sorted array of letters to permute
     * @param used    tracks which indices of {@code letters} are already
     *                placed into {@code current}
     * @param current the arrangement being built on this recursive branch
     * @param results the accumulating set of completed arrangements
     */
    private static void permute(char[] letters, boolean[] used,
            StringBuilder current, Set<String> results) {
        if (current.length() == letters.length) {
            results.add(current.toString());
            return;
        }
        for (int i = 0; i < letters.length; i++) {
            if (used[i]) {
                continue;
            }
            // Skip duplicate letters at the same recursion depth so we
            // don't generate the same arrangement more than once.
            if (i > 0 && letters[i] == letters[i - 1] && !used[i - 1]) {
                continue;
            }
            used[i] = true;
            current.append(letters[i]);

            permute(letters, used, current, results);

            // backtrack
            current.deleteCharAt(current.length() - 1);
            used[i] = false;
        }
    }
}