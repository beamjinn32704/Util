/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author beamj
 */
public class ArrayUtil {
    
    /**
     * Convert an Integer array (Integer[]) to an int array (int[]). The resultant array has exactly 
     * the same contents as the original array, the type has simply been changed from Integer to int
     * @param integerArray the Integer array
     * @return the int version of the Integer array
     */
    public static int[] toIntArray(Integer[] integerArray){
        //Make a new int array the same size as the Integer array
        int[] intArray = new int[integerArray.length];
        
        //Iterate through the Integer array and take the values in the Integer array
        //and put them in the int array
        for(int i = 0; i < integerArray.length; i++){
            intArray[i] = (int)integerArray[i];
        }
        //Return the int array
        return intArray;
    }
    
    /**
     * Converts the array to an ArrayList
     * @param <T> the object type of the elements in the array
     * @param array the array to convert to an ArrayList
     * @return the array converted to an ArrayList
     */
    public static <T> ArrayList<T> toList(T[] array){
        //Create a new ArrayList and add all the elements in array one by one
        ArrayList<T> list = new ArrayList<>();
        for(int i = 0; i < array.length; i++){
            list.add(array[i]);
        }
        return list;
    }
    
    /**
     * When using Arrays.indexOf, they will return an index of 
     * the parameter you typed in - sometimes it's negative(not in the array) and sometimes it's positive
     * (in the array); this function turns negative indexes into where they should be (-1 * (ind + 1). 
     * If the ind given is positive, it returns ind.
     * @param ind the index
     * @return fixed index
     */
    public static int translateIndex(int ind){
        if(ind >= 0){
            return ind;
        }
        return -1 * (ind + 1);
    }
    
    /**
     * Returns the index of an element in a sorted array that when compared to key, 
     * it returns 0 (index of element n, so that n.compareTo(key) == 0)
     * @param <T> The class of the elements in the array
     * @param <K> The key's class
     * @param array The array to look in. This array has to be sorted
     * @param key This object is used to find the element
     * @return The index of an element in array that when compared to key, it returns 0 (index of element n, so that n.compareTo(key) == 0)
     */
    public static <K, T extends Comparable<? super K>> int sortedIndexOf(T[] array, K key){
        return Arrays.binarySearch(array, key);
    }
    
    /**
     * Returns the index of an element in a sorted array that when compared to key, 
     * it returns 0 (index of element n, so that n.compareTo(key) == 0)
     * @param <T> The class of the elements in the array
     * @param array The array to look in. This array has to be sorted
     * @param key This object is used to find the element
     * @param comparator A comparator that will compare the elements in array to key
     * @return The index of an element in array that when compared to key, it returns 0 (index of element n, so that n.compareTo(key) == 0)
     */
    public static <T> int sortedIndexOf(T[] array, T key, Comparator<? super T> comparator){
        return Arrays.binarySearch(array, key, comparator);
    }
    
    /**
     * Returns whether a sorted array has an element equivalent to key. An element equivalent to key is one such 
     * that element.compareTo(key) == 0
     * @param <K> They key's class
     * @param <T> The elements in the array's class
     * @param array The array to look in
     * @param key The key equivalent to the element trying to be found
     * @return Whether array has an an element equivalent to key
     */
    public static <K, T extends Comparable<? super K>> boolean sortedHas(T[] array, K key){
        return sortedIndexOf(array, key) > -1;
        //If sortedIndexOf returns an index at least 0, it means that there's an element equivalent to key in the array
    }
    
    /**
     * Returns whether a sorted array has an element equivalent to key. An element equivalent to key is one such 
     * that element.compareTo(key) == 0
     * @param <T> The class of the elements in the array and the key 
     * @param array The array to look in
     * @param key The key equivalent to the element trying to be found
     * @param comparator A comparator that will compare the elements in array to key
     * @return Whether array has an an element equivalent to key
     */
    public static <T> boolean sortedHas(T[] array, T key, Comparator<? super T> comparator){
        //If sortedIndexOf returns an index at least 0, it means that there's an element equivalent to key in the array
        return sortedIndexOf(array, key, comparator) > -1;
    }
    
