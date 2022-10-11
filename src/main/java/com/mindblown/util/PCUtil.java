/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.util;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;

/**
 *
 * @author beamj
 */
public class PCUtil {
    
    /**
     * Open a file on the PC. When calling this function, if the function works, the file listed 
     * as the parameter for file will be opened in a window. A boolean variable will be returned 
     * that indicates whether the operation worked. True means it worked. False means it didn't.
     * @param file The file to open on the PC
     * @return whether the operation worked
     */
    public static boolean openFile(File file){
        try {
            //First try using Desktop to open the file
            Desktop.getDesktop().open(file);
        } catch (Exception e){
            try {
                //If Desktop fails, try using CMD
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", file.toString()});
            } catch(Exception ex){
                //If CMD also fails, then return false to indicate that the operation failed
                return false;
            }
        }
        //If Desktop or CMD worked, than return true
        return true;
    }
    
    /**
     * Opens up a folder in Windows File Explorer on the PC. If the operation works, a boolean will 
     * be returned with the value of true. If the operation doesn't work, the boolean will be false. 
     * False will be returned if the file is not a folder.
     * @param file the folder to open up
     * @return whether the operation worked
     */
    public static boolean openDir(File file){
        if(!file.isDirectory()){
            //If the file isn't a folder, return false. This function only deals with folders
            return false;
        }
        try {
            //Try opening the folder with CMD
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", file.toString()});
        } catch(Exception ex){
            //If CMD fails, return false saying the operation failed
            return false;
        }
        //If CMD works, return true saying the operation worked
        return true;
    }
    
    /**
     * Opens up a URL webpage in the PC's default browser. A boolean will be returned indicating whether 
     * the URL successfully opened. True means the operation worked. False means it didn't.
     * @param url The URL to open up in a browser
     * @return whether the URL successfully opened up
     */
    public static boolean openUrl(String url){
        try {
            //Try using Desktop to open the URL
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e){
            try {
                //If Desktop fails, try using CMD
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", url});
            } catch(Exception ex){
                //If CMD fails, return false saying the operation failed
                return false;
            }
        }
        //If either Desktop or CMD worked, return true saying the operation worked
        return true;
    }
    
    
    public static String getPCUserName(){
        return System.getProperty("user.name");
    }
}
