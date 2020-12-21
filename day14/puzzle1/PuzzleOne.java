import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PuzzleOne {

    static final Pattern memoryAddressPattern = Pattern.compile("(?!\\[)\\d(?=\\])");
    static String mask = "";

    public static void main(String[] args) throws FileNotFoundException {
        List<String> lines =  getFile();

        Map<String, Long> map = new HashMap<>();

        for (String line : lines) {
            if (line.contains("mask")) {
                mask = line.split("=")[1].trim();
                continue;
            }

            if (line.contains("mem")) {
                Matcher matcher = memoryAddressPattern.matcher(line);
                if (matcher.find()) {
                    String key = matcher.group();
                    String binaryRep = getBinary(line.split("=")[1].trim());
                    long newNum = Long.parseLong(maskBinary(mask, binaryRep), 2);
                    map.put(key, newNum);
                }
            }
        }

        long sum = map.values().stream().mapToLong(i -> i).sum();
        System.out.printf("Answer: %d", sum);
    }

    private static String maskBinary(String mask, String binaryRep) {
        StringBuilder newString = new StringBuilder();

        for (int i = 0; i < binaryRep.length(); i++) {
            if (mask.charAt(i) == 'X') {
                newString.append(binaryRep.charAt(i));
                continue;
            }

            if (mask.charAt(i) == '0' || mask.charAt(i) == '1') {
                newString.append(mask.charAt(i));
            }
        }

        return newString.toString();
    }

    private static String getBinary(String num) {
        String result = Long.toBinaryString(Long.parseLong(num));
        return String.format("%36s", result).replaceAll(" ", "0");
    }

    private static List<String> getFile() throws FileNotFoundException {
        File file = new File("input.txt");
        Scanner scanner = new Scanner(file);

        List<String> times = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            times.add(line);
        }
        return times;
    }
}
// 323658420454
// 12512013221615