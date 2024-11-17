package com.cdfive.learn.question;

import java.util.*;

/**
(()
=>()
)()())
=>()()
(())(()
=>(())()
 * @author cdfive
 */
public class MatchBrackets2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            Set<Integer> chooseIndexes = new TreeSet<>((a, b) -> Integer.compare(a, b));
            List<Integer> leftBrackets = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);

                if ("(".equals(String.valueOf(c))) {
                    leftBrackets.add(i);
                    continue;
                }

                if (")".equals(String.valueOf(c))) {
                    if (leftBrackets.size() == 0) {
                        continue;
                    }

                    Integer leftIndex = leftBrackets.get(leftBrackets.size() - 1);
                    leftBrackets.remove(leftBrackets.size() - 1);
                    chooseIndexes.add(leftIndex);
                    chooseIndexes.add(i);
                    continue;
                }
            }

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < line.length(); i++) {
                if (chooseIndexes.contains(i)) {
                    result.append(String.valueOf(line.charAt(i)));
                }
            }
            System.out.println(result.toString());
        }
    }
}
