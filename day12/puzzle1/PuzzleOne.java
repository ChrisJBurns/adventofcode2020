import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

class Directions {
    public String move;
    public int value;

    public Directions(String move, int value) {
        this.move = move;
        this.value = value;
    }
}

class PuzzleOne {

    private static final Integer[] position = { 0, 0 };
    private static String direction = "E"; //starting position facing east
    private static final Map<String, Integer> directionsMap = Map.of(
        "N", 0,
        "E", 90,
        "S", 180,
        "W", 270
    );

    public static void main(String[] args) throws FileNotFoundException {
        List<Directions> actions =  getActions();

        for (Directions d : actions) {
            switch (d.move) {
                case "F":
                    move(direction, d);
                    break;
                case "L":
                case "R":
                    direction = calculateDegrees(direction, d.move, d.value);
                    break;
                default:
                    move(d.move, d);
                    break;
            }
        }

        int manhattanDistance = Math.abs(position[0]) + Math.abs(position[1]);
        System.out.printf("Manhattan Distance: %s \n", Integer.valueOf(manhattanDistance).toString());

    }

    private static void move(String d, Directions directions) {
        switch (d) {
            case "N":
                position[0] += directions.value;
                break;
            case "E":
                position[1] += directions.value;
                break;
            case "S":
                position[0] -= directions.value;
                break;
            case "W":
                position[1] -= directions.value;
                break;
            default:
                position[0] = 0;
                position[1] = 0;
                break;
        }
    }

    private static String calculateDegrees(String startingDirection, String leftOrRight, int degrees) {
        int position = directionsMap.get(startingDirection);

        AtomicInteger r = new AtomicInteger(0);
        if (leftOrRight.equals("L")) {
            r.getAndSet(position - degrees);
            if (r.get() < 0) r.getAndSet(360 + r.get());
        }

        if (leftOrRight.equals("R")) {
            r.getAndSet(position + degrees);
            if (r.get() > 360) r.getAndSet(Math.abs(360 - r.get()));
            if (r.get() == 360) r.getAndSet(0);
        }

        return directionsMap.entrySet().stream().filter(k -> k.getValue().equals(r.get())).map(Entry::getKey).findFirst().orElse(null);
    }

    private static List<Directions> getActions() throws FileNotFoundException {
        File file = new File("input.txt");
        Scanner scanner = new Scanner(file);

        List<Directions> actions = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] directions = line.split("(?<=\\D)(?=\\d)");
            actions.add(new Directions(directions[0], Integer.parseInt(directions[1])));
        }
        return actions;
    }
}
