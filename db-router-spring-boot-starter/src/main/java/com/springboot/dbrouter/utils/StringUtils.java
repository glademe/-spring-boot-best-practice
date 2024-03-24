package com.springboot.dbrouter.utils;

/**
 * @author :Jooye
 * @date : 2024-03-24 19:22
 * @Describe: 类的描述信息
 */
public class StringUtils {

    public static String middleScoreToCamelCase(String input) {
        StringBuilder sb = new StringBuilder();
        boolean nextUpperCase = false;
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (currentChar == '-') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    sb.append(Character.toUpperCase(currentChar));
                    nextUpperCase = false;
                } else {
                    sb.append(currentChar);
                }
            }
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        String text="user-info";

        String middled = middleScoreToCamelCase(text);
        System.out.println(middled);
    }
}
