package com.mindblown.util;



import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

/**
 *
 * @author beamj
 */
public class Utilll {
    
    
    
    public static double removeDecimal(double num, int maxDec){
        String rep = Double.toString(num);
        if(rep.length() - (rep.lastIndexOf(".") + 1) <= maxDec){
            return num;
        }
        //123.45
        //(int)(123.45 * (maxDec * 10)) = 1234 / (maxDec * 10)
        double factor = Math.pow(10, maxDec);
        return (double)((int) (num * factor)) / factor;
    }
    
    public static String formatBytes(long d){
        double data = (double)d;
        String type = "B";
        String coefficient = "";
        if(d < 0){
            coefficient = "-";
            data = Math.abs(data);
        }
        if(data / 1024.0 >= 1.0){
            type = "KB";
            data /= 1024.0;
            if(data / 1024.0 >= 1.0){
                type = "MB";
                data /= 1024.0;
                if(data / 1024.0 >= 1.0){
                    type = "GB";
                    data /= 1024.0;
                    if(data / 1024.0 >= 1.0){
                        type = "TB";
                        data /= 1024.0;
                    }
                }
            }
        }
        data = removeDecimal(data, 1);
        if(data < 0){
            coefficient = "";
        }
        return coefficient + data + " " + type;
    }
    
    public static double map(double num, double numMin, double numMax, double mappedNumMin, double mappedNumMax){
        double min1 = numMin;
        double max1 = numMax;
        double min2 = mappedNumMin;
        double max2 = mappedNumMax;
        return ((num - min1) * (max2 - min2) / (max1 - min1)) + min2;
    }
    
    public static String getPCUserName(){
        return System.getProperty("user.name");
    }
    
    
    
    /**
     * This function merges all 1-D elements of the arrays passed into a single new array.If given [1, 5] and [3, 2], this would return [1, 5, 3, 2].
     * If given [1, 5], [3, 2], and [10, 7], this would return [1, 5, 3, 2, 10, 7].
 If given [1, 5,], [3, 2], and [[10, 7], [4, 8]], this would return [1, 5, 3, 2, [10, 7], [4, 8]].
     * @param <T>
     * @param arr1
     * @param arr2
     * @param arrs
     * @return 
     */
    public static <T> T[] mergeArrays(T[] arr1, T[] arr2, T[]... arrs){
        ArrayList<Object> objList = new ArrayList<>();
        objList.addAll(ArrayUtil.toList(arr1));
        objList.addAll(ArrayUtil.toList(arr2));
        if(arrs.length > 0){
            objList.addAll(ArrayUtil.toList(arrs));
        }
        return objList.toArray(arr1);
    }
    
    public static boolean writeToFile(File fileToWriteTo, String toWrite){
        return writeToFile(fileToWriteTo, toWrite, false);
    }
    
    public static boolean writeToFile(File fileToWriteTo, String toWrite, boolean append){
        try(FileOutputStream fo = new FileOutputStream(fileToWriteTo, append)){
            fo.write(toWrite.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
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
     * Returns the directory that the program is running in.
     * @return the directory / folder that the program is running in.
     */
    public static String getCurrentWorkingDirectory(){
        String dir = System.getProperty("user.dir");
        return dir;
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