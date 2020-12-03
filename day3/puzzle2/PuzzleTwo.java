import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Traverse {
  private final Move move;
  private final char[][] array;

  public Traverse(Move move, char[][] array) {
    this.move = move;
    this.array = array;
  }

  public long traverseSlope() {

    int column = 0;
    long treeCount = 0;

    for (int i = 0; i < array.length; i = i + move.getDown()) {
      if (i + move.getDown() >= array.length) {
        break;
      }
      column = column + move.getRight(); // increase by right

      // handle the looping back to the beginning of the array
      if (column >= array[i].length) {
        column = column - array[i].length;
      }

      // get the character at the next line down
      char a = array[i + move.getDown()][column];
      if (a == '#') {
        treeCount++;
      }
    }

    System.out.printf("Tree count %s \n", treeCount);
    return treeCount;
  }
}

class Move {
  private final int right;
  private final int down;

  public Move(int right, int down) {
    this.right = right;
    this.down = down;
  }

  public int getRight() {
    return right;
  }

  public int getDown() {
    return down;
  }
}

class PuzzleTwo {
  public static void main(String[] args) throws FileNotFoundException {
    char[][] array = getAndCreateGraph();

    long treeCountSlope1 = new Traverse(new Move(1, 1), array).traverseSlope();
    long treeCountSlope2 = new Traverse(new Move(3, 1), array).traverseSlope();
    long treeCountSlope3 = new Traverse(new Move(5, 1), array).traverseSlope();
    long treeCountSlope4 = new Traverse(new Move(7, 1), array).traverseSlope();
    long treeCountSlope5 = new Traverse(new Move(1, 2), array).traverseSlope();

    long totalTrees = treeCountSlope1 * treeCountSlope2 * treeCountSlope3 * treeCountSlope4 * treeCountSlope5;
    System.out.printf("Total tree count: %d", totalTrees);
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
      for (int i = 0; i < data.length(); i++) {
        a[i] = data.charAt(i);
      }
      array[count] = a;
      count++;
    }
    scanner.close();
    return array;
  }
}

