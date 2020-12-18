import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class PuzzleOne {

    public static void main(String[] args) throws FileNotFoundException {
        List<Integer> joltageRatings =  getJoltageRatings();

        int oneVoltDifferenceCounter = 0;
        int threeVoltDifferenceCounter = 0;

        for (int i = 0; i < joltageRatings.size() - 1; i++) {
            Integer firstJoltageRating = joltageRatings.get(i);
            Integer secondJoltageRating = joltageRatings.get(i + 1);

            if ((secondJoltageRating - firstJoltageRating) == 1) {
                oneVoltDifferenceCounter++;
            } else {
                threeVoltDifferenceCounter++;
            }
        }

        System.out.printf("Result: %d \n", oneVoltDifferenceCounter * threeVoltDifferenceCounter);
    }

    private static List<Integer> getJoltageRatings() throws FileNotFoundException {
        File file = new File("input.txt");
        Scanner scanner = new Scanner(file);

        List<Integer> numbers = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String number = scanner.nextLine();
            numbers.add(Integer.valueOf(number));
        }
        scanner.close();

        // add the default joltage rating for seat
        numbers.add(0);

        // add the device build-in joltage rating (3 ratings above highest joltage rating in the set)
        numbers.add(Collections.max(numbers) + 3);

        Collections.sort(numbers);
        return numbers;
    }
}
