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
    private static final double LiterToUsCups = 4.22675; // "c",  ambiguous values US cup vs. US legal cap: 4.16667
    private static final double LiterToUsGallon = 0.264172; // "g"
    private static final double LiterToCubicFeet = 0.0353147;  // "f"
    private static final double LiterToCubicInch = 61.023744; // "i"

    // due to functional similarity both temperature & volume share variable naming convention
    private static double teacherNumeric;
    private static double studentNumeric;
    private static double convertedNumeric;


    public static void main(String[] args) {

        if (args.length > 0 && args[0].equals("help") ) {
            help();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter arguments, separated by spaces: ");
        String input = scanner.nextLine();
        String trimmedText = removeExtraSpaces(input);
        String[] arr = trimmedText.split(" ");
        if(validateInput(arr)) {

            compute(arr);
        } else {
            System.out.println("invalid");
        }
    }

    private static void help() {
        System.out.println("Usage: java Converter <input> <output>");
        System.exit(0);
    }

    private static void compute(String[] arr) {
        switch(arr[0]) {
            case "t":
                computeTemperature(arr);
                break;
             case "v":
                 computeVolume(arr);
                 break;
             default:
                 System.out.println("invalid");
                 break;
            }
        }


    private static void computeVolume(String[] arr) {
        teacherNumeric = Double.parseDouble(arr[1]);
        studentNumeric = Double.parseDouble(arr[4]);
        // ensure volume patterns match
        if(!LTICFG.contains(arr[2]) || !LTICFG.contains(arr[3])) {
            System.out.println("invalid");
        } else {
            switch(arr[2].concat(arr[3])) {
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
                case "tl":
                    convertedNumeric = 1 / literToUsTableSpoon(teacherNumeric);
                    break;
                case "ti":
                    convertedNumeric = literToCubicInch(1 / literToUsTableSpoon(teacherNumeric));
                    break;
                case "tc":
                    convertedNumeric = literToUsCups(1 / literToUsTableSpoon(teacherNumeric));
                    break;
                case "tf":
                    convertedNumeric = literToCubicFeet(1 / literToUsTableSpoon(teacherNumeric));
                    break;
                case "tg":
                    convertedNumeric = literToUsGallon(1 / literToUsTableSpoon(teacherNumeric));
                    break;
                case "il":
                    convertedNumeric = 1 / literToCubicInch(teacherNumeric);
                    break;
                case "it":
                    convertedNumeric = literToUsTableSpoon(1 / literToCubicInch(teacherNumeric));
                    break;
                case "ic":
                    convertedNumeric = literToUsCups(1 / literToCubicInch(teacherNumeric));
                    break;
                case "if":
                    convertedNumeric = literToCubicFeet(1 / literToCubicInch(teacherNumeric));
                    break;
                case "ig":
                    convertedNumeric = literToUsGallon(1 / literToCubicInch(teacherNumeric));
                    break;
                case "cl":
                    convertedNumeric = 1 / literToUsCups(teacherNumeric);
                    break;
                case "ct":
                    convertedNumeric = literToUsTableSpoon(1 / literToUsCups(teacherNumeric));
                    break;
                case "ci":
                    convertedNumeric = literToCubicInch(1 / literToUsCups(teacherNumeric));
                    break;
                case "cf":
                    convertedNumeric = literToCubicFeet(1 / literToUsCups(teacherNumeric));
                    break;
                case "cg":
                    convertedNumeric = literToUsGallon(1 / literToUsCups(teacherNumeric));
                    break;
                case "fl":
                    convertedNumeric = 1 / literToCubicFeet(teacherNumeric);
                    break;
                case "ft":
                    convertedNumeric = literToUsTableSpoon(1 / literToCubicFeet(teacherNumeric));
                    break;
                case "fi":
                    convertedNumeric = literToCubicInch(1 / literToCubicFeet(teacherNumeric));
                    break;
                case "fc":
                    convertedNumeric = literToUsCups(1 / literToCubicFeet(teacherNumeric));
                    break;
                case "fg":
                    convertedNumeric = literToUsGallon(1 / literToCubicFeet(teacherNumeric));
                    break;
                case "gl":
                    convertedNumeric = 1 / literToUsGallon(teacherNumeric);
                    break;
                case "gt":
                    convertedNumeric = literToUsTableSpoon(1 / literToUsGallon(teacherNumeric));
                    break;
                case "gi":
                    convertedNumeric = literToCubicInch(1 / literToUsGallon(teacherNumeric));
                    break;
                case "gc":
                    convertedNumeric = literToUsCups(1 / literToUsGallon(teacherNumeric));
                    break;
                case "gf":
                    convertedNumeric = literToCubicFeet(1 / literToUsGallon(teacherNumeric));
                    break;
                default:
                    System.out.println("invalid");
                    break;
            }
            System.out.println(roundToTheTenthsPlace(convertedNumeric) == roundToTheTenthsPlace(studentNumeric) ? "correct" : "incorrect");
        }
    }

    private static void computeTemperature(String[] arr) {
        teacherNumeric = Double.parseDouble(arr[1]);
        studentNumeric = Double.parseDouble(arr[4]);
        // ensure temperature patterns match
        if(!CFKR.contains(arr[2]) || !CFKR.contains(arr[3])) {
            System.out.println("invalid");
        } else {
            switch(arr[2].concat(arr[3])) {
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
                    System.out.println("unknown temperature conversion " + arr[2] + arr[3]);
                    break;
            }
            System.out.println(roundToTheTenthsPlace(convertedNumeric) == roundToTheTenthsPlace(studentNumeric) ? "correct" : "incorrect");
        }
    }

    private static double roundToTheTenthsPlace(double value) {
        return (double)(Math.round(value * 10)) / 10;
    }

    private static String removeExtraSpaces(String text) {
        return text.replaceAll("\\s+", " ").trim();
    }

    private static boolean validateInput(String [] arr) {
        double teacher;
        double student;
        if (arr.length != 5) return false; // check number of user arguments
        if (!"t".equals(arr[0]) && !"v".equals(arr[0])) return false; // high level unit validation
        if (arr[2].equals(arr[3])) return false;  // source and destination must be distinct
        try {
            // validate input for numeric CLI arguments
            teacher = Double.parseDouble(arr[1]);
            student = Double.parseDouble(arr[4]);
        } catch (NumberFormatException badUserData) {
            System.out.println("Invalid numeric input!!! Try again: " + arr[1] + " / " + arr[4]);
            return false;
        }
        // volumes must be more than zero
        if ("v".equals(arr[0]) && (teacher <= 0 || student <= 0)) return false;

        // temperatures must be above theoretical minimums
        if ("t".equals(arr[0])) {
            switch(arr[2]) {
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
                    System.out.println("invalid");
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



