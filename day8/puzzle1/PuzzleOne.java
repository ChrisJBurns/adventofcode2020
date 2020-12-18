import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PuzzleOne {

    private static class InstructionPair<String, Integer> {
        private final String key;
        private final Integer value;

        public InstructionPair(String key, Integer value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public Integer getValue() {
            return value;
        }
    }

    private static final String ACC = "acc";
    private static final String JMP = "jmp";

    public static void main(String[] args) throws FileNotFoundException {
        List<InstructionPair<String, Integer>> instructions = getInstructions();

        int accumulator = 0;

        List<Integer> completedInstructionIndexes = new ArrayList<>();

        for (int i = 0; i < instructions.size(); i++) {
            if (completedInstructionIndexes.contains(i)) {
                System.out.printf("Accumulator: %d \n", accumulator);
                break;
            }
            completedInstructionIndexes.add(i);
            InstructionPair<String, Integer> instruction = instructions.get(i);

            if (ACC.equals(instruction.getKey())) {
                accumulator += instruction.getValue();
                continue;
            }

            if (JMP.equals(instruction.getKey())) {
                i += instruction.getValue() - 1;
            }
        }
    }

    private static List<InstructionPair<String, Integer>> getInstructions() throws FileNotFoundException {
        File file = new File("input.txt");
        Scanner scanner = new Scanner(file);

        List<InstructionPair<String, Integer>> rules = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String instruction = scanner.nextLine();

            String type = instruction.split("\\s")[0].trim();
            int number = Integer.parseInt(instruction.split("\\s")[1]);

            rules.add(new InstructionPair<>(type, number));
        }
        scanner.close();
        return rules;
    }
}
