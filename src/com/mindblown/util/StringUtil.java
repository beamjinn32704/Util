/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author beamj
 */
public class StringUtil {

    /**
     * Returns the first alphabetic character in the string
     * @param text the string to look in
     * @return the first alphabetic character in the string. This will be null if there 
     * are no alphabetic characters in the text string
     */
    public static String nextAlpha(String text) {
        return nextAlpha(text, 0);
    }

    /**
     * Returns the first alphabetic character in the string given, starting from 
     * the index given (start).
     * @param text the string to look in
     * @param start the index to start looking at
     * @return the first alphabetic character in the string (starting at index start). This will be null if there 
     * are no alphabetic characters in the text string (starting at index start).
     */
    public static String nextAlpha(String text, int start) {
        for (int i = start; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isAlphabetic(c)) {
                return c + "";
            }
        }
        return null;
    }

    /**
     * Sets the first character in the string (regardless if number, alphabetical, etc.) 
     * to upper case. This will only have an effect if the first character in the string 
     * is a letter (alphabetical character).
     * @param str the string to modify
     * @return the string with the first character in the string set to upper case.
     */
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
    
    /**
     * This function is used to fully replace all instances of targets in a string. 
     * Sometimes targets it is wanted to turn all doubles to singles (like all // 's to / 's). 
     * In these cases, however, sometimes there can be a triple slash, leaving a double slash at 
     * the end. This function gets rid of those extra left-behind instances.
     * 
     * @param str The string to have all target instances replaced with the replacement.
     * @param target What needs to be replaced in str.
     * @param replacement What each target needs to be replaced with.
     * @return 
     */
    public static String fullyReplace(String str, String target, String replacement){
        String string = str + "";
        //If the target and the replacement are the same, it would lead to an infinite loop.
        if(replacement.equals(target)){
            return string;
        }
        //While the string contains the target (ex: target = //, replacement = /, and there's a ///)
        //, remove replace it.
        while(string.contains(target)){
            string = string.replace(target, replacement);
        }
        return string;
    }
    
    /**
     * This function is used to fully replace all instances of regexes in a string. 
     * Sometimes targets it is wanted to turn all doubles to singles (like all // 's to / 's). 
     * In these cases, however, sometimes there can be a triple slash, leaving a double slash at 
     * the end. This function gets rid of those extra left-behind instances.
     * @param str The string with all target instances of regexes that need to be replaced.
     * @param regex The regex that defines the things that need to be replaced.
     * @param replacement
     * @return 
     */
    public static String fullyReplaceRegex(String str, String regex, String replacement){
        String workingWith = str + "";
        //Create a matcher which matches parts of a string to a regex
        Matcher matcher = Pattern.compile(regex).matcher(workingWith);
        //While the matcher finds an instance of the regex, replace all.
        while(matcher.find()){
            workingWith = matcher.replaceAll(replacement);
            //If what the regex defines is equivalent to the replacement, then it would lead to an infinite loop.
            if(workingWith.equals(str)){
                return workingWith;
            }
            matcher = Pattern.compile(regex).matcher(workingWith);
        }
        return workingWith;
    }
    
    /**
     * This function splits string with the literal string quote. In 
     * string.split(param), the param needs to be a regex. This function 
     * uses the same method except converts quote into a regex form of the literal quote. 
     * Basically, if you use this method with the params "123.244.122.2" and ".", this method 
     * will return [123, 244, 122, 2] . This method works in the same way as string.split
     * @param string the string that needs to be split
     * @param quote the literal string that the string needs to be split by
     * @return the string split by the quote
     */
    public static String[] split(String string, String quote){
        return string.split(Pattern.quote(quote));
    }
}
