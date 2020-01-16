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

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import se.devnordstrom.mathattack.difficulty.GameDifficulty;
import se.devnordstrom.mathattack.gui.entity.AnswerTextInputEntity;
import se.devnordstrom.mathattack.gui.entity.MenuItemEntity;
import se.devnordstrom.mathattack.gui.entity.PaintableEntity;
import se.devnordstrom.mathattack.gui.entity.TextEntity;
import se.devnordstrom.mathattack.highscore.HighscoreController;
import se.devnordstrom.mathattack.highscore.HighscoreEntry;
import se.devnordstrom.mathattack.util.Callable;
import se.devnordstrom.mathattack.util.GuiUtil;
import se.devnordstrom.mathattack.util.MenuUtil;

/**
 *
 * @author Orville Nordström
 */
public class GameOverController extends EntityController {
    
    private static final Color COLOR = Color.WHITE;
    
    private static final Font FONT = new Font("Tahoma", Font.PLAIN, 30);
    
    private static final int MENU_ITEM_SPACING = 100;
    private static final int STAT_TEXT_VERTICAL_MARGIN = 50;
    private static final int PROMPT_NAME_INPUT_WIDTH = 150;
    private static final int PROMPT_NAME_INPUT_HEIGHT = 30;
    private static final int PROMPT_NAME_MAX_CHAR_LENGTH = 20;
    
    private boolean active;
    
    private int baseX, baseY;
    
    private List<PaintableEntity> entityList = new ArrayList<>();
        
    private MouseListener mouseListener;
    
    private MouseMotionListener mouseMotionListener;
        
    private AnswerTextInputEntity answerInputTextEntity;
    
    private HighscoreEntry highscoreEntry;
    
    private Runnable repaintRunnable, showMenuRunnable;
    
    private Callable<GameDifficulty> showHighscoreCallable;
    
    public GameOverController(int baseX, int baseY, 
            HighscoreEntry highscoreEntry,
            Runnable repaintRunnable,
            Callable<GameDifficulty> showHighscoreCallable, 
            Runnable showMenuRunnable) {

        if(highscoreEntry == null) {
            throw new IllegalArgumentException("The highscore entry must not be null!");
        }
        
        if(highscoreEntry.getDifficulty() == null) {
            throw new IllegalArgumentException("The difficulty for the entry must be set!");
        }
        
        
        this.baseX = baseX;
        
        this.baseY = baseY;
                
        this.highscoreEntry = highscoreEntry;
                
        this.repaintRunnable = repaintRunnable;
        
        this.showHighscoreCallable = showHighscoreCallable;
        
        this.showMenuRunnable = showMenuRunnable;
        
        this.setMouseListeners();
        
        this.setEntities();
    }
    
    private void setEntities() {
        
        int index = 0;
        
        entityList.add(createTextEntity(getHeaderText(), index++));

        
        List<String> gameStats = getGameStatText();
        for(String gameStat : gameStats) {
            entityList.add(createTextEntity(gameStat, index++));   
        }
        
        MenuItemEntity menuItem = createMenuItem("Back to the menu", index++, showMenuRunnable);
        entityList.add(menuItem);
                
        // this.showMenuRunnable
        
        
        
        //Controls/menuitems related to the highscore.
        if(isQualifiedForHighscore()) {
            
            TextEntity promptNameTextEntity = createTextEntity(getPromptNameText(), index++);

            createAnswerInputEntity(promptNameTextEntity.getX(), 
                    promptNameTextEntity.getY());
            
            
            MenuItemEntity addToHighscoreEntity = createMenuItem("ADD TO HIGHSCORE", index++, ()-> {
                    manageAddEntryToHighscore();
                    });
            
            
            entityList.add(promptNameTextEntity);
            entityList.add(answerInputTextEntity);
            entityList.add(addToHighscoreEntity);
            
        } else {
            
            entityList.add(createTextEntity(getWhyNotQualifiedInfoText(), index++));
            
        }
  
        MenuItemEntity showHighscoreMenuEntity = createMenuItem("SHOW HIGHSCORE", index++, ()-> {
                    goToHighscore();
                    });
        
        entityList.add(showHighscoreMenuEntity);   
        
    }
    
    private boolean isQualifiedForHighscore() {
        return HighscoreController.isQualifiedForHighScore(highscoreEntry);
    }
    
