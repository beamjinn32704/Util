/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.util;

import java.io.File;

/**
 *
 * @author beamj
 */
public class BatchUtil {
    public static void runBatch(File batch, boolean wait) throws Exception {
        Runtime rt = Runtime.getRuntime();
        Process exec = rt.exec("cmd /c start " + batch);
        if(wait){
            exec.waitFor();
        }
    }
    
    
}
