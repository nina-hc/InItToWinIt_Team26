package a2_tests;

import catan.InputCleaner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * testing Input Cleaner Class
 * @author Marva Hassan
 */
class InputCleanerTest {

    //null input should return empty string
    @Test
    void nullInputReturnsEmptyString() {
        String result = InputCleaner.cleaner(null);
        assertEquals("", result);
    }

    //removes leading and trailing spaces
    @Test
    void trimsLeadingAndTrailingSpaces() {
        String result = InputCleaner.cleaner("   roll   ");
        assertEquals("roll", result);
    }

    //collapses multiple internal spaces into one
    @Test
    void collapsesMultipleSpaces() {
        String result = InputCleaner.cleaner("build    settlement     5");
        assertEquals("build settlement 5", result);
    }

    //converts uppercase letters to lowercase
    @Test
    void convertsToLowercase() {
        String result = InputCleaner.cleaner("RoLl");
        assertEquals("roll", result);
    }

    //whitespace only string becomes empty
    @Test
    void whitespaceOnlyBecomesEmpty() {
        String result = InputCleaner.cleaner("     ");
        assertEquals("", result);
    }

    //already clean string remains unchanged
    @Test
    void alreadyCleanString() {
        String result = InputCleaner.cleaner("build city 10");
        assertEquals("build city 10", result);
    }

    //combined transformation: trim + collapse + lowercase
    @Test
    void combinedTransformation() {
        String result = InputCleaner.cleaner("   BuIlD   RoAd   3 ,   4   ");
        assertEquals("build road 3 , 4", result);
    }

    //empty string remains empty
    @Test
    void emptyStringRemainsEmpty() {
        String result = InputCleaner.cleaner("");
        assertEquals("", result);
    }
}