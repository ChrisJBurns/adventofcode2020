import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


class Pair {
    private final int min;
    private final int max;

    public Pair(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}

class PuzzleOne {

  public static void main(String[] args) throws FileNotFoundException {

      List<String> seatList = getSeatList();

      Pair pair = new Pair(0 , 127);
      Pair columnPair = new Pair(0 , 7);

      List<Integer> seatIdList = new ArrayList<>();
      int row = 0;
      int column = 0;

      for (String seat : seatList) {
          for (int i = 0; i < seat.length(); i++) {
              pair = getPartition(pair, Character.toString(seat.charAt(i)));
              if (pair.getMin() == pair.getMax()) {
                  row = pair.getMin();
                  break;
              }
          }

          for (int i = 7; i < seat.length(); i++) {
              columnPair = getPartition(columnPair, Character.toString(seat.charAt(i)));
              if (columnPair.getMin() == columnPair.getMax()) {
                  column = columnPair.getMin();
                  break;
              }
          }

          seatIdList.add((row * 8) + column);
          pair = new Pair(0, 127);
          columnPair = new Pair(0, 7);
      }
      System.out.printf("Highest seatId %s \n", Collections.max(seatIdList));

  }

  private static Pair getPartition(Pair pair, String letter) {
      int min;
      int max;

      if ("F".equals(letter) || "L".equals(letter)) {
          min = pair.getMin();
          max = pair.getMax() - ((pair.getMax() - pair.getMin()) / 2 ) - 1;
      } else {
          min = pair.getMax() - ((pair.getMax() - pair.getMin()) / 2 );
          max = pair.getMax();
      }
      return new Pair(min , max);
  }

  private static List<String> getSeatList() throws FileNotFoundException {

      File file = new File("input.txt");
      Scanner scanner = new Scanner(file);

      List<String> seatList = new ArrayList<>();
      while (scanner.hasNextLine()) {
          seatList.add(scanner.nextLine());
      }
      scanner.close();
      return seatList;
  }
}