    /**
     * Returns an element equivalent in a sorted array to key (such that element.compareTo(key) == 0).
     * @param <K> The key's class
     * @param <T> The class of the elements in the array
     * @param array The sorted array to look in
     * @param key An object equivalent to the element that is being searched for
     * @return An element in array equivalent to key
     */
    public static <K, T extends Comparable<? super K>> T sortedGet(T[] array, K key){
        //Get the index of the key
        int index = sortedIndexOf(array, key);
        //Use sortedGetHelper to get the element from array using index
        return sortedGetHelper(array, index);
    }
    
     /**
     * Returns an element equivalent in a sorted array to key (such that element.compareTo(key) == 0).
     * @param <T> The class of the elements in the array and key
     * @param array The sorted array to look in
     * @param key An object equivalent to the element that is being searched for
     * @param comparator A comparator that will compare the elements in array to key
     * @return An element in array equivalent to key
     */
    public static <T> T sortedGet(T[] array, T key, Comparator<? super T> comparator){
        //Get the index of the key
        int index = sortedIndexOf(array, key, comparator);
        //Use sortedGetHelper to get the element from array using index
        return sortedGetHelper(array, index);
    }
    
    /**
     * This function contains the functionality that returns an element from a sorted array based on an index received from sortedIndexOf 
     * To find an element in a array, the programmer can first call sortedIndexOf() with the 
     * appropriate arguments and then use this function to get the element from the array. If the index is less than 1
     * (which means that the element the programmer was looking for doesn't exist), this function will return null. Otherwise, it will 
     * return the element in the array corresponding to that index.
     * @param <T> The class of the elements in the array
     * @param array The sorted array to get an element from
     * @param index The index used to get an element from array
     * @return an element (or null) that is found with array[index]. This will be null if index < 0
     */
    private static <T> T sortedGetHelper(T[] array, int index){
        if(index >= 0){
            //If the index is >= 0, that means there's an element that when compared with key, it returns 0.
            return array[index];
        }
        //Otherwise, return null
        return null;
    }
    
    /**
     * Creates an array of objects from the individual objects. The array will contain the objects in the same 
     * order in which they were given.
     * @param <T> The class of the objects that the array will contain.
     * @param objects The objects to go into the array.
     * @return An array of objects containing the objects passed to this function.
     */
    public static <T> T[] toArray(T... objects){
        return objects;
    }
    
    public static Object[] toObjectArray(Object... objs){
        return objs;
    }
    
    /**
     * This function will take the obj variable, all the members of the arr and objs array and
     * put them into a single array.
     * @param <T>
     * @param arr
     * @param obj
     * @param objs
     * @return 
     */
    public static <T> T[] joinToArray(T[] arr, T obj, T... objs){
        ArrayList<T> objList = new ArrayList<>();
        objList.addAll(ArrayUtil.toList(arr));
        objList.add(obj);
        if(objs.length > 0){
            objList.addAll(ArrayUtil.toList(objs));
        }
        return objList.toArray(arr);
    }
    
    public static <T> int indexOf(T[] array, T object){
        for (int i = 0; i < array.length; i++) {
            if (array[i] == object) {
                return i;
            }
        }
        return -1;
    }
    
