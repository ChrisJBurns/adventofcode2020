import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PuzzleOne {

    public static void main(String[] args) throws FileNotFoundException {
        List<Long> numbers = getNumbers();

        long invalidNumber = getInvalidNumber(numbers);

        System.out.printf("Found number: %d \n", invalidNumber);
    }

    private static long getInvalidNumber(List<Long> numbers) {
        List<Long> numbersForUse = new ArrayList<>();
        long invalidNumber = 0;
        for (int i = 25; i < numbers.size(); i++) {
            for (int j = 25; j > 0; j--) {
                numbersForUse.add(numbers.get(i - j));
            }

            long n = numbers.get(i);
            boolean found = true;

            main:
            for (int k = 0; k < numbersForUse.size(); k++) {
                for (int j = 0; j < numbersForUse.size(); j++) {

                    long x = numbersForUse.get(k);
                    long y = numbersForUse.get(j);

                    if (x == y) {
                        continue;
                    }

                    long result = x + y;

                    if (result == n) {
                        numbersForUse.clear();
                        found = false;
                        break main;
                    }
                }
            }

            if (found) {
                invalidNumber = n;
                break;
            }
        }
        return invalidNumber;
    }

    private static List<Long> getNumbers() throws FileNotFoundException {
        File file = new File("input.txt");
        Scanner scanner = new Scanner(file);

        List<Long> numbers = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String number = scanner.nextLine();
            numbers.add(Long.valueOf(number));
        }
        scanner.close();
        return numbers;
    }
}
