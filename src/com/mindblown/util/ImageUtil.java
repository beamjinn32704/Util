/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.util;

import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author beamj
 */
public class ImageUtil {
    public static boolean isImg(File img){
        try {
            Image image = ImageIO.read(img);
            return image != null;
        } catch(Exception e) {
            return false;
        }
    }
}