    public static <T> boolean has(T[] array, T object){
        return indexOf(array, object) != -1;
    }
    
    
    //THE REMAINS OF THE SHUFFLE ARRAY FUNCTION WHICH ISN'T COMPLETED DUE TO THE ANNOYANCES
    //OF MAKING A NEW SHUFFLED ARRAY OF TYPE T
//    /**
//     * Returns an array with the elements in a array shuffled
//     * @param <T> the class of the elements in the array
//     * @param array the array whose elements should be shuffled
//     * @return an array with the elements in array shuffled
//     */
//    public static <T> T[] shuffleArray(T[] array, T example){
//        /*
//        This algorithm essentially creates a list of the possible indices that any
//        of the elements in list can be in in.
//        Then, for each element in array, an index from the indices list
//        is chosen, and that index chosen will be the index of that element in array
//        in a shuffled array. Then, that index chosen from the indices list is removed, 
//        so no other elements from array also get that same index.
//        */
//        ArrayList<Integer> indices = new ArrayList<>();
//        for(int i = 0; i < array.length; i++){
//            indices.add(i);
//        }
//        
//        //Create the shuffled array and give it the same length as the array.
//        T[] shuffledArray = new T[array.length];
//        
//        //Random number generator
//        Random randomIntGen = new Random();
//        for(int i = 0; i < array.size(); i++){
//            //Choose the index from indices that is at a random location in indices.
//            int shuffledIndexLoc = randomIntGen.nextInt(indices.size());
//            int shuffledIndex = indices.get(shuffledIndexLoc);
//            shuffledList.set(shuffledIndex, array.get(i));
//            //Remove the element at location shuffledIndexLoc
//            indices.remove(shuffledIndexLoc);
//        }
//        //Once the elements from array have been randomly located into shuffled array, return the shuffled array.
//        return shuffledList;
//    }
    
    
//    THESE ARE THE SORTED-ADD FUNCTIONS THAT I MADE TO ADD AN OBJECT TO A SORTED ARRAY. HOWEVER, SINCE ARRAYS CAN'T
//    GROW IN SIZE, I HAVE LEFT THIS COMMENTIZED IN CASE I WANT TO USE IT IN THE FUTURE
//    /**
//     * Adds an object to a sorted array in such a way that the array remains sorted after the object is added.
//     * @param <T> the class of the elements in the array and the object to add to the array
//     * @param array the array to add an object to
//     * @param object the object to add to the array
//     */
//    public static <T extends Comparable<? super T>> void sortedAdd(T[] array, T object){ 
//        //First find the index of where the object should go. This will return either the index of 
//        //an equivalent element (an element such that element.compareTo(object) == 0)
//        //or the index in the array of where the object should go if the array was to remain sorted.
//        int ind = sortedIndexOf(array, object);
//        //Use the sortedAdd helper function to use the index to add the object to the array
//        sortedAdd(array, object, ind);
//    }
//    
//    /**
//     * Adds an object to a sorted array in such a way that the array remains sorted after the object is added.
//     * @param <T> the class of the elements in the array and the object to add to the array
//     * @param array the array to add an object to
//     * @param object the object to add to the array
//     * @param comparator the comparator used to compare the object to the elements in array
//     */
//    public static <T> void sortedAdd(T[] array, T object, Comparator<? super T> comparator){
//        //First find the index of where the object should go. This will return either the index of 
//        //an equivalent element (an element such that comparator.compare(element, object) == 0)
//        //or the index in the array of where the object should go if the list was to remain sorted.
//        int ind = sortedIndexOf(array, object, comparator);
//        //Use the sortedAdd helper function to use the index to add the object to the array
//        sortedAdd(array, object, ind);
//    }
//    
//    /**
//     * A helper function for the other sortedAdd functions that essentially gets the index taken by sortedIndexOf 
//     * and uses it to add an object to a sorted array such that the array remains sorted after the object is added.
//     * @param <T> the class of the elements in the array and the object
//     * @param array the sorted array to add the object to
//     * @param object the object to add to the array
//     * @param index the index received from sortedIndexOf which signifies where the object should go in the array
//     */
//    private static <T> void sortedAdd(T[] array, T object, int index){
//        //If the index is negative, that means there is no element equivalent to the object. Translate
//        //the index to the position object should be added to.
//        if(index < 0){
//            index = translateIndex(index);
//        }
//        
//        //Add the object to the index where object should go
//        array.add(index, object);
//    }

}
