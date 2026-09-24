package passwordApp;

/**
 * Utility class containing the core (non-GUI) logic for the Password
 * Strength application.
 * <p>
 * Kept separate from {@link Mainpassword} so it can be unit tested
 * independently (see {@code passwordAppTester.TestMainpassword}) and
 * stepped through cleanly in the Eclipse debugger without GUI
 * event-dispatch noise getting in the way.
 *
 * @author  Leo A
 * @version 2.0
 */
public class Passwordutil {

    /** Minimum allowed password length, inclusive. */
    public static final int MIN_LENGTH = 8;

    /** Maximum allowed password length, inclusive. */
    public static final int MAX_LENGTH = 12;

    /** A password is considered decent when its largest block is no bigger than this. */
    public static final int DECENT_BLOCK_THRESHOLD = 2;

    /**
     * Validates a password entered by the user.
     * <p>
     * A valid password must:
     * <ul>
     *   <li>be between {@value #MIN_LENGTH} and {@value #MAX_LENGTH}
     *       characters long, and</li>
     *   <li>contain no space characters.</li>
     * </ul>
     *
     * @param password the raw text entered by the user
     * @throws IllegalArgumentException if the password is null, contains a
     *         space, or is not between {@value #MIN_LENGTH} and
     *         {@value #MAX_LENGTH} characters long. The exception's message
     *         is intended to be shown directly to the user.
     */
    public static void validatePassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Please enter a password.");
        }
        if (password.contains(" ")) {
            throw new IllegalArgumentException("Passwords cannot contain a space.");
        }
        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "Password must be between " + MIN_LENGTH + " and "
                            + MAX_LENGTH + " characters long. You entered "
                            + password.length() + ".");
        }
    }

    /**
     * Computes the length of the largest "block" in the password, where a
     * block is a run of adjacent characters that are exactly (case
     * sensitive) the same.
     * <p>
     * For example, {@code "xyyyyyyy2"} has a block of seven consecutive
     * {@code 'y'} characters, so this method returns {@code 7}.
     *
     * @param password the password to scan; assumed to already be valid
     *                 (call {@link #validatePassword(String)} first)
     * @return the length of the longest run of identical adjacent
     *         characters; returns 0 for an empty string
     */
    public static int largestBlockLength(String password) {
        if (password == null || password.isEmpty()) {
            return 0;
        }

        int largest = 1;
        int current = 1;

        for (int i = 1; i < password.length(); i++) {
            if (password.charAt(i) == password.charAt(i - 1)) {
                current++;
            } else {
                current = 1;
            }
            if (current > largest) {
                largest = current;
            }
        }
        return largest;
    }

    /**
     * Builds the exact user-facing message describing the password's
     * strength, matching the format:
     * <pre>
     * "The largest block in the password is 2. This is a decent password."
     * "The largest block in the password is 7. This password can be made
     *  stronger by reducing this block by 5;"
     * </pre>
     *
     * @param password the password to describe; assumed to already be valid
     * @return a human-readable strength message
     */
    public static String describeStrength(String password) {
        int largest = largestBlockLength(password);
        StringBuilder message = new StringBuilder();
        message.append("The largest block in the password is ")
                .append(largest).append(". ");

        if (largest <= DECENT_BLOCK_THRESHOLD) {
            message.append("This is a decent password.");
        } else {
            int reduceBy = largest - DECENT_BLOCK_THRESHOLD;
            message.append("This password can be made stronger by reducing this block by ")
                    .append(reduceBy).append(";");
        }
        return message.toString();
    }
}