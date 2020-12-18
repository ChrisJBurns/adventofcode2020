import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PuzzleTwo {

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
    private static final String NOP = "nop";

    public static void main(String[] args) throws FileNotFoundException {
        List<InstructionPair<String, Integer>> instructions = getInstructions();

        int accumulator = 0;

        for (int i = 0; i < instructions.size(); i++) {
            InstructionPair<String, Integer> instruction = instructions.get(i);

            String type = instruction.getKey();

            if (JMP.equals(type) || NOP.equals(type)) {
                List<InstructionPair<String, Integer>> modifiedList = new ArrayList<>(instructions);
                modifiedList.remove(instruction);

                type = JMP.equals(instruction.getKey()) ? NOP : JMP;
                modifiedList.add(i, new InstructionPair<>(type, instruction.getValue()));

                try {
                    accumulator = getAccumulator(modifiedList);
                    break;
                } catch (RuntimeException ex) {
                    // do nothing as it continues to next index in loop
                }
            }
        }

        System.out.printf("Accumulator: %d \n", accumulator);
    }

    private static int getAccumulator(List<InstructionPair<String, Integer>> modifiedList) {
        int accumulator = 0;

        List<Integer> completedInstructionIndexes = new ArrayList<>();
        for (int i = 0; i < modifiedList.size(); i++) {
            if (completedInstructionIndexes.contains(i)) {
                throw new RuntimeException("Infinite loop");
            }
            completedInstructionIndexes.add(i);

            InstructionPair<String, Integer> instruction = modifiedList.get(i);

            switch (instruction.getKey()) {
                case NOP:
                    break;
                case ACC:
                    accumulator += instruction.getValue();
                    break;
                case JMP:
                    i += instruction.getValue() - 1;
                    break;
            }
        }

        return accumulator;
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
