package com.cdfive.learn.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author cdfive
 */
public class MatchBrackets {


    public static void main(String[] args) {
        testMatch("123", true);
        testMatch("(123", false);
        testMatch("(123)", true);
        testMatch("(123))", false);
        testMatch("(12)3", true);
        testMatch("(12)[3", false);
        testMatch("(12)[]3", true);
        testMatch("(1(2))[aa]b3", true);
        testMatch("(1(2)[)aa]b3", false);
        testMatch("(1(2)])aa[b3", false);
        testMatch("(1(2)[cd])abd", true);
    }

    public static Map<Character, Character> BRACKET_MAP = new HashMap<Character, Character>() {{
        put(')', '(');
        put(']', '[');
        put('}', '{');
    }};

    public static boolean match(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (BRACKET_MAP.values().contains(ch)) {
                stack.push(ch);
            } else if (BRACKET_MAP.keySet().contains(ch)) {
                if (stack.isEmpty()) {
                    return false;
                }

                Character chPop = stack.pop();
                if (!chPop.equals(BRACKET_MAP.get(ch))) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    public static void testMatch(String str, boolean expected) {
        boolean actual = match(str);
        if (actual == expected) {
            System.out.println(String.format("%s,expected=%s,actual=%s", str, expected, actual));
        } else {
            System.err.println(String.format("%s,expected=%s,actual=%s", str, expected, actual));
        }
    }
}
