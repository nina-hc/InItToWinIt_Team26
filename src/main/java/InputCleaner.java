package java;

import java.util.Scanner;


public class InputCleaner {


        //clean input string: remove extra spaces, make lowercase
        public static String cleaner(String input) {
            if (input == null) {
                return "";
            }
            input = input.trim();  //remove spaces at start/end
            input = input.replaceAll("\\s+", " "); //replace multiple spaces with single space

            //lowercase version for parser
            return input.toLowerCase();
        }

}


/**
 * Main class to test InputCleaner
 * Reads user input and shows the cleaned version
 */
class MainCleanerTest {

    public static void main(String[] args) {

        // Scanner for reading input from the console
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== InputCleaner Test ===");
        System.out.println("Type some commands (e.g., Roll, build Settlement 5, GO, list).");
        System.out.println("Type 'exit' to quit.");

        while (true) {
            System.out.print("\nEnter command: ");
            String userInput = scanner.nextLine();

            // Exit condition
            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("Exiting InputCleaner test. Bye!");
                break;
            }

            // Clean input using InputCleaner
            String cleanedInput = InputCleaner.cleaner(userInput); // converts to lowercase and trims

            // Show the cleaned input
            System.out.println("Original input: " + userInput);
            System.out.println("Cleaned input:  " + cleanedInput);
        }

    }
}
