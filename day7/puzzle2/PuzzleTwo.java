import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PuzzleTwo {

    private static final Pattern BAG = Pattern.compile("\\w*\\s\\w*\\s(?=(bag|bags) contain)");
    private static final Pattern CHILD_BAG = Pattern.compile("(?<=contain\\s)(.*)(?=.)");

    public static void main(String[] args) throws FileNotFoundException {

        List<String> rules = getRules();

        Map<String, List<Map<String, String>>> bagRuleMap = getBagList(rules);

        long count = getCount(bagRuleMap, "shiny gold");

        System.out.printf("Count: %s", count - 1);
    }

    private static long getCount(Map<String, List<Map<String, String>>> bagRuleMap, String bagName) {
        AtomicLong count = new AtomicLong(1);
        bagRuleMap.entrySet().stream()
            .filter(e -> e.getKey().equals(bagName))
            .forEach(entry -> {
                entry.getValue().forEach(
                    e -> e.forEach((key, value) -> count.getAndAdd(Integer.parseInt(value) * getCount(bagRuleMap, key)))
                );
            });
        return count.get();
    }

    /**
     * builds a map of the bags and their child bags. each bag has a list of child bags and their
     * counts or has no child bags at all. this makes it easier to query due to it representing a
     * data object
     * @param rules
     * @return
     */
    private static Map<String, List<Map<String, String>>> getBagList(List<String> rules) {
        Map<String, List<Map<String, String>>> bagsMap = new HashMap<>();

        rules.forEach(
            rule -> {
                List<Map<String, String>> childBagMapList = new ArrayList<>();

                Matcher childBagMatcher = CHILD_BAG.matcher(rule);
                if (childBagMatcher.find()) {
                    String childBagRule = childBagMatcher.group();

                    if (!childBagRule.contains("no other bags")) {
                        List<String> childBagRules = Arrays
                            .asList(childBagRule.split("(bag|bags)(,|$)"));

                        childBagRules.forEach(
                            childRule -> {
                                // splits the number and colour of the bag into two separate strings
                                String[] parts2 = childRule.trim().split("(?<=[0-9])");
                                childBagMapList.add(Map.of(
                                    parts2[1].trim(), parts2[0].trim()));
                            }
                        );
                    }

                    Matcher parentBagMatcher = BAG.matcher(rule);
                    if (parentBagMatcher.find()) {
                        bagsMap.put(parentBagMatcher.group().trim(), childBagMapList);
                    }
                }
            }
        );

        return bagsMap;
    }

    private static List<String> getRules() throws FileNotFoundException {
        File file = new File("input.txt");
        Scanner scanner = new Scanner(file);

        List<String> rules = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            rules.add(data);
        }
        scanner.close();
        return rules;
    }
}
