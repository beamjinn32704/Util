/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.util;

/**
 *
 * @author beamj
 */
public class NumUtil {
    /**
     * Takes a number and gets rid of a certain number of decimals in the number 
     * until there are a maximum of maxDec decimals left in the number. No rounding occurs.
     * @param num the number to modify
     * @param maxDec the max number of decimals to be left over in the number. 
     * For example, if num is 123.456 and maxDec is 2, the number returned is 123.45 . 
     * If on the other hand maxDec is 5, the number returned is 123.456.
     * @return num modified to have a maximum of maxDec decimals
     */
    public static double removeDecimal(double num, int maxDec){
        String rep = Double.toString(num);
        //Find the # of decimals
        if(rep.length() - (rep.lastIndexOf(".") + 1) <= maxDec){
            return num;
        }
        //123.45
        //(int)(123.45 * (maxDec * 10)) = 1234 / (maxDec * 10)
        double factor = Math.pow(10, maxDec);
        //Transitions the decimals to keep to be not decimals, gets rid of the remaining 
        //decimals by turning the number to an int, and then moving the decimals back to 
        //the decimal place
        return (double)((int) (num * factor)) / factor;
    }
    
    /**
     * Maps a number num within a range of numbers (numMin to numMax) to another range of numbers 
     * (mappedNumMin to mappedNumMax). For ex, if the num given is 0, and numMin and numMax are -50 
     * and 50, respectively, and mappedNumMin and mappedNumMax are 100 and 300, than the returned 
     * number is 200 (since 0 is at the center between -50 and 50, and 200 is at the center between 100 
     * and 300).
     * @param num The number to map
     * @param numMin The minimum number of the range num is located in
     * @param numMax The maximum number of the range num is located in
     * @param mappedNumMin The minimum number of the range num will be located to
     * @param mappedNumMax The maximum number of the range num will be located to
     * @return num mapped to in between mappedNumMin and mappedNumMax
     */
    public static double map(double num, double numMin, double numMax, double mappedNumMin, double mappedNumMax){
        assert num >= numMin && num <= numMax;
        double min1 = numMin;
        double max1 = numMax;
        double min2 = mappedNumMin;
        double max2 = mappedNumMax;
        return ((num - min1) * (max2 - min2) / (max1 - min1)) + min2;
    }
}
