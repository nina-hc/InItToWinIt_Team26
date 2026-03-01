package java;


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


