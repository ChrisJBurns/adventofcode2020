import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Directions {
    public String move;
    public int value;

    public Directions(String move, int value) {
        this.move = move;
        this.value = value;
    }
}

class PuzzleTwo {

    private static final Integer[] waypointPosition = { 1, 10 };
    private static final Integer[] position = { 0, 0 };

    public static void main(String[] args) throws FileNotFoundException {
        List<Directions> actions =  getActions();

        for (Directions d : actions) {
            switch (d.move) {
                case "F":
                    moveShipToWaypoint(d);
                    break;
                case "L":
                case "R":
                    calculateDegrees(d);
                    break;
                default:
                    moveWaypoint(d.move, d);
                    break;
            }
        }

        int manhattanDistance = Math.abs(position[0]) + Math.abs(position[1]);
        System.out.printf("Manhattan Distance: %s \n", Integer.valueOf(manhattanDistance).toString());

    }

    private static void moveShipToWaypoint(Directions directions) {
        position[0] += waypointPosition[0] * directions.value;
        position[1] += waypointPosition[1] * directions.value;
    }

    private static void moveWaypoint(String d, Directions directions) {
        switch (d) {
            case "N":
                waypointPosition[0] += directions.value;
                break;
            case "E":
                waypointPosition[1] += directions.value;
                break;
            case "S":
                waypointPosition[0] -= directions.value;
                break;
            case "W":
                waypointPosition[1] -= directions.value;
                break;
            default:
                waypointPosition[0] = 0;
                waypointPosition[1] = 0;
                break;
        }
    }

    private static void calculateDegrees(Directions d) {
        int rl = d.move.equals("R") ? 1 : -1;
        while (d.value > 0) {
            int tempX = waypointPosition[1];
            waypointPosition[1] = waypointPosition[0] * rl;
            waypointPosition[0] = tempX * -rl;
            d.value -= 90;
        }
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
