import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PuzzleTwo {
    public static void main(String[] args) throws FileNotFoundException {

        List<String> passportList = getAndCreatePassportList();

        int validPassportCounter = 0;
        for (String passport : passportList) {
            if (validBirthYear(passport)
                && validIssueYear(passport)
                && validExpirationYear(passport)
                && validHeight(passport)
                && validHairColor(passport)
                && validEyeColor(passport)
                && validPid(passport)
            ) {
                validPassportCounter++;
            }
        }

        System.out.printf("Number of valid passports: %s", validPassportCounter);
    }

    private static boolean validPid(String passport) {
        Pattern pid = Pattern.compile("(?<=pid:)[0-9]{9}(?!\\d)");
        Matcher pidM = pid.matcher(passport);
        return pidM.find();
    }

    private static boolean validEyeColor(String passport) {
        Pattern ecl = Pattern.compile("(?<=ecl:)(amb|blu|brn|gry|grn|hzl|oth){1}");
        Matcher eclM = ecl.matcher(passport);
        return eclM.find();
    }

    private static boolean validHairColor(String passport) {
        Pattern hcl = Pattern.compile("(?<=hcl:)#([0-9a-f]{6})(?!(\\d|\\w))");

        Matcher hclM = hcl.matcher(passport);
        return hclM.find();
    }

    private static boolean validHeight(String passport) {
        Pattern cm = Pattern.compile("(?<=hgt:)([0-9]*)(?=cm)");
        Pattern in = Pattern.compile("(?<=hgt:)([0-9]*)(?=in)");

        Matcher cmM = cm.matcher(passport);
        if (cmM.find()) {
            int heightCm = Integer.parseInt(cmM.group());
            return heightCm >= 150 && heightCm <= 193;
        }

        Matcher inM = in.matcher(passport);
        if (inM.find()) {
            int heightIn = Integer.parseInt(inM.group());
            return heightIn >= 59 && heightIn <= 76;
        }
        return false;
    }

    private static boolean validExpirationYear(String passport) {
        Pattern p = Pattern.compile("(?<=eyr:)([0-9]{4})(?!\\d)");
        Matcher m = p.matcher(passport);
        if (m.find()) {
            int year = Integer.parseInt(m.group());
            return year >= 2020 && year <= 2030;
        }
        return false;
    }

    private static boolean validIssueYear(String passport) {
        Pattern p = Pattern.compile("(?<=iyr:)([0-9]{4})(?!\\d)");
        Matcher m = p.matcher(passport);
        if (m.find()) {
            int year = Integer.parseInt(m.group());
            return year >= 2010 && year <= 2020;
        }
        return false;
    }

    private static boolean validBirthYear(String passport) {
        Pattern p = Pattern.compile("(?<=byr:)([0-9]{4})(?!\\d)");
        Matcher m = p.matcher(passport);
        if (m.find()) {
            int year = Integer.parseInt(m.group());
            return year >= 1920 && year <= 2002;
        }
        return false;
    }

    private static List<String> getAndCreatePassportList() throws FileNotFoundException {

        File file = new File("input.txt");
        Scanner scanner = new Scanner(file);

        // not ideal hardcoding of array size. can easily use List<char[]>, but im lazy.
        List<String> passportList = new ArrayList<>();
        String passport = "";
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            passport = passport + " " + data;
            if (data.equals("") || !scanner.hasNextLine()) {
                passportList.add(passport);
                passport = "";
            }
        }
        scanner.close();
        return passportList;
    }
}

