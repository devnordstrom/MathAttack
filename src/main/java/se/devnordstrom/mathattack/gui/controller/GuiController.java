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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowStateListener;
import java.util.EventListener;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import se.devnordstrom.mathattack.App;
import se.devnordstrom.mathattack.gui.GameScreenController;
import se.devnordstrom.mathattack.difficulty.GameDifficulty;
import se.devnordstrom.mathattack.highscore.HighscoreEntry;
import se.devnordstrom.mathattack.util.Callable;

/**
 *
 * @author Orville Nordström
 */
public class GuiController {
    
    private static final int FRAME_WIDTH = 900;
    
    private static final int FRAME_HEIGHT = 700;
    
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.black;
    
    private static final int BASE_X = 75;
    
    private static final int BASE_Y = 50;

    private final GameScreenController gameScreenController;

    private final Runnable showHighscoreRunnable = () -> {
        showHighscore(null);
    };
    
    private MenuController menuController;
    
    private GameController gameController;
  
    private GameOverController gameOverController;
    
    private HighscoreGuiController highscoreGuiController;
    
    private final JFrame mainJFrame;
    
    public GuiController() {
        mainJFrame = new JFrame(App.APP_NAME);
        
        gameScreenController = new GameScreenController(mainJFrame.getWidth(), 
                mainJFrame.getHeight());
        
        setMainFrameProperties();
    }
    
    private void setMainFrameProperties() {
        mainJFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainJFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        mainJFrame.setBackground(DEFAULT_BACKGROUND_COLOR);
        mainJFrame.setResizable(false);
        mainJFrame.setLayout(new BorderLayout());
        mainJFrame.setLocationRelativeTo(null);    //Centers the frame.        
        
        Container container = mainJFrame.getContentPane();
        container.add(gameScreenController, BorderLayout.CENTER);
    }
    
    /**
     * Starts the gui and logic also shows the menu.
     */
    public void startGui() {
        gameScreenController.start();
        
        mainJFrame.setVisible(true);
        mainJFrame.requestFocus();
                
        showMenu();
    }
    
    /**
     * 
     */
    public void showMenu() {
        if(menuController == null) {
            setMenuController();
        }
        
        setController(menuController);        
    }
    
    private void setMenuController() {
        menuController = new MenuController(BASE_X, BASE_Y, 
                getStartGameCallable(), showHighscoreRunnable);
    }   
    
    private Callable<GameDifficulty> getStartGameCallable() {
        return (GameDifficulty gameDifficulty) -> {
            startGame(gameDifficulty);
        };
    }
    
    /**
     * 
     * @param entry 
     */
    private void showGameOverController(HighscoreEntry entry) {
        gameOverController = new GameOverController(BASE_X, BASE_Y, entry,
                getRepaintRunnable(), getShowHighscoreCallable(), getShowMenuRunnable());
        
        setController(gameOverController);
    }
        
    private Callable<HighscoreEntry> getShowGameoverCallable() {
        return (HighscoreEntry entry) -> {
            showGameOverController(entry);
        };
    }
    
    private Callable<GameDifficulty> getShowHighscoreCallable() {
        return (GameDifficulty difficulty) -> {
            showHighscore(null);
        };
    }
    
    /**
     * 
     * @param difficulty 
     */
    private void showHighscore(GameDifficulty difficulty) { 
        highscoreGuiController = new HighscoreGuiController(getShowMenuRunnable(), 
                difficulty, BASE_X, BASE_Y);
        
        setController(highscoreGuiController);
    }
    
    private Runnable getRepaintRunnable() {
        return () -> {
            gameScreenController.repaint();
        };
    }
    
    private Runnable getShowMenuRunnable() {
        return () -> {
            showMenu();
        };
    }
    
    private void startGame(GameDifficulty gameDifficulty) {
        gameController = new GameController(mainJFrame.getWidth(), 
                mainJFrame.getHeight(), 
                getShowGameoverCallable());
                
        gameController.setDifficulty(gameDifficulty);
        
        gameController.setGroundSurfaceY(mainJFrame.getHeight() - 100);
        
        setController(gameController);
    }
    
    /**
     * 
     * @param controller 
     */
    private void setController(EntityController controller) {
        if(controller == null) {
            throw new IllegalArgumentException("The controller must not be null!");
        }
        
        resetListeners();
        
        gameScreenController.setEntityController(controller);

        addListeners(controller.getEventListeners());
    }
    
    private void resetListeners() {
        for(MouseListener mouseListener : mainJFrame.getMouseListeners()) {
            mainJFrame.removeMouseListener(mouseListener);
        }
        
        for(MouseMotionListener mouseMotionListener : mainJFrame.getMouseMotionListeners()) {
            mainJFrame.removeMouseMotionListener(mouseMotionListener);
        }
        
        for(MouseWheelListener mouseWheelListener : mainJFrame.getMouseWheelListeners()) {
            mainJFrame.removeMouseWheelListener(mouseWheelListener);
        }
        
        for(KeyListener keyListener : mainJFrame.getKeyListeners()) {
            mainJFrame.removeKeyListener(keyListener);
        }
    }
    
    /**
     * 
     * @param eventListenerArray 
     */
    public void addListeners(EventListener[] eventListenerArray) {
        for(EventListener eventListener : eventListenerArray) {
            addListener(eventListener);
        }
    }
    
    /**
     * 
     * @param eventListener 
     */
    public void addListener(EventListener eventListener) {
        if(eventListener == null) {
            throw new IllegalArgumentException("The eventListener must not be null.");
        }
        
        if(eventListener instanceof MouseListener) {
            mainJFrame.addMouseListener((MouseListener) eventListener);
        } else if(eventListener instanceof MouseMotionListener) {
            mainJFrame.addMouseMotionListener((MouseMotionListener) eventListener);
        } else if(eventListener instanceof MouseWheelListener) {
            mainJFrame.addMouseWheelListener((MouseWheelListener) eventListener);
        } else if(eventListener instanceof KeyListener) {
            mainJFrame.addKeyListener((KeyListener) eventListener);
        } else {
            throw new UnsupportedOperationException("Event listener of class: " 
                    + eventListener.getClass() + " not supported!");
        }
    }
    
}