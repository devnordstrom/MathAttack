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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import se.devnordstrom.mathattack.App;
import se.devnordstrom.mathattack.gui.entity.MenuItemEntity;
import se.devnordstrom.mathattack.gui.entity.PaintableEntity;
import se.devnordstrom.mathattack.gui.entity.TextEntity;
import se.devnordstrom.mathattack.difficulty.GameDifficulty;
import se.devnordstrom.mathattack.util.Callable;
import se.devnordstrom.mathattack.util.GuiUtil;
import se.devnordstrom.mathattack.util.MenuUtil;
import se.devnordstrom.mathattack.util.Util;

/**
 *
 * @author Orville Nordström
 */
public class MenuController extends EntityController {
    
    //Text colors for the menu items.
    
    private static final Font WELLCOME_TEXT_FONT = new Font("Tahoma", Font.PLAIN, 40);    
    
    private static final int MENU_ITEM_SPACING = 75;
    
    private List<PaintableEntity> entityList = new ArrayList<>();
        
    private final int baseX, baseY;
    
    private boolean active;
        
    private final Callable<GameDifficulty> difficultyCallback;
            
    private MouseMotionListener mouseMotionListener;
    
    private MouseListener mouseListener;
     
    private Runnable showHighscoreRunnable;
     
    public MenuController(int baseX, int baseY, 
            Callable<GameDifficulty> difficultyCallback,
            Runnable showHighscoreRunnable) {
        this.baseX = baseX;
        
        this.baseY = baseY;
        
        this.difficultyCallback = difficultyCallback;
                
        this.showHighscoreRunnable = showHighscoreRunnable;
                
        setListeners();
        
        setMenuItems();
    }
    
    /**
     * 
     */
    private void setListeners() {
        mouseListener = MenuUtil.getMenuMouseListener(this);
        
        mouseMotionListener = MenuUtil.getMenuMouseMotionListener(this);
    }
    
    /**
     * 
     */
    private void setMenuItems() {
        int menuIndex = 0;
        
        TextEntity wellcomeText = new TextEntity("Wellcome to MathAttack", baseX, baseY, Color.WHITE);
        wellcomeText.setFont(WELLCOME_TEXT_FONT);
        menuIndex++;
        
        MenuItemEntity normalDiffMenuItem = createMenuItemEntity("NORMAL", menuIndex++, ()-> {
                    startGame(GameDifficulty.NORMAL);
                });
        
        
        MenuItemEntity hardDiffMenuItem = createMenuItemEntity("HARD", menuIndex++, ()-> {
                    startGame(GameDifficulty.HARD);
                });
        
        
        MenuItemEntity extremeDiffMenuItem = createMenuItemEntity("EXTREME", menuIndex++, ()-> {
                    startGame(GameDifficulty.EXTREME);
                });
        
        
        MenuItemEntity helpMenuItem = createMenuItemEntity("HELP", menuIndex++, ()-> {
                    showHelpInfo();
                });
        
        
        MenuItemEntity highscoreMenuItem = createMenuItemEntity("HIGHSCORE", menuIndex++, ()-> {
                    showHighScore();
                });
        
        
        MenuItemEntity quitMenuItem = createMenuItemEntity("EXIT", menuIndex++, ()-> {
                    App.exitApplication();
                });
        
        entityList.add(wellcomeText);
        entityList.add(normalDiffMenuItem);
        entityList.add(hardDiffMenuItem);
        entityList.add(extremeDiffMenuItem);
        entityList.add(helpMenuItem);
        entityList.add(highscoreMenuItem);
        entityList.add(quitMenuItem);
    }
    
    /**
     * 
     * @param text
     * @param index
     * @param action
     * @return 
     */
    private MenuItemEntity createMenuItemEntity(String text, int index, Runnable action) {
        int x = baseX;
        int y = baseY + (MENU_ITEM_SPACING * index);
        
        return MenuUtil.createMenuItemEntity(x, y, text, action);
    }
    
    private void startGame(GameDifficulty difficulty) {
        difficultyCallback.call(difficulty);        
    }
    
    private void showHelpInfo() {
        Util.showMessage(App.HELP_TEXT, App.APP_NAME);
    }
    
    private void showHighScore() {
        showHighscoreRunnable.run();
    }
    
    /**
     * 
     * @return 
     */
    public MouseMotionListener getMouseMotionListener() {
        return mouseMotionListener;
    }
    
    /**
     * 
     * @return 
     */
    public MouseListener getMouseListener() {
        return mouseListener;
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
    
    @Override
    public List<PaintableEntity> getEntities() {
        return entityList;
    }
    
    @Override
    public EventListener[] getEventListeners() {
        return new EventListener[]{mouseMotionListener, mouseListener};
    }
}