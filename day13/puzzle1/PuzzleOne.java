import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PuzzleOne {

    private static int earliestTime = 0;

    public static void main(String[] args) throws FileNotFoundException {
        List<String> timesGrid =  getTimesGrid();

        int answer = 0;
        int time = earliestTime;

        boolean notFound = true;
        while (notFound) {
            for (String busId : timesGrid) {
                if (time % Integer.parseInt(busId) == 0) {
                    answer = (time - earliestTime) * Integer.parseInt(busId);
                    notFound = false;
                    break;
                }
            }
            time++;
        }

        System.out.printf("Answer: %d \n", answer);
    }

    private static List<String> getTimesGrid() throws FileNotFoundException {
        File file = new File("input.txt");
        Scanner scanner = new Scanner(file);

        List<String> times = new ArrayList<>();
        boolean first = true;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (first) {
                earliestTime = Integer.parseInt(line);
                first = false;
                continue;
            }

            String[] ids = line.split(",");
            for (String id : ids) {
                if (!id.equals("x")) {
                    times.add(id);
                }
            }
        }
        return times;
    }
}
