import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PuzzleTwo {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> timesGrid = getTimesGrid();
        System.out.printf("Answer: %d \n", calculate(timesGrid));
    }

    // stole this from one of the solutions online. my solution would have worked but it took too long
    // to finish (for loop) as puzzle2 on day13 was anchored more around the speed of the solution. apparently
    // the chinese remainder theorem was the answer but i haven't a clue on how it worked so stole the below
    // from freefal
    public static long calculate(List<String> inputs) {
        ArrayList<Long> remainders = new ArrayList<>();
        ArrayList<Long> modulos = new ArrayList<>();
        String[] buses = inputs.get(1).split(",");
        for (int i = 0; i < buses.length; i++) {
            if (buses[i].equals("x"))
                continue;
            long mod = Long.parseLong(buses[i]);
            long rem = -(long)i;
            while (rem < 0)
                rem += mod;
            remainders.add(rem);
            modulos.add(mod);
        }
        long curTest = remainders.get(remainders.size() - 1);
        long inc = modulos.get(modulos.size() - 1);

        for (int i = modulos.size() - 2; i >=0; i--) {
            long mod = modulos.get(i);
            long rem = remainders.get(i);

            while (curTest % mod != rem) {
                curTest += inc;
            }

            inc *= mod;
        }

        return curTest;
    }

    private static List<String> getTimesGrid() throws FileNotFoundException {
        File file = new File("input.txt");
        Scanner scanner = new Scanner(file);

        List<String> times = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            times.add(line);
        }
        return times;
    }
}
