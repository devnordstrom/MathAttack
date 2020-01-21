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
package se.devnordstrom.mathattack.gui.controller;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import se.devnordstrom.mathattack.App;
import se.devnordstrom.mathattack.gui.entity.MenuItemEntity;
import se.devnordstrom.mathattack.gui.entity.PaintableEntity;
import se.devnordstrom.mathattack.gui.entity.TextEntity;
import se.devnordstrom.mathattack.highscore.HighscoreController;
import se.devnordstrom.mathattack.highscore.HighscoreEntry;
import se.devnordstrom.mathattack.difficulty.GameDifficulty;
import se.devnordstrom.mathattack.util.MenuUtil;
import se.devnordstrom.mathattack.util.Util;

/**
 *
 * @author Orville Nordström
 */
public class HighscoreGuiController extends EntityController {
    
    private static final int HIGHSCORE_ENTRY_PADDING = 175;
    private static final int MENU_ITEM_SPACING = 150;
    private static final int MENU_ITEM_VERTICAL_SPACING = 40;
    
    private GameDifficulty gameDifficulty;
    
    private List<MenuItemEntity> menuItemEntityList;
    
    private List<TextEntity> highscoreTextEntryList = new ArrayList<>();
        
    private Runnable showMenuRunnable;
    
    private int baseX, baseY, highscoreBaseY;
    
    private MouseListener mouseListener;
            
    private MouseMotionListener mouseMotionListener;
    
    public HighscoreGuiController(Runnable showMenuRunnable, GameDifficulty gameDifficulty, 
            int baseX, int baseY) {
        this.baseX = baseX;
        this.baseY = baseY;
        this.highscoreBaseY = baseY + MENU_ITEM_VERTICAL_SPACING*2;
        this.showMenuRunnable = showMenuRunnable;
        this.gameDifficulty = gameDifficulty;
        this.mouseListener = MenuUtil.getMenuMouseListener(this);
        this.mouseMotionListener = MenuUtil.getMenuMouseMotionListener(this);
        
        setMenuItems();
        
        setHighscoreItems();
    }
    
    /**
     *  
     */
    private void setMenuItems() {
        if(menuItemEntityList != null) {
            return;
        }
        
        int x = baseX;
        
        menuItemEntityList = new ArrayList<>();

        MenuItemEntity goToMenuMenuItem = MenuUtil.createMenuItemEntity(x, baseY, 
                "MENU", showMenuRunnable);
        x += MENU_ITEM_SPACING*4;
        
        menuItemEntityList.add(goToMenuMenuItem);
        
        
        
        MenuItemEntity exitMenuItem = MenuUtil.createMenuItemEntity(x, baseY, 
                "EXIT", () -> {App.exitApplication();});
        x += MENU_ITEM_SPACING;
        
        menuItemEntityList.add(exitMenuItem);    
    }
    
    /**
     * 
     */
    @Deprecated
    private MenuItemEntity createMenuItemForDifficulty(GameDifficulty difficulty, int x) {
        Runnable action = ()-> {
            setGameDifficulty(difficulty);
        };
        
        MenuItemEntity menuItemEntity = MenuUtil.createMenuItemEntity(x, baseY, 
            difficulty.toString(), action);
        
        return menuItemEntity;
    }
    
    @Deprecated
    public void setGameDifficulty(GameDifficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
        
        setHighscoreItems();
    }
    
    public void printHighscore() {
        List<HighscoreEntry> highscoreEntryList = 
                HighscoreController.getHighscoreEntries();
        
        StringBuilder sb = new StringBuilder();
        
        for(int i = 0; i < highscoreEntryList.size(); i++) {
            HighscoreEntry entry = new HighscoreEntry();
            
            String entryInfo = "#" + (i + 1) + " " + entry.getPlayerName() 
                    + " | " + entry.getScore() + " points.";
            
            sb.append(entryInfo);
            
        }
        
        System.out.println(sb.toString());
    }
    
    private void setHighscoreItems() {
        highscoreTextEntryList.clear();
        
        String highscoreHeader = "Highscore";
        if(gameDifficulty != null) {
            highscoreHeader = " for "+gameDifficulty;
        }
        
        int diffTextY = highscoreBaseY;
        TextEntity diffTextEntity = new TextEntity(highscoreHeader, baseX, diffTextY);
        diffTextEntity.setFont(MenuUtil.MENU_ITEM_FONT);
        
        highscoreTextEntryList.add(diffTextEntity);                
        
        List<HighscoreEntry> highscoreEntryList = fetchHighscoreEntries();
                
        if(highscoreEntryList.isEmpty()) {
            int noEntriesY = highscoreBaseY + MENU_ITEM_VERTICAL_SPACING;
            TextEntity noEntriesTextEntity = new TextEntity("(No entries)", baseX, noEntriesY);
            noEntriesTextEntity.setFont(MenuUtil.MENU_ITEM_FONT);
            highscoreTextEntryList.add(noEntriesTextEntity);
        } else {
            for(int i = 0; i < highscoreEntryList.size(); i++) {
                HighscoreEntry highscoreEntry = highscoreEntryList.get(i);            
                highscoreTextEntryList.addAll(createHighscoreTextEntries(highscoreEntry, i));
            }
        }
    }
    
