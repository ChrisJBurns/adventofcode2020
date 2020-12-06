import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class PuzzleOne {

  public static void main(String[] args) throws FileNotFoundException {

      List<String> groupAnswers = getGroupAnswers();

      List<Integer> answersList = new ArrayList<>();
      Map<Character, String> answers = new HashMap<>();
      for (String groupAnswer : groupAnswers) {
          for (int i = 0; i < groupAnswer.length(); i++) {
              if (!answers.containsKey(groupAnswer.charAt(i))) {
                  answers.put(groupAnswer.charAt(i), "");
              }
          }

          answersList.add(answers.size());
          answers.clear();
      }

      int total = answersList.stream()
          .mapToInt(a -> a)
          .sum();

      System.out.printf("Total sum: %d", total);
  }

  private static List<String> getGroupAnswers() throws FileNotFoundException {
      File file = new File("input.txt");
      Scanner scanner = new Scanner(file);

      List<String> groupAnswerList = new ArrayList<>();
      String answers = "";
      while (scanner.hasNextLine()) {
          String data = scanner.nextLine();
          answers = answers + data;
          if (data.equals("") || !scanner.hasNextLine()) {
              groupAnswerList.add(answers);
              answers = "";
          }
      }
      scanner.close();
      return groupAnswerList;
  }
}
