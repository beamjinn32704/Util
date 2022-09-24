/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.util;

/**
 *
 * @author beamj
 */
public class StringUtil {

    public static String nextAlpha(String text) {
        return nextAlpha(text, 0);
    }

    public static String nextAlpha(String text, int start) {
        for (int i = start; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isAlphabetic(c)) {
                return c + "";
            }
        }
        return null;
    }

    public static String toFirstUpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * This function returns how many times a certain string str2 appears in the
     * string str1
     *
     * @param str1 the string to check
     * @param str2 the string to look for.
     * @return how many times c appears in str. For example, if str1 is "Hello
     * There Peepers" and str2 is "er", than this function will return 2.
     */
    public static int numOfTimes(String str1, String str2) {
        boolean go = true;
        int index = 0;
        int counter = 0;
        while(go){
            int locationInd = str1.indexOf(str2, index);
            if(locationInd != -1){
                counter++;
                index = locationInd + 1;
            } else {
                go = false;
            }
        }
        return counter;
    }
}
