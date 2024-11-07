import java.util.Scanner;

public class Converter {
    public static final String CFKR = "cfkr"; // Celsius, Fahrenheit, Kelvin, Rankine
    public static final String LTICFG = "lticfg"; // liters, tablespoons, cubic-inches, cups, cubic-feet, gallons
    // below theoretical temperature minimums per scale for boundaries validation
    public static final double FaregnheitMIN = -459.67;
    public static final double CelsiusMIN = -273.15;
    public static final double KelvinMIN = 0;
    public static final double RankineMIN = 0;


    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("help") ) {
            help();
        }

//        System.out.println("Enter arguments, separated by spaces: ");
//        String input = scanner.nextLine();
//        String trimmedText = removeExtraSpaces(input);
//        System.out.println(trimmedText);
//        String[] split = trimmedText.split(" ");
        String [] split = {"t", "100", "f", "k", "310.93"};
    //    String [] split = {"v","100.11", "l", "g", "100.11"};
        if(validateInput(split)) {
            compute(split);
        } else {
            System.out.println("invalid");
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
        System.out.println(LTICFG.contains(split[2]) && LTICFG.contains(split[3])) ; // ensure volume patterns match
    }

    private static void computeTemperature(String[] split) {
        double tempConverted = 0;
        double tempToConvert = Double.valueOf(split[1]);
        double tempByStudent = Double.valueOf(split[4]);
        // ensure temperature patterns match
        if(!CFKR.contains(split[2]) || !CFKR.contains(split[3])) {
            System.out.println("invalid temperature input");
        } else {
            switch(split[2].concat(split[3])) {
                case "ck":
                    tempConverted = celsiusToKelvin(tempToConvert);
                    break;
                case "cf":
                    tempConverted = celsiusToFahrenheit(tempToConvert);
                    break;
                case "cr":
                    tempConverted = celsiusToRankine(tempToConvert);
                    break;
                case "rc":
                    tempConverted = rankineToCelsius(tempToConvert);
                    break;
                case "rf":
                    tempConverted = celsiusToFahrenheit(rankineToCelsius(tempToConvert));
                    break;
                case "rk":
                    tempConverted = celsiusToKelvin(rankineToCelsius(tempToConvert));
                    break;
                case "kc":
                    tempConverted = kelvinToCelcius(tempToConvert);
                    break;
                case "kr":
                    tempConverted = celsiusToRankine(kelvinToCelcius(tempToConvert));
                    break;
                case "kf":
                    tempConverted = celsiusToFahrenheit(kelvinToCelcius(tempToConvert));
                    break;
                case "fc":
                    tempConverted = fahrenheitToCelsius(tempToConvert);
                    break;
                case "fr":
                    tempConverted = celsiusToRankine(fahrenheitToCelsius(tempToConvert));
                    break;
                case "fk":
                    tempConverted = celsiusToKelvin(fahrenheitToCelsius(tempToConvert));
                    break;
                default:
                    System.out.println("unknown temperature conversion " + split[2] + split[3]);
                    break;
            }
            // System.out.println("tempConverted from " + split[2] + " to " + split[3] + " " + tempConverted);
            // System.out.println(roundToTwoDecimals(tempConverted) + " == " + roundToTwoDecimals(tempByStudent));
            System.out.println(roundToTwoDecimals(tempConverted) == roundToTwoDecimals(tempByStudent) ? "correct" : "incorrect");
        }
    }

    private static double roundToTwoDecimals(double value) {
        return (double)(Math.round(value * 100)) / 100;
    }

    private static String removeExtraSpaces(String text) {
        return text.replaceAll("\\s+", " ").trim();
    }

    private static boolean validateInput(String [] split) {
        if (split.length != 5) return false; // check number of user arguments
        if (!split[0].equals("t") && !split[0].equals("v")) return false; // high level unit validation
        if (split[2].equals(split[3])) return false;  // source and destination must be distinct
        try {
            // validate input for numeric CLI arguments
            Double.parseDouble(split[1]);
            Double.parseDouble(split[4]);
        } catch (NumberFormatException badUserData) {
            System.out.println("Invalid numeric input!!! Try again: " + split[1] + " / " + split[4]);
            return false;
        }
        return true;
    }

    // All temperature conversion will be "rooted" to Celsius grade to avoid many-to-many combinations
    private static double celsiusToFahrenheit(double degrees) {
        return degrees * 9/5 + 32;
    }
    private static double celsiusToKelvin(double degrees) {
        return degrees + 273.15;
    }

    private static double celsiusToRankine(double degrees) {
        return degrees * 9/5 + 491.67;
    }

    private static double fahrenheitToCelsius(double degrees) {
        return (degrees - 32) * 5/9;
    }
    private static double kelvinToCelcius(double degrees) {
        return degrees - 273.15;
    }
    private static double rankineToCelsius(double degrees) {
        return (degrees - 491.67) * 5/9;
    }
}



