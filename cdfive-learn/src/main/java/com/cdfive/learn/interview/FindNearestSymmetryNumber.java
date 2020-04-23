package com.cdfive.learn.interview;

import java.util.Scanner;

/**
 * 2020-03-19
 * 输入一个正整数,找距离最近的对称数,如果距离相等的有多个以逗号分隔从小到大打印
 *
 * 例:
 * input:  103
 * output: 101
 *
 * @author cdfive
 */
public class FindNearestSymmetryNumber {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            int num = in.nextInt();
            process(num);
        }
    }

    public static void process(int curNum) {
        if (curNum <= 0) {
            System.out.println("ERROR");
            return;
        }

        int prevNum = curNum;
        int nextNum = curNum;
        while (prevNum > 0) {
            prevNum--;

            if (nextNum < Integer.MAX_VALUE - 1) {
                nextNum++;
            }

            if (checkNum(prevNum)) {
                System.out.print(prevNum);
                if (checkNum(nextNum)) {
                    System.out.print("," + nextNum);
                }
                System.out.println();
                return;
            }

            if (checkNum(nextNum)) {
                System.out.println(nextNum);
                return;
            }
        }
    }

    public static boolean checkNum(int num) {
        String numStr = String.valueOf(num);

        for (int i = 0; i < numStr.length(); i++) {
            if (numStr.charAt(i) != numStr.charAt(numStr.length() - 1 - i)) {
                return false;
            }
        }

        return true;
    }
}