    private List<HighscoreEntry> fetchHighscoreEntries() {
        return HighscoreController.getHighscoreEntries();
    }
        
    private List<TextEntity> createHighscoreHeaderTextEntries(int rowIndex) {
        int x = baseX;
        int y = highscoreBaseY + ((rowIndex + 1) * MENU_ITEM_VERTICAL_SPACING);
        
        List<TextEntity> textEntities = new ArrayList<>();
        
        TextEntity scoreTextEntity = new TextEntity("SCORE", x, y);
        scoreTextEntity.setColor(MenuUtil.MENU_COLOR);
        scoreTextEntity.setFont(MenuUtil.MENU_ITEM_FONT);
        textEntities.add(scoreTextEntity);
        
        x += HIGHSCORE_ENTRY_PADDING;
        
        TextEntity difficultyTextEntity = new TextEntity("DIFFICULTY", x, y);
        difficultyTextEntity.setColor(MenuUtil.MENU_COLOR);
        difficultyTextEntity.setFont(MenuUtil.MENU_ITEM_FONT);
        textEntities.add(difficultyTextEntity);
        
        x += HIGHSCORE_ENTRY_PADDING;
        
        TextEntity nameTextEntity = new TextEntity("NAME", x, y);
        nameTextEntity.setColor(MenuUtil.MENU_COLOR);
        nameTextEntity.setFont(MenuUtil.MENU_ITEM_FONT);
        textEntities.add(nameTextEntity);
        
        x += HIGHSCORE_ENTRY_PADDING;
        
        TextEntity dateTextEntity = new TextEntity("DATE", x, y);
        dateTextEntity.setColor(MenuUtil.MENU_COLOR);
        dateTextEntity.setFont(MenuUtil.MENU_ITEM_FONT);
        textEntities.add(dateTextEntity);
        
        return textEntities;
    }
    
    private List<TextEntity> createHighscoreTextEntries(HighscoreEntry entry, int rowIndex) {
        
        String formatedDate = Util.formatDate(entry.getCreatedAt());
        String name = entry.getPlayerName();
        int score = entry.getScore();
        
        int x = baseX;
        int y = highscoreBaseY + ((rowIndex + 1) * MENU_ITEM_VERTICAL_SPACING);
        
        List<TextEntity> textEntities = new ArrayList<>();
        
        TextEntity scoreTextEntity = new TextEntity(String.valueOf(score), x, y);
        scoreTextEntity.setColor(MenuUtil.MENU_COLOR);
        scoreTextEntity.setFont(MenuUtil.MENU_ITEM_FONT);
        textEntities.add(scoreTextEntity);
        
        x += HIGHSCORE_ENTRY_PADDING;
        
        
        TextEntity difficultyTextEntity = new TextEntity(entry.getDifficulty().toString(), x, y);
        difficultyTextEntity.setColor(MenuUtil.MENU_COLOR);
        difficultyTextEntity.setFont(MenuUtil.MENU_ITEM_FONT);
        textEntities.add(difficultyTextEntity);
        
        x += HIGHSCORE_ENTRY_PADDING;
        
        
        TextEntity nameTextEntity = new TextEntity(name, x, y);
        nameTextEntity.setColor(MenuUtil.MENU_COLOR);
        nameTextEntity.setFont(MenuUtil.MENU_ITEM_FONT);
        textEntities.add(nameTextEntity);
        
        x += HIGHSCORE_ENTRY_PADDING;
        
        TextEntity dateTextEntity = new TextEntity(formatedDate, x, y);
        dateTextEntity.setColor(MenuUtil.MENU_COLOR);
        dateTextEntity.setFont(MenuUtil.MENU_ITEM_FONT);
        textEntities.add(dateTextEntity);
        
        return textEntities;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public List<PaintableEntity> getEntities() {
        List<PaintableEntity> entityList = new ArrayList<>();
        
        entityList.addAll(menuItemEntityList);
        
        entityList.addAll(highscoreTextEntryList);
        
        return entityList;
    }
    
    
    
    @Override
    public EventListener[] getEventListeners() {
        return new EventListener[]{mouseListener, 
            mouseMotionListener};
    }
}