import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class Pair {

    public int getTurn() {
        return turn;
    }

    int turn;
    int spokenNumber;

    public Pair(int turn, int spokenNumber) {
        this.turn = turn;
        this.spokenNumber = spokenNumber;
    }
}

class PuzzleOne {

    public static void main(String[] args) throws FileNotFoundException {
        List<Integer> lines =  getFile();

        List<Pair> spoken = new ArrayList<>();
        AtomicInteger finalTurn = new AtomicInteger(0);
        AtomicInteger finalLastSpokenNumber = new AtomicInteger(0);

        for (Integer i : lines) {
            finalTurn.getAndIncrement();
            spoken.add(new Pair(finalTurn.get(), i));
            finalLastSpokenNumber.getAndSet(i);
        }

        while(true) {
            long count = spoken.stream().filter(pair -> pair.spokenNumber == finalLastSpokenNumber.get()).count();

            if (count == 1) {
                finalLastSpokenNumber.getAndSet(0);
                spoken.add(new Pair(finalTurn.incrementAndGet(), finalLastSpokenNumber.get()));
                continue;
            }

            if (count > 1) {
                List<Pair> tempSpoken = spoken.stream()
                    .filter(pair -> pair.spokenNumber == finalLastSpokenNumber.get())
                    .sorted(Comparator.comparingInt(Pair::getTurn).reversed())
                    .collect(Collectors.toList());
                tempSpoken = tempSpoken.subList(0, 2);
                finalLastSpokenNumber.getAndSet(tempSpoken.get(0).turn - tempSpoken.get(1).turn);
                spoken.add(new Pair(finalTurn.incrementAndGet(), finalLastSpokenNumber.get()));
            }

            if (finalTurn.get() == 2020) {
                System.out.printf("Last spoken number: %s \n", finalLastSpokenNumber.get());
                break;
            }
        }
    }

    private static List<Integer> getFile() throws FileNotFoundException {
        File file = new File("/Users/chrisburns/Library/Application Support/JetBrains/IntelliJIdea2020.2/scratches/input.txt");
        Scanner scanner = new Scanner(file);

        List<Integer> times = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            times.add(Integer.parseInt(line));
        }
        return times;
    }
}