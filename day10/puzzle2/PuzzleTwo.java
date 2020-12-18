import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class PuzzleTwo {

    public static Map<Integer, Long> countsMap = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        List<Integer> joltageRatings =  getJoltageRatings();

        long count = countArrangements(joltageRatings, 0);

        System.out.printf("Result: %d \n", count);
    }

    public static long countArrangements(List<Integer> joltageRatings, int index) {
        if (index == joltageRatings.size() - 1) {
            return 1;
        }
        if (countsMap.containsKey(index)) {
            return countsMap.get(index);
        }
        long count = 0;
        for (int i = index + 1; i < joltageRatings.size(); i++) {
            if (joltageRatings.get(i) - joltageRatings.get(index) > 3) {
                break;
            }
            count += countArrangements(joltageRatings, i);
        }
        countsMap.put(index, count);
        return count;
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
