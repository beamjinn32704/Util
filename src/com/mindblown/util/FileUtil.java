/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author beamj
 */
public class FileUtil {
    //List of file types that are used to specify what type of files JFileChooser can select
    public static int filesOnly = JFileChooser.FILES_ONLY;
    public static int dirsOnly = JFileChooser.DIRECTORIES_ONLY;
    public static int filesAndDirs = JFileChooser.FILES_AND_DIRECTORIES;
    
    //File name extensions for image and video files
    public static FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
    public static FileNameExtensionFilter vidFilter = new FileNameExtensionFilter("Videos", new String[]{"avi", "riff", "mpg",
        "vob", "mp4", "m2ts", "mov", "3gp", "mkv"});
    
    
    public static File getFile(int selectionMode, String title) {
        return getFile(selectionMode, title, null);
    }
    
    public static File getFile(int selectionMode, String title, File f) {
        return getFile(selectionMode, title, null, f);
    }
    
    public static File getFile(int selectionMode, String title, FileFilter filter, File f) {
        File file = null;
        JFileChooser chooser = new JFileChooser();
        if(filter != null){
            chooser.setFileFilter(filter);
        }
        chooser.setFileSelectionMode(selectionMode);
        chooser.setDialogTitle(title);
        if(f != null){
            chooser.setSelectedFile(f);
        }
        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }
        if(file == null){
            return null;
        }
        return file.getAbsoluteFile();
    }
    
    public static String getText(File file){
        if(!file.isFile()){
            return "";
        }
        String text;
        try(FileInputStream in = new FileInputStream(file)){
            text = StreamUtil.readInputStream(in, 100000);
        } catch (Exception e){
            e.printStackTrace();
            text = "";
        }
        return text;
    }
    
    public static boolean delete(File fileToDelete){
        if(!fileToDelete.exists()){
            return true;
        }
        if(!fileToDelete.delete()){
            File batch = new File("deleteBatch.bat").getAbsoluteFile();
            try (PrintWriter writer = new PrintWriter(batch)){
                writer.println("del /F \"" + fileToDelete.toString() + "\"");
                writer.println("(goto) 2>nul & del \"%~f0\" & exit /b");
                BatchUtil.runBatch(batch, true);
            } catch (Exception e){
                e.printStackTrace();
            }
            batch.delete();
        }
        return !fileToDelete.exists();
    }
    
    public static FileFilter filesOnlyFilter = new FileFilter() {
        @Override
        public boolean accept(File f) {
            return f.isFile();
        }

        @Override
        public String getDescription() {
            return "Files";
        }
    };
    
    public static File getImgFile(String title) {
        return FileUtil.getFile(JFileChooser.FILES_ONLY, title, imgFilter, null);
    }
    
    public static ArrayList<File> getImgFiles(File dir, boolean noSub){
        return getFilesOfType(dir, noSub, imgFilter);
    }
    
    public static ArrayList<File> getMovies(File dir, boolean noSub){
        return getFilesOfType(dir, noSub, vidFilter);
    }
    
    public static File getFileOfType(File dir, boolean sub, FileFilter... filters){
        ArrayList<File> files = gfot(dir, sub, true, filters);
        if(files.isEmpty()){
            return null;
        } else {
            return files.get(0);
        }
    }
    
    public static ArrayList<File> getFilesOfType(File dir, boolean sub, FileFilter... filters){
        return gfot(dir, sub, false, filters);
    }
    
    private static ArrayList<File> gfot(File dir, boolean sub, boolean oneFile, FileFilter... filters){
        ArrayList<File> files = new ArrayList<>();
        if(!dir.isDirectory()){
            return files;
        }
        
        if(filters.length == 0){
            return files;
        }
        
        ArrayList<File> dirsToCheck = new ArrayList<>();
        dirsToCheck.add(dir);
        
        while(!dirsToCheck.isEmpty()){
            File file = dirsToCheck.remove(0);
            File[] fs = file.listFiles();
            for(int i = 0; i < fs.length; i++){
                File f = fs[i];
                if(sub && f.isDirectory()){
                    files.addAll(gfot(f, true, oneFile, filters));
                } else {
                    for(int j = 0; j < filters.length; j++){
                        FileFilter filter = filters[j];
                        if(filter.accept(f)){
                            files.add(f);
                            if(oneFile){
                                return files;
                            }
                        }
                    }
                }
            }
        }
        return files;
    }
    
    public static boolean rename(File fileToRename, String renameTo){
        if(fileToRename.getName().equals(renameTo)){
            return true;
        }
        
        File renamedFile = new File(fileToRename.getParentFile(), renameTo);
        if(renamedFile.exists()){
            return false;
        }
        File batch = new File("renameBatch.bat");
        if(!fileToRename.renameTo(renamedFile)){
            try (PrintWriter writer = new PrintWriter(batch)){
                writer.println("rename \"" + fileToRename.toString() + "\" \"" + renameTo + "\"");
                writer.println("exit /b");
                BatchUtil.runBatch(batch, true);
            } catch (Exception e){
                e.printStackTrace();
            }
            persistentDelete(batch, 3);
        }
        return !fileToRename.exists();
    }
    
    public static boolean persistentDelete(File fileToDelete, int persistenceCount){
        boolean tryToDelete = true;
        boolean success = false;
        int deleteCount = 0;
        while(tryToDelete){
            if(!delete(fileToDelete)){
                deleteCount++;
                if(deleteCount >= 3){
                    tryToDelete = false;
                }
            } else {
                tryToDelete = false;
                success = true;
            }
        }
        return success;
    }
    
    
    
    /**
     * This function returns the root file of file f.
     * @param f the file to find the root of.
     * @return f's root file
     */
    public static File getRootFile(File f){
        //Go through roots and find the root that the file starts with.
        File[] roots = File.listRoots();
        String file = f.getAbsoluteFile().toString();
        for(File root : roots){
            if(file.startsWith(root.toString())){
                return root;
            }
        }
        System.out.println("NO ROOT");
        return null;
    }
    
    /**
     * Appends a string to a file
     * @param fileToWriteTo the file the string toWrite should be appended to
     * @param toWrite the string to append to fileToWriteTo
     * @return whether the operation was completed with no errors
     */
    public static boolean writeToFile(File fileToWriteTo, String toWrite){
        return writeToFile(fileToWriteTo, toWrite, false);
    }
    
    /**
     * Modifies a file.
     * @param fileToWriteTo the file to modify
     * @param toWrite the string to modify the file with
     * @param append whether the string should be appended to the file. If this variable is true, 
     * the string toWrite will be appended. Otherwise, all the text in fileToWriteTo will be replaced 
     * with the string toWrite
     * @return whether the operation was completed with no errors
     */
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
     * Returns the directory that the program is running in.
     * @return the directory / folder that the program is running in.
     */
    public static String getCurrentWorkingDirectory(){
        String dir = System.getProperty("user.dir");
        return dir;
    }
}
