package catan;

/**
 * Utility class for cleaning player input strings.
 * Removes extra spaces and converts input to lowercase for parsing.
 *
 * @author Marva Hassan
 */
public class InputCleaner {

    /**
     * Cleans the input string by trimming whitespace, collapsing multiple spaces,
     * and converting to lowercase.
     *
     * @param input The raw input string
     * @return A cleaned, lowercase version of the input
     */
    public static String cleaner(String input) {

        //Return empty string if input is null
        if (input == null) {
            return "";
        }

        //Remove spaces at start/end
        input = input.trim();

        //Replace multiple consecutive spaces with a single space
        input = input.replaceAll("\\s+", " ");

        //Return lowercase version for parser
        return input.toLowerCase();
    }
}