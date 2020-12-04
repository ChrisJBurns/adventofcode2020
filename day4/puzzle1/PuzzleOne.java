import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PuzzleOne {
  public static void main(String[] args) throws FileNotFoundException {

      List<String> passportList = getAndCreatePassportList();

      int validPassportCounter = 0;
      for (String passport : passportList) {
          if (passport.contains("byr:")
              && passport.contains("iyr:")
              && passport.contains("eyr:")
              && passport.contains("hgt:")
              && passport.contains("hcl:")
              && passport.contains("ecl:")
              && passport.contains("pid:")
          ) {
              validPassportCounter++;
          }
      }

      System.out.printf("Number of valid passports: %s", validPassportCounter);

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

