import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class PuzzleTwo {

    static Integer[][] directions = {{-1, -1}, {-1, 0}, {0, -1}, {1, -1}, {-1, 1}, {1, 1}, {1, 0}, {0, 1}};

    public static void main(String[] args) throws FileNotFoundException {
        char[][] twoDArray =  getAndCreateGraph();

        System.out.printf("Total occupied seats: %s \n", countOccupiedSeats(eval(twoDArray)));
    }

    private static int countOccupiedSeats(char[][] copyArray) {
        int occupiedSeatCounter = 0;
        for (int i = 0; i < copyArray.length; i++) {
            for (int j = 0; j < copyArray[i].length; j++) {
                if (copyArray[i][j] == '#') {
                    occupiedSeatCounter++;
                }
            }
        }
        return occupiedSeatCounter;
    }

    private static char[][] eval(char[][] twoDArray) {
        boolean changed = false;

        char[][] copyArray = Arrays.stream(twoDArray).map(char[]::clone).toArray(char[][]::new);

        for (int i = 1; i < twoDArray.length - 1; i++) {
            for (int j = 1; j < twoDArray[i].length - 1; j++) {

                if (twoDArray[i][j] == 'L') {
                    if (adjacentSeatCounter(twoDArray, i, j) == 0) {
                        copyArray[i][j] = '#';
                        changed = true;
                    }
                    continue;
                }

                if (twoDArray[i][j] == '#') {
                    if (adjacentSeatCounter(twoDArray, i, j) >= 5) {
                        copyArray[i][j] = 'L';
                        changed = true;
                        continue;
                    }
                }
            }
        }

        if (changed) {
            copyArray = eval(copyArray);
        }
        return copyArray;
    }

    private static int counter(char[][] copyArray, int i, int j, int x, int y) {
        int adjacentSeatCount = 0;

        i += x;
        j += y;

        if (copyArray[i][j] == '#') {
            adjacentSeatCount++;
        } else if (copyArray[i][j] == '.') {
            adjacentSeatCount += counter(copyArray, i, j, x, y);
        }
        return adjacentSeatCount;
    }

    private static int adjacentSeatCounter(char[][] copyArray, int i, int j) {
        // checks top-left diagonal
        int adjacentSeatCount = 0;

        for (Integer[] direction : directions) {
            adjacentSeatCount += counter(copyArray, i, j, direction[0], direction[1]);
        }

        return adjacentSeatCount;
    }

    /**
     * gets file and creates a multi-dimensional array that has 0's on the boundaries for easier processing
     * for edges and corners
     *
     * e.g.
     *
     * instead of:
     *      L . L
     *      L L .
     *      L L L
     *
     * it becomes:
     *
     *      0 0 0 0 0
     *      0 L . L 0
     *      0 L L . 0
     *      0 L L L 0
     *      0 0 0 0 0
     * @return 2d array of the seats
     * @throws FileNotFoundException
     */
    private static char[][] getAndCreateGraph() throws FileNotFoundException {
        File file = new File("input.txt");
        Scanner scanner = new Scanner(file);

        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }

        // create 2d array that has same length as input line count
        // we also create two additional rows at start and end of array to pad the bounds
        int columnCount = lines.get(0).length() + 2;
        char[][] chars = new char[lines.size() + 2][columnCount];

        for (int i = 0; i < chars.length; i++) {
            char[] a = new char[columnCount];

            // fill the top and bottom row with 0's
            if (i == 0 || i == chars.length - 1) {
                for (int k = 0; k < columnCount; k++) {
                    a[k] = '0';
                    chars[i] = a;
                }
                continue;
            }

            String string = lines.get(i - 1);
            for(int j = 0; j < a.length; j++) {
                if (j == 0 || j == a.length - 1) {
                    a[j] = '0';
                    chars[i] = a;
                    continue;
                }
                a[j] = string.charAt(j - 1);
                chars[i] = a;
            }
        }
        return chars;
    }
}