    private String getWhyNotQualifiedInfoText() {
        
        HighscoreEntry entryToBeat = HighscoreController.
                getLowestQualifyingHighscoreForDifficulty(highscoreEntry.getDifficulty());
        
        if(entryToBeat == null) {
            return "Could not read highscore info.";
        }

        return "To qualify for highscore you need atleast " + (entryToBeat.getScore() + 1) + " points.";
        
    }
    
    private String getHeaderText() {
        return "GAMOEVER! Points: " + highscoreEntry.getScore() + ", "
                + "Wave: " + highscoreEntry.getCompletedWaves() + ".";
    }
    
    private List<String> getGameStatText() {
        
        int answerCount = highscoreEntry.getAnswerCount();
        int solvedProblemCount = highscoreEntry.getSolvedProblemCount();
        double solvedProblemsPerAnswerRatio = ((double) solvedProblemCount) / ((double) answerCount);
                
        if(Double.isNaN(solvedProblemsPerAnswerRatio)) {
            solvedProblemsPerAnswerRatio = 0;
        }
        
        List<String> gameStats = new ArrayList<>();
        gameStats.add("Points: "+highscoreEntry.getScore());
        gameStats.add("Waves: " + highscoreEntry.getCompletedWaves());
        gameStats.add("Answers: " + answerCount );
        gameStats.add("solved problems: " + solvedProblemCount);
        gameStats.add("solved problems/answer: " + String.format("%.2f", solvedProblemsPerAnswerRatio));
        
        return gameStats;
    }
    
    private String getPromptNameText() {
        return "What is your name?";
    }
    
    private TextEntity createTextEntity(String text, int index) {
        TextEntity textEntity = new TextEntity(text, baseX, 
                baseY + (index * STAT_TEXT_VERTICAL_MARGIN));
        textEntity.setColor(COLOR);
        textEntity.setFont(FONT);
        
        return textEntity;
    }
    
    private AnswerTextInputEntity createAnswerInputEntity(int x, int y) {
        Rectangle answerInputRectangle = new Rectangle(x, y, 
                PROMPT_NAME_INPUT_WIDTH, PROMPT_NAME_INPUT_HEIGHT);
        
        answerInputTextEntity = new AnswerTextInputEntity(answerInputRectangle, (String value) -> {
                manageAddEntryToHighscore();
                });
        
        answerInputTextEntity.setMaxCharLength(PROMPT_NAME_MAX_CHAR_LENGTH);
        
        return answerInputTextEntity;
    }
    
    
    private MenuItemEntity createMenuItem(String text, int index, Runnable action) {
        
        int x = baseX;
        
        int y = baseY + (MENU_ITEM_SPACING * index);
        
        return MenuUtil.createMenuItemEntity(x, y, text, action);
        
    }
    
    
    /**
     * 
     * @return 
     */
    public List<PaintableEntity> getEntities() {
        return entityList;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    
    private void setMouseListeners() {
        mouseMotionListener = MenuUtil.getMenuMouseMotionListener(this);
        
        mouseListener = MenuUtil.getMenuMouseListener(this);
    }

    private void manageAddEntryToHighscore() {
        if(answerInputTextEntity.getText().trim().isEmpty()) {
            System.err.println("Returning from manageAddEntryToHighscore() because "
                    + "the answerInputTextEntity is empty.");
            return;
        }
        
        highscoreEntry.setPlayerName(answerInputTextEntity.getText());
        
        HighscoreController.addHighscoreEntry(highscoreEntry);
        
        goToHighscore();
        
    }
    
    private void goToHighscore() {
        System.out.println("goToHighscore() started!");
        
        active = false;
        
        showHighscoreCallable.call(highscoreEntry.getDifficulty());
    }
            
    
    /**
     * 
     * @return 
     */
    public MouseListener getMouseListener() {
        return mouseListener;
    }
    
    /**
     * 
     * @return 
     */
    public MouseMotionListener getMouseMotionListener() {
        return mouseMotionListener;
    }
    
    @Override
    public void moveEntities(double delta, long updateLengthNanos) {
        
    }
    
    @Override
    public EventListener[] getEventListeners() {
        return new EventListener[]{mouseListener, 
            mouseMotionListener, answerInputTextEntity.getKeyListener()};
    }    
}