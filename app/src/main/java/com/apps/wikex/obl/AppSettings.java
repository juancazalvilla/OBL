package com.apps.wikex.obl;

import java.util.ArrayList;

public class AppSettings {

    // Share preferences keys

    private static ItemList itemList = null;
    private static ItemList currentList = null;
    private static ArrayList<String> listIndex = null;
    private static Boolean savedList = false;
    public static final String FILEINDEX = "index.filelist";
    public static final String listMode = "listMode";

    public static final String SP_SAVEDLIST = "saveList";


    // Use myjson website to hots the json file.
    // https://api.myjson.com/bins/777ns

    public static final String URL_DATA = "https://api.myjson.com/bins/777ns";

    public static ItemList getItemList() {
        if (itemList == null){
            itemList = new ItemList();
        }
        return itemList;
    }

    public static ItemList getCurrentList() {
        if (currentList == null){
            currentList = new ItemList();
        }
        return currentList;
    }

    public static ArrayList<String> getListIndex(){
        if (listIndex == null){
            listIndex = new ArrayList<>();
        }
        return listIndex;
    }

    public static Boolean getSavedList() {
        return savedList;
    }

    public static void setSavedList(Boolean savedList) {
        AppSettings.savedList = savedList;
    }
}
