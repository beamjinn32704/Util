/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author beamj
 */
public class ArrayListUtil {
    
    /**
     * Prints out each element in an ArrayList. Each element gets their own line.
     * @param <T> the class of the elements in list
     * @param list the list whose elements will be printed
     */
    public static <T> void print(ArrayList<T> list){
        //For each element in list, print them out
        for(T o : list){
            System.out.println(o);
        }
    }
    
    /**
     * When using Collections.indexOf, they will return an index of 
     * the parameter you typed in - sometimes it's negative(not in the array) and sometimes it's positive
     * (in the array); this function turns negative indexes into where they should be (-1 * (ind + 1). 
     * If the ind given is positive, it returns ind.
     * @param ind The index
     * @return Translated index
     */
    public static int translateIndex(int ind){
        //If the index is a positive index, there's no need of translating it.
        if(ind >= 0){
            return ind;
        }
        //If not, then translate it.
        return -1 * (ind + 1);
    }
    
    /**
     * Returns an element equivalent in a sorted list to key (such that element.compareTo(key) == 0).
     * @param <K> The key's class
     * @param <T> The class of the elements in the list
     * @param list The sorted list to look in
     * @param key An object equivalent to the element that is being searched for
     * @return An element in list equivalent to key
     */
    public static <K, T extends Comparable<? super K>> T sortedGet(ArrayList<T> list, K key){
        //Get the index of the key
        int index = sortedIndexOf(list, key);
        //Use sortedGetHelper to get the element from list using index
        return sortedGetHelper(list, index);
    }
    
    /**
     * Returns an element equivalent in a sorted list to key (such that element.compareTo(key) == 0).
     * @param <K> The key's class
     * @param <T> The class of the elements in the list
     * @param list The sorted list to look in
     * @param key An object equivalent to the element that is being searched for
     * @param comparator A comparator that will compare the elements in list to key
     * @return An element in list equivalent to key
     */
    public static <K, T extends K> T sortedGet(ArrayList<T> list, K key, Comparator<? super K> comparator){
        //Get the index of the key
        int index = sortedIndexOf(list, key, comparator);
        //Use sortedGetHelper to get the element from list using index
        return sortedGetHelper(list, index);
    }
    
    /**
     * This function contains the functionality that returns an element from a sorted list based on an index received from sortedIndexOf 
     * To find an element in a list, the programmer can first call sortedIndexOf() with the 
     * appropriate arguments and then use this function to get the element from the list. If the index is less than 1
     * (which means that the element the programmer was looking for doesn't exist), this function will return null. Otherwise, it will 
     * return the element in the list corresponding to that index.
     * @param <T> The class of the elements in the list
     * @param list The sorted list to get an element from
     * @param index The index used to get an element from list
     * @return an element (or null) that is found with list.get(index). This will be null if index < 0
     */
    private static <T> T sortedGetHelper(ArrayList<T> list, int index){
        if(index >= 0){
            //If the index is >= 0, that means there's an element that when compared with key, it returns 0.
            return list.get(index);
        }
        //Otherwise, return null
        return null;
    }
    
    /**
     * Returns the index of an element in a sorted list that when compared to key, 
     * it returns 0 (index of element n, so that n.compareTo(key) == 0)
     * @param <T> The class of the elements in the list
     * @param <K> The key's class
     * @param list The list to look in. This list has to be sorted
     * @param key This object is used to find the element
     * @return The index of an element in list that when compared to key, it returns 0 (index of element n, so that n.compareTo(key) == 0)
     */
    public static <K, T extends Comparable<? super K>> int sortedIndexOf(ArrayList<T> list, K key){
        return Collections.binarySearch(list, key);
    }
    
    /**
     * Returns the index of an element in a sorted list that when compared to key with comparator, the comparator returns 0.
     * @param <T> The class of the elements in the list
     * @param <K> The key's class
     * @param list The list to look in
     * @param key This object is used to find the element
     * @param comparator A comparator that will compare the elements in list to key
     * @return The index of an element in list that when compared to key, it returns 0 (index of element n, so that n.compareTo(key) == 0)
     */
    public static <K, T extends K> int sortedIndexOf(ArrayList<T> list, K key, Comparator<? super K> comparator){
        return Collections.binarySearch(list, key, comparator);
    }
    
    /**
     * Returns whether a sorted list has an element equivalent to key. An element equivalent to key is one such 
     * that element.compareTo(key) == 0
     * @param <K> They key's class
     * @param <T> The elements in the list's class
     * @param list The list to look in
     * @param key The key equivalent to the element trying to be found
     * @return Whether list has an an element equivalent to key
     */
    public static <K extends Comparable, T extends Comparable<? super K>> boolean sortedHas(ArrayList<T> list, K key){
        //If sortedIndexOf returns an index at least 0, it means that there's an element equivalent to key in the list
        return sortedIndexOf(list, key) > -1;
    }
    
    
    /**
     * Returns whether a sorted list has an element equivalent to key. An element equivalent to key is one such 
     * that element.compareTo(key) == 0
     * @param <T> The class of the elements in the list and the key
     * @param list The list to look in
     * @param key The key equivalent to the element trying to be found
     * @param comparator A comparator that will compare the elements in list to key
     * @return Whether list has an an element equivalent to key
     */
    public static <T> boolean sortedHas(ArrayList<T> list, T key, Comparator<? super T> comparator){
        //If sortedIndexOf returns an index at least 0, it means that there's an element equivalent to key in the list
        return sortedIndexOf(list, key, comparator) > -1;
    }
    
