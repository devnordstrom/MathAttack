/*
 * Copyright (C) 2018 Orville Nordström
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package se.devnordstrom.mathattack.highscore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import se.devnordstrom.mathattack.App;
import se.devnordstrom.mathattack.difficulty.GameDifficulty;
import se.devnordstrom.mathattack.util.Util;

/**
 *
 * @author Orville Nordström
 */
public class HighscoreController {
    
    private static final File HIGHSCORE_FILE = new File("highscore.ser");
    
    private static final int DISPLAY_HIGHSCORE_MAXIMUM = 10;
    
    /**
     * Reads and sorts the Highscore entries for the difficulty provided.
     * 
     * @param difficulty
     * @throws IllegalArgumentException if difficulty is null.
     * @return 
     */
    public static List<HighscoreEntry> getHighscoreEntriesForDifficulty(GameDifficulty difficulty)
            throws IllegalArgumentException {
        
        if(difficulty == null) throw new IllegalArgumentException("The difficulty must not be null!");
                    
        List<HighscoreEntry> highscoreEntryList = new ArrayList<>();
        
        List<HighscoreEntry> unserializedHighscoreEntryList = readHighscoreEntries();
        
        for(HighscoreEntry highscoreEntry : unserializedHighscoreEntryList) {
            if(difficulty == highscoreEntry.getDifficulty()) {
                highscoreEntryList.add(highscoreEntry);
            }
        }
        
        Collections.sort(highscoreEntryList);
        Collections.reverse(highscoreEntryList);
        
        return highscoreEntryList;
    }
    
    private static List<HighscoreEntry> unserializeHighscoreEntries() throws IOException, 
            ClassNotFoundException {
        try {
            HighscoreEntry[] entries = (HighscoreEntry[]) 
                    Util.unserialize(HIGHSCORE_FILE);
                        
            List<HighscoreEntry> entryList = new ArrayList<>();
            for(HighscoreEntry entry : entries) {
                entryList.add(entry);
            }
            return entryList;
        } catch(FileNotFoundException fex) {
            fex.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * 
     * @return 
     */
    private static List<HighscoreEntry> readHighscoreEntries() {
        try {
            return unserializeHighscoreEntries();
        } catch(Exception ex) {
            ex.printStackTrace();
            
            showErrorMessage("Couldn't read highscore: "+ex.getMessage());
            
            return null;
        }    
    }
    
    private static void showErrorMessage(String msg)
    {
        Util.showErrorMesage(msg, App.APP_NAME);
    }
    
    /**
     * 
     * @param entry
     * @return 
     */
    public static boolean isQualifiedForHighScore(HighscoreEntry entry) {
        
        if(entry == null) {
            throw new IllegalArgumentException("The highscore entry must be set!");
        }
        
        if(entry.getDifficulty() == null)  {
            throw new IllegalArgumentException("The difficulty must be set!");
        }
        
        
        List<HighscoreEntry> highScoreEntryList = getHighscoreEntriesForDifficulty(entry.getDifficulty());
        
        highScoreEntryList.add(entry);
        
        Collections.sort(highScoreEntryList);
        
        return highScoreEntryList.indexOf(entry) <= DISPLAY_HIGHSCORE_MAXIMUM;
        
    }
    
    /**
     * 
     * @param difficulty
     * @return 
     */
    public static HighscoreEntry getLowestQualifyingHighscoreForDifficulty(GameDifficulty difficulty) {
        
        if(difficulty == null) {
            throw new IllegalArgumentException("The difficulty must not be null!");
        }
        
        
        
        List<HighscoreEntry> entryList = getHighscoreEntriesForDifficulty(difficulty);
        
        if(entryList.isEmpty()) {
            return null;
        }
        
        return entryList.get(entryList.size() - 1);
        
    }
    
    /**
     * 
     * @param entry 
     */
    public static void addHighscoreEntry(HighscoreEntry entry) {
        if(entry == null) {
            throw new IllegalArgumentException("Entry must not be null!");
        }
        
        if(entry.getDifficulty() == null) {
            throw new IllegalArgumentException("The difficulty must be set!");
        }

        List<HighscoreEntry> highscoreEntryList = readHighscoreEntries();
        
        highscoreEntryList.add(entry);
        
        saveHighscoreList(highscoreEntryList);
        
        try {
            Util.serializeObject(new File("HIGHSCORE_ENTRY"), entry);
        } catch(Exception ex) {
            ex.printStackTrace();
        }    
    }
    
    private static void saveHighscoreList(List<HighscoreEntry> highscoreEntryList) {
        try {
            HighscoreEntry[] entryArray = highscoreEntryList.toArray(new HighscoreEntry[0]);
            
            Util.serializeObject(HIGHSCORE_FILE, entryArray);
        } catch(Exception ex) {
            ex.printStackTrace();
            
            Util.showErrorMesage("Could not save highscore: " + ex, App.APP_NAME);
        }
    }
}