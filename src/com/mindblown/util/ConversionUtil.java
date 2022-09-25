/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.util;

/**
 *
 * @author beamj
 */
public class ConversionUtil {
    
    /**
     * Takes a number of bytes and returns the amount that number represents in String format. 
     * For ex: if the number of bytes given is 2000, the string returned is "1.9 KB"
     * @param d the number of bytes
     * @return the number of bytes converted to String form. The highest data value 
     * that will be returned is in Terabytes (TB). This means if the number of bytes 
     * corresponding to 2000 TB is given, the answer will not be given in terms of "PB" 
     * or petabytes. The answer will just be returned as 2000 TB. Also, the number returned 
     * in the string will be modified to only have one decimal place.
     */
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
        data = NumUtil.removeDecimal(data, 1);
        if(data < 0){
            coefficient = "";
        }
        return coefficient + data + " " + type;
    }
}
