/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.util;

import java.awt.Desktop;
import java.io.File;
import java.io.PrintWriter;
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
    
    /**
     * Creates a Windows shortcut file.
     * @param lnkName The name of the shortcut file. This isn't the name of the file to 
     * be shortcutted, but rather what the programmer wants the actual name of the shortcut file to be
     * @param relFilePath This is the file path of the file to be shortcutted relative to where this program 
     * is running right now.
     * @param shortcutParent This is the file path of the folder where the shortcut should be created. If you want 
     * the shortcut to be created in the Desktop folder that has the path "C:\Users\FakeUser\Desktop", then the argument 
     * to be submitted for this parameter should be "C:\Users\FakeUser\Desktop"
     * @return whether the shortcut file was successfully created. True means it worked. False means it didn't.
     */
    public static boolean shortcut(String lnkName, String relFilePath, String shortcutParent){
        //Create the name of the shortcut file
        String lnk = lnkName + ".lnk";
        
        //Create a batch file that will make the shortcut
        File batch = new File("ShortcutMaker.bat");
        PrintWriter writer = null;
        //Create a boolean worked which will be returned which will indicate to the programmer using this
        //function whether the shortcut operation worked
        boolean worked = true;
        try {
            //Write to the batch file the instructions
            writer = new PrintWriter(batch);
            writer.println("@echo off");
            writer.println("set startupPath=\"" + shortcutParent + "\"\n"
                    + "set exePath=\"%CD%\n"
                    + "cd %startupPath%\n"
                    + "echo Set oWS = WScript.CreateObject(\"WScript.Shell\") > %startupPath%\\CreateShortcut.vbs\n"
                    + "echo sLinkFile = \"" + lnk + "\" >> %startupPath%\\CreateShortcut.vbs\n"
                            + "echo Set oLink = oWS.CreateShortcut(sLinkFile) >> %startupPath%\\CreateShortcut.vbs\n"
                            + "echo oRef.TargetPath = %exePath%\\" + relFilePath + "\" >> %startupPath%\\CreateShortcut.vbs\n"
                                    + "echo oRef.WorkingDirectory = %startupPath%\" >> %startupPath%\\CreateShortcut.vbs\n"
                                    + "echo oRef.Description = \"" + lnkName + "\" >> %startupPath%\\CreateShortcut.vbs\n"
                                            + "echo oRef.Save >> %startupPath%\\CreateShortcut.vbs\n"
                                            + "cd %startupPath%\n"
                                            + "C:\\Windows\\System32\\cscript.exe %startupPath%\\CreateShortcut.vbs\n"
                                            + "del CreateShortcut.vbs\n"
                                            + "echo %CD%\n"
                                            + "start " + lnk +"\n"
                                                    + "cd %exePath%\n"
                                                    +"(goto) 2>nul & del \"%~f0\" & exit /b");
            //Run the batch file
            Desktop.getDesktop().open(batch);
        }
        catch (Exception e) {
            //Debug to see what went wrong and what alternative fixes there are
            
            //First see if the batch file exists.
            if (batch.exists()) {
                //If it does, try running it through CMD instead of Desktop.getDesktop()
                try {
                    Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", batch.toString()});
                }
                catch(Exception ex) {
                    //If running through CMD still doesn't work, then creating the shortcut failed,
                    //so set the worked boolean to false to indicate that the operation failed.
                    worked = false;
                }
            } else {
                //If the file wasn't able to be made, than the operation failed. Set the worked boolean
                //to false to indicate this.
                worked = false;
            }
        } finally {
            writer.close();
        }
        if(batch.isFile()){
            //If the batch file exists, than delete it.
            FileUtil.delete(batch);
        }
        //Return whether the operation worked
        return worked;
    }
    
    
    /**
     * Causes the file located with the relative file path to the program (relFilePath) to open whenever the PC opens.
     * @param lnkName This function places a shortcut file (a .lnk file) that is linked to the file you want to open 
     * when Windows starts up in the Windows Startup folder. This will cause the .lnk file to open when the PC starts up. This parameter is the name you want the .lnk file to have.
     * @param relFilePath This is the relative file path (from wherever this program is running from) to the file you 
     * want to start up.
     */
    public static void startupShortcut(String lnkName, String relFilePath) {
        //Create the name of the folder where the shortcut should be created
        String shortcutParent = "C:\\users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";
        //Create the shortcut
        shortcut(lnkName, relFilePath, shortcutParent);
    }
    
    /**
     * This causes the .exe file located with the relative file path to the program (relFilePath) to be available
     * in SendTo menu that is available when right clicking a file on a computer.
     * @param lnkName This function places a shortcut file (a .lnk file) in the Windows SendTo folder. This .lnk file 
     * is linked to the file you want to have in the SendTo menu. This parameter is the name you want the .lnk 
     * file to have.
     * @param relFilePath This is the relative file path (from wherever this program is running from) to the file you 
     * want to start up. However, the file this path references MUST be a program file. Otherwise, the file will not show 
     * up in the Windows SendTo menu.
     */
    public static void sendToShortcut(String lnkName, String relFilePath) {
        //Create the name of the folder where the shortcut should be created
        String shortcutParent = "C:\\users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Microsoft\\Windows\\SendTo";
        //Create the shortcut
        shortcut(lnkName, relFilePath, shortcutParent);
    }
}
