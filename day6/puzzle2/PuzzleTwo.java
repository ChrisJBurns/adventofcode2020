import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PuzzleTwo {

    public static void main(String[] args) throws FileNotFoundException {

        List<List<String>> groupAnswers = getGroupAnswers();

        int number = 0;
        List<Integer> list = new ArrayList<>();
        for (List<String> group : groupAnswers) {
            for(char alphabet = 'a'; alphabet <='z'; alphabet++ ) {
                char finalAlphabet = alphabet;
                boolean answer = group.stream().allMatch(a -> a.contains(String.valueOf(finalAlphabet)));
                if (answer) {
                    number++;
                }
            }
            list.add(number);
            number = 0;
        }

        int total = list.stream()
            .mapToInt(a -> a)
            .sum();

        System.out.printf("Total sum: %d", total);
    }

    private static List<List<String>> getGroupAnswers() throws FileNotFoundException {
        File file = new File("input.txt");
        Scanner scanner = new Scanner(file);

        List<String> groupAnswerList = new ArrayList<>();
        List<List<String>> list = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            if (data.equals("") || !scanner.hasNextLine()) {
                list.add(new ArrayList<>(groupAnswerList));
                groupAnswerList.clear();
            } else {
                groupAnswerList.add(data);
            }
        }
        scanner.close();
        return list;
    }
}
