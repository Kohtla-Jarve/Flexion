import java.util.Scanner;

public class Converter {
    public static final String CFKR = "cfkr";
    public static final String LTICFG = "lticfg";
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        if (args[0].equals("help")) {
            help();
        }

//        System.out.println("Enter arguments, separated by spaces: ");
//        String input = scanner.nextLine();
//        String trimmedText = removeExtraSpaces(input);
//        System.out.println(trimmedText);
//        String[] split = trimmedText.split(" ");
        String [] split = {"t", "c", "g", "100.11"};
    //    String [] split = {"v", "l", "g", "100.11"};
        if(validateInput(split)) {
            System.out.println("valid input");
            compute(split);
        } else {
            System.out.println("invalid");
        }

        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
            }
        }

    private static void help() {
        System.out.println("Usage: java Converter <input> <output>");
        System.exit(0);
    }

    private static void compute(String[] split) {
        switch(split[0]) {
            case "t":
                computeTemperature(split);
                break;
             case "v":
                 computeVolume(split);
                 break;
             default:
                 System.out.println("neither t or v");
                 break;
            }
        }

    private static void computeVolume(String[] split) {
        System.out.println("computing volume");
        System.out.println(LTICFG.contains(split[1]) && LTICFG.contains(split[2])) ; // ensure volume patterns match
    }

    private static void computeTemperature(String[] split) {
        System.out.println("computing temperature");
        System.out.println(CFKR.contains(split[1]) && CFKR.contains(split[2])); // ensure temperature patterns match
    }

    private static String removeExtraSpaces(String text) {
        return text.replaceAll("\\s+", " ").trim();
    }

    private static boolean validateInput(String [] split) {
        if (split.length != 4) return false; // check number of user arguments
        if (!split[0].equals("t") && !split[0].equals("v")) return false; // high level unit validation
        if (split[1].equals(split[2])) return false;  // source and destination must be distinct
        try {
            validateDoubleInput(split[3]);  // validate numeric input for last CLI argument
        } catch (NumberFormatException badUserData) {
            System.out.println("Invalid numeric input!!! Try again: " + split[3]);
            return false;
        }
        return true;
    }

    private static double validateDoubleInput(String number) {
        return Double.valueOf(number.trim());
    }
}



