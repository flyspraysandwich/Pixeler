package com.swordsforlegs.pixeler;

import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.Arrays;

public class SaveData {

    //Save a new sprite to the file
    public static void SaveNew(String spriteString) {

        final FileHandle file = MainController.file;
        file.writeString(spriteString + ",", true);
        SaveData.LoadData();
        MainMenu.goingTo = MainMenu.optionSpaced;

    }

    //Save over an existing sprite
    public static void SaveOver(String spriteString, int position) {

        LoadData();
        MainMenu.spriteList.set(position, spriteString);
        SaveAll();
        MainMenu.scrolled = MainMenu.optionSpaced * (MainMenu.spritesAmount - position + 1);
    }

    //Delete a sprite from the file
    public static void DeleteData(int position) {
        FileHandle file = MainController.file;

        if (MainMenu.spritesAmount > 1) {
            MainMenu.spriteList.remove(position);
            MainMenu.spritesAmount -= 1;
            SaveAll();
        }
        else
            file.delete();
        LoadData();
    }

    //convert list into single string and save data
    public static void SaveAll() {
        String spriteString = "";
        FileHandle file = MainController.file;

        for (int i = 0; i < MainMenu.spritesAmount; i++) {
            spriteString += MainMenu.spriteList.get(i) + ",";
        }

        file.writeString(spriteString, false);
    }

    //Load data
    public static void LoadData() {

        String rawString;
        String[] stringArray;

        FileHandle file = MainController.file;

        //Split string and store in array
        if (file.exists()) {
            rawString = file.readString();
            stringArray = rawString.split(",");

            //Store as a list
            ArrayList<String> spriteList = new ArrayList<String>(Arrays.asList(stringArray));

            //Set the variables
            if (!spriteList.isEmpty()) {
                MainMenu.spritesAmount = spriteList.size();
                MainMenu.scrollAmount = (MainMenu.optionSpaced)*(MainMenu.spritesAmount);
                MainMenu.spriteList = spriteList;
            }
        }
        else {
            MainMenu.spritesAmount = 0;
            MainMenu.scrollAmount = 0;
        }
    }

    //Get file size
    public static int GetSize() {
        FileHandle file = MainController.file;

        if (file.exists()) {
            LoadData();
            return(MainMenu.spritesAmount);
        }
        else
            return(0);
    }
}
