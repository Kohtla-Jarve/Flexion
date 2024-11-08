import java.util.Scanner;

public class Converter {
    private static final String CFKR = "cfkr"; // Celsius, Fahrenheit, Kelvin, Rankine
    private static final String LTICFG = "lticfg"; // liters, tablespoons, cubic-inches, cups, cubic-feet, gallons
    // below theoretical temperature minimums
    private static final double FaregnheitMIN = -459.67;
    private static final double CelsiusMIN = -273.15;
    private static final double KelvinMIN = 0;
    private static final double RankineMIN = 0;
    // below volume constants
    private static final double LiterToUsTableSpoon = 67.628045; // "t"
    private static final double LiterToUsCups = 4.22675; // "c"
    private static final double LiterToUsGallon = 0.264172; // "g"
    private static final double LiterToCubicFeet = 0.0353147;  // "f"
    private static final double LiterToCubicInch = 61.0237; // "i"

    // due to functional similarity both temperature & volume share variable naming convention
    private static double teacherNumeric;
    private static double studentNumeric;
    private static double convertedNumeric;


    public static void main(String[] args) {

        if (args.length > 0 && args[0].equals("help") ) {
            help();
        }

        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter arguments, separated by spaces: ");
//        String input = scanner.nextLine();
//        String trimmedText = removeExtraSpaces(input);
//        System.out.println(trimmedText);
//        String[] split = trimmedText.split(" ");
        String [] split = {"t", "-450", "f", "k", "5.3722"};
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
        teacherNumeric = Double.parseDouble(split[1]);
        studentNumeric = Double.parseDouble(split[4]);
        // ensure volume patterns match
        if(!LTICFG.contains(split[2]) || !LTICFG.contains(split[3])) {
            System.out.println("invalid volume input");
        } else {
            switch(split[2].concat(split[3])) {
                case "lt":
                    convertedNumeric = literToUsTableSpoon(teacherNumeric);
                    break;
                case "li":
                    convertedNumeric = literToCubicInch(teacherNumeric);
                    break;
                case "lc":
                    convertedNumeric = literToUsCups(teacherNumeric);
                    break;
                case "lf":
                    convertedNumeric = literToCubicFeet(teacherNumeric);
                    break;
                case "lg":
                    convertedNumeric = literToUsGallon(teacherNumeric);
                    break;

                default:
                    System.out.println("unknown volume conversion " + split[2] + split[3]);
                    break;
            }
            System.out.println("tempConverted from " + split[2] + " to " + split[3] + " " + convertedNumeric);
            System.out.println(roundToTheTenthsPlace(convertedNumeric) + " == " + roundToTheTenthsPlace(studentNumeric));
            System.out.println(roundToTheTenthsPlace(convertedNumeric) == roundToTheTenthsPlace(studentNumeric) ? "correct" : "incorrect");
        }

    }

    private static void computeTemperature(String[] split) {
        teacherNumeric = Double.parseDouble(split[1]);
        studentNumeric = Double.parseDouble(split[4]);
        // ensure temperature patterns match
        if(!CFKR.contains(split[2]) || !CFKR.contains(split[3])) {
            System.out.println("invalid temperature input");
        } else {
            switch(split[2].concat(split[3])) {
                case "ck":
                    convertedNumeric = celsiusToKelvin(teacherNumeric);
                    break;
                case "cf":
                    convertedNumeric = celsiusToFahrenheit(teacherNumeric);
                    break;
                case "cr":
                    convertedNumeric = celsiusToRankine(teacherNumeric);
                    break;
                case "rc":
                    convertedNumeric = rankineToCelsius(teacherNumeric);
                    break;
                case "rf":
                    convertedNumeric = celsiusToFahrenheit(rankineToCelsius(teacherNumeric));
                    break;
                case "rk":
                    convertedNumeric = celsiusToKelvin(rankineToCelsius(teacherNumeric));
                    break;
                case "kc":
                    convertedNumeric = kelvinToCelcius(teacherNumeric);
                    break;
                case "kr":
                    convertedNumeric = celsiusToRankine(kelvinToCelcius(teacherNumeric));
                    break;
                case "kf":
                    convertedNumeric = celsiusToFahrenheit(kelvinToCelcius(teacherNumeric));
                    break;
                case "fc":
                    convertedNumeric = fahrenheitToCelsius(teacherNumeric);
                    break;
                case "fr":
                    convertedNumeric = celsiusToRankine(fahrenheitToCelsius(teacherNumeric));
                    break;
                case "fk":
                    convertedNumeric = celsiusToKelvin(fahrenheitToCelsius(teacherNumeric));
                    break;
                default:
                    System.out.println("unknown temperature conversion " + split[2] + split[3]);
                    break;
            }
            System.out.println("convertedNumeric from " + split[2] + " to " + split[3] + " " + convertedNumeric);
            System.out.println(roundToTheTenthsPlace(convertedNumeric) + " == " + roundToTheTenthsPlace(studentNumeric));
            System.out.println(roundToTheTenthsPlace(convertedNumeric) == roundToTheTenthsPlace(studentNumeric) ? "correct" : "incorrect");
        }
    }

    private static double roundToTheTenthsPlace(double value) {
        return (double)(Math.round(value * 10)) / 10;
    }

    private static String removeExtraSpaces(String text) {
        return text.replaceAll("\\s+", " ").trim();
    }

    private static boolean validateInput(String [] split) {
        double teacher;
        double student;
        if (split.length != 5) return false; // check number of user arguments
        if (!"t".equals(split[0]) && !"v".equals(split[0])) return false; // high level unit validation
        if (split[2].equals(split[3])) return false;  // source and destination must be distinct
        try {
            // validate input for numeric CLI arguments
            teacher = Double.parseDouble(split[1]);
            student = Double.parseDouble(split[4]);
        } catch (NumberFormatException badUserData) {
            System.out.println("Invalid numeric input!!! Try again: " + split[1] + " / " + split[4]);
            return false;
        }
        // volumes must be more than zero
        if ("v".equals(split[0]) && (teacher <= 0 || student <= 0)) return false;

        // temperatures must be above theoretical minimums
        if ("t".equals(split[0])) {
            switch(split[2]) {
                case "c":
                    if(teacher < CelsiusMIN) return false;
                    break;
                case "k":
                    if(teacher < KelvinMIN) return false;
                    break;
                case "f":
                    if(teacher < FaregnheitMIN) return false;
                    break;
                case "r":
                    if(teacher < RankineMIN) return false;
                    break;
                default:
                    System.out.println("improper teacher temperature input ");
                    break;
            }
        }
        return true;
    }

    // All temperature conversion will be "rooted" to Celsius grade to minimize combinations
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

    // All volume conversion will be "rooted" to Liter common denominator
    private static double literToUsTableSpoon(double volume) {
        return volume * LiterToUsTableSpoon;
    }

    private static double literToUsCups(double volume) {
        return volume * LiterToUsCups;
    }

    private static double literToCubicFeet(double volume) {
        return volume * LiterToCubicFeet;
    }

    private static double literToCubicInch(double volume) {
        return volume * LiterToCubicInch;
    }

    private static double literToUsGallon(double volume) {
        return volume * LiterToUsGallon;
    }
}



