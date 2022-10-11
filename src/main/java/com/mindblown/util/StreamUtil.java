/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author beamj
 */
public class StreamUtil {
    public static String readInputStream(InputStream stream, int buffer){
        String inputStreamText;
        try(BufferedInputStream in = new BufferedInputStream(stream, buffer)){
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buff = new byte[buffer];
            int read;
            while((read = in.read(buff)) != -1){
                out.write(buff, 0, read);
            }
            inputStreamText = out.toString(StandardCharsets.UTF_8.name());
        } catch (Exception e){
            e.printStackTrace();
            inputStreamText = "";
        }
        return inputStreamText;
    }
}
