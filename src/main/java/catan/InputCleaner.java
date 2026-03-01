package catan;

public class InputCleaner {

    // Clean input string: remove extra spaces, make lowercase
    public static String clean(String input) {
        if (input == null) return "";
        input = input.trim();               // Remove spaces at start/end
        input = input.replaceAll("\\s+", " "); // Replace multiple spaces with single space
        return input;
    }

}
