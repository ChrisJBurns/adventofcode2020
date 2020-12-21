import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/** this could also be the puzzleOne solution, but it had to be faster and I didn't want to change
 * the puzzleOne solution as it would be wrong of me to claim I used the below solution originally.
 * instead i had to rethink the way the algorithm had to work which is the what the below is the end
 * result of.
 */
class PuzzleTwo {

    public static void main(String[] args) throws FileNotFoundException {
        List<Integer> lines =  getFile();

        Map<Integer, Integer> spokenNumbers = new HashMap<>();

        int turn = 0;
        for (; turn < lines.size() - 1; turn++) {
            spokenNumbers.put(lines.get(turn), turn + 1);
        }

        int latestNumber = lines.get(turn++);
        int result = latestNumber;
        for (; turn <= 30000000; turn++) {
            int newSpokenNumber =  spokenNumbers.containsKey(latestNumber) ?  turn - spokenNumbers.get(latestNumber) : 0;
            spokenNumbers.put(latestNumber, turn);
            result = latestNumber;
            latestNumber = newSpokenNumber;
        }

        System.out.printf("Result: %s \n", result);
    }

    private static List<Integer> getFile() throws FileNotFoundException {
        File file = new File("input.txt");
        Scanner scanner = new Scanner(file);

        List<Integer> times = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            times.add(Integer.parseInt(line));
        }
        return times;
    }
}