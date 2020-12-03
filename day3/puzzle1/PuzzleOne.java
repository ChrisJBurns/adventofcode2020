import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class PuzzleOne {

    public static void main(String[] args) throws FileNotFoundException {
        char[][] array = getAndCreateGraph();

        int column = 0;
        int treeCount = 0;

        for(int i = 0; i < array.length; ++i) {
            if (i == array.length - 1) {
                break;
            }
            column = column + 3; // increase by 3

            // handle the looping back to the beginning of the array
            if (column >= array[i].length) {
                column = column - array[i].length;
            }

            // get the character at the next line down
            char a = array[i + 1][column];
            if (a == '#') {
                treeCount++;
            }
        }

        System.out.printf("Tree count %s", treeCount);
    }

    private static char[][] getAndCreateGraph() throws FileNotFoundException {
        File file = new File("input.txt");
        Scanner scanner = new Scanner(file);

        // not ideal hardcoding of array size. can easily use List<char[]>, but im lazy.
        char[][] array = new char[323][];
        int count = 0;
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            char[] a = new char[data.length()];
            for(int i = 0; i < data.length(); i++) {
                a[i] = data.charAt(i);
            }
            array[count] = a;
            count++;
        }
        scanner.close();
        return array;
    }
}