    /**
     * Adds an object to a sorted list in such a way that the list remains sorted after the object is added.
     * @param <T> the class of the elements in the list and the class of the objects to add to the list
     * @param list the list to add an object to
     * @param object the object to add to the list
     */
    public static <T extends Comparable<? super T>> void sortedAdd(ArrayList<T> list, T object){ 
        //First find the index of where the object should go. This will return either the index of 
        //an equivalent element (an element such that element.compareTo(object) == 0)
        //or the index in the list of where the object should go if the list was to remain sorted.
        int ind = sortedIndexOf(list, object);
        //Use the sortedAdd helper function to use the index to add the object to the list
        sortedAdd(list, object, ind);
    }
    
    /**
     * Adds an object to a sorted list in such a way that the list remains sorted after the object is added.
     * @param <T> the class of the elements in the list and the class of the object to add to the list
     * @param list the list to add an object to
     * @param object the object to add to the list
     * @param comparator the comparator used to compare the object to the elements in list
     */
    public static <T> void sortedAdd(ArrayList<T> list, T object, Comparator<? super T> comparator){
        //First find the index of where the object should go. This will return either the index of 
        //an equivalent element (an element such that comparator.compare(element, object) == 0)
        //or the index in the list of where the object should go if the list was to remain sorted.
        int ind = sortedIndexOf(list, object, comparator);
        //Use the sortedAdd helper function to use the index to add the object to the list
        sortedAdd(list, object, ind);
    }
    
    /**
     * A helper function for the other sortedAdd functions that essentially gets the index taken by sortedIndexOf 
     * and uses it to add an object to a sorted list such that the list remains sorted after the object is added.
     * @param <T> the class of the elements in the list and the object
     * @param list the sorted list to add the object to
     * @param object the object to add to the list
     * @param index the index received from sortedIndexOf which signifies where the object should go in the list
     */
    private static <T> void sortedAdd(ArrayList<T> list, T object, int index){
        //If the index is negative, that means there is no element equivalent to the object. Translate
        //the index to the position object should be added to.
        if(index < 0){
            index = translateIndex(index);
        }
        
        //Add the object to the index where object should go
        list.add(index, object);
    }
    
    /**
     * Removes an element from a sorted list equivalent to key. If the key isn't equivalent (such that element.compareTo(key) == 0) 
     * to any of the elements in the list, nothing happens. 
     * This function will remove a (not necessarily the first) element in the list equivalent to the key.
     * @param <T> the class of the key 
     * @param <K> the class of the elements of in the sorted list
     * @param list the list to remove an element from
     * @param key the key equivalent to an element that should be removed from the list
     * @return the element that was removed (if any element was removed). If no element was removed, null is returned.
     */
    public static <T, K extends Comparable<? super T>> K sortedRemove(ArrayList<K> list, T key){
        int ind = sortedIndexOf(list, key);
        if(ind >= 0){
            return list.remove(ind);
        } else {
            return null;
        }
    }
    
    /**
     * Removes an element from a sorted list equivalent to key using a comparator.If the key isn't equivalent (such that comparator.compare(element, key) == 0) to any of the elements in the list, nothing happens. 
     * This function will remove a (not necessarily the first) element in the list equivalent to the key.
     * @param <T> the class of the key
     * @param <K> the class of the elements in the list
     * @param list the list to remove an element from
     * @param key the key equivalent to an element that should be removed from the list
     * @param comparator the comparator used to determine whether an element in the list is equivalent to the key
     * @return the element that was removed (if any element was removed). If no element was removed, null is returned.
     */
    public static <T, K extends T> K sortedRemove(ArrayList<K> list, T key, Comparator<? super T> comparator){
        int ind = sortedIndexOf(list, key, comparator);
        if(ind >= 0){
            return list.remove(ind);
        } else {
            return null;
        }
    }
    
    /**
     * Returns a list with the elements in a list shuffled
     * @param <T> the class of the elements in the list
     * @param list the list whose elements should be shuffled
     * @return a list with the elements in list shuffled
     */
    public static <T> ArrayList<T> shuffleList(ArrayList<T> list){
        /*
        This algorithm essentially creates a list of the possible indices that any
        of the elements in list can be in in.
        Then, for each element in list, an index from the indices list
        is chosen, and that index chosen will be the index of that element in list
        in a shuffled list. Then, that index chosen from the indices list is removed, 
        so no other elements from list also get that same index.
        */
        ArrayList<Integer> indices = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            indices.add(i);
        }
        
        //Create the shuffled list and give it the same length as the list.
        //Note: the null doesn't have any special usage. It could be any element. It's just
        //used as a placeholder for the elements in list.
        ArrayList<T> shuffledList = new ArrayList<T>();
        for(int i = 0; i < list.size(); i++){
            shuffledList.add(null);
        }
        
        //Random number generator
        Random randomIntGen = new Random();
        for(int i = 0; i < list.size(); i++){
            //Choose the index from indices that is at a random location in indices.
            int shuffledIndexLoc = randomIntGen.nextInt(indices.size());
            int shuffledIndex = indices.get(shuffledIndexLoc);
            shuffledList.set(shuffledIndex, list.get(i));
            //Remove the element at location shuffledIndexLoc
            indices.remove(shuffledIndexLoc);
        }
        //Once the elements from list have been randomly located into shuffled list, return the shuffled list.
        return shuffledList;
    }
}