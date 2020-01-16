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
package se.devnordstrom.mathattack.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import se.devnordstrom.mathattack.gui.controller.EntityController;
import se.devnordstrom.mathattack.gui.entity.PaintableEntity;
import se.devnordstrom.mathattack.logic.GameLoopController;

/**
 *
 * @author Orville Nordström
 */
public class GameScreenController extends JPanel {
    
    private static final Color DEFAULT_COLOR = Color.WHITE;
    
    private final int prefWidth, prefHeight;
    
    private volatile EntityController entityController;
    
    private final GameLoopController gameLoopController; 
    
    private Thread gameLoopThread;
    
    /**
     * Used for repainting incase user minimizes window.
     */
    private Image gameScreenImage;
    
    /**
     * Used for repainting incase user moves or minimizes window.
     */
    private Graphics gameScreenGraphics;
    
    public GameScreenController(int prefWidth, int prefHeight) {
        this.prefWidth = prefWidth;
        
        this.prefHeight = prefHeight;
        
        setGuiProperties();
        
        gameLoopController = new GameLoopController(this);
    }
    
    public void start() {
        if(gameLoopThread == null) {
            gameLoopThread = new Thread(gameLoopController);

            gameLoopThread.start();
        }
    }
    
    private void setGuiProperties() {
        this.setSize(new Dimension(prefWidth, prefHeight));
        this.setPreferredSize(new Dimension(prefWidth, prefHeight));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
    }
    
    
    /**
     * @return the entityController
     */
    public EntityController getEntityController() {
        return entityController;
    }

    /**
     * @param entityController the entityController to set
     */
    public void setEntityController(EntityController entityController) {
        this.entityController = entityController;
    }
    
    @Override
    public void paint(Graphics g) {
        gameScreenImage = createImage(getWidth(), getHeight());
        gameScreenGraphics = gameScreenImage.getGraphics();
        paintComponent(gameScreenGraphics);
        g.drawImage(gameScreenImage, 0, 0, this);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(DEFAULT_COLOR);
        
        if(entityController != null) {
            for(PaintableEntity paintableEntity : entityController.getEntities()) {
                if(null != paintableEntity) paintableEntity.paint(g);
            }
        }
        
        repaint();
    }
    
    public void moveEntities(double delta, long updateLengthNanos) {
        if(entityController != null) entityController.moveEntities(delta, updateLengthNanos);
    }
}