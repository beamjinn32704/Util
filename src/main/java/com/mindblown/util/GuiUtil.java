package com.mindblown.util;


import java.awt.Component;
import java.awt.Container;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author beamj
 */
public class GuiUtil {
    /**
     * Toggles whether the children of a container are enabled. Note: the container itself (parent)'s 
     * enablement state will not be modified. 
     * @param parent the container whose children should be enabled or disabled
     * @param enable whether to enable or disable the children of parent
     * @param thorough whether to enable or disable all grandchildren (children of parent's children)
     */
    public static void enableChildren(Container parent, boolean enable, boolean thorough){
        //Get all the children components of the parent container.
        Component[] comps = parent.getComponents();
        for(Component child : comps){
            //For each component, enable or disable them.
            child.setEnabled(enable);
            //If enabling / disabling grandchildren and if this child is also a container (has children components),
            //then call this function on that child and enable / disable the grandchildren components.
            if(thorough && child instanceof Container){
                enableChildren((Container)child, enable, true);
            }
        }
    }
}
