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
package se.devnordstrom.mathattack.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import se.devnordstrom.mathattack.gui.controller.EntityController;
import se.devnordstrom.mathattack.gui.entity.MenuItemEntity;

/**
 *
 * @author Orville Nordström
 */
public class MenuUtil {

    public static String DEFAULT_FONT_NAME = "Tahoma";
    
    public static final Color MENU_COLOR = Color.WHITE;
    
    private static final Color FOCUSED_MENU_COLOR = Color.GRAY;
    
    private static final Color ACTIVE_MENU_COLOR = Color.DARK_GRAY;
    
    private static final Color DISABLED_MENU_COLOR = new Color(50, 50, 50);
    
    public static final Font MENU_ITEM_FONT = new Font(DEFAULT_FONT_NAME, Font.PLAIN, 30);
    
    public static final int MENU_ITEM_WIDTH = 200;
    
    public static final int MENU_ITEM_HEIGHT = 50;
    
    private static final int MENU_TEXT_MARGIN_X = 0;
    
    private static final int MENU_TEXT_MARGIN_Y = 0;
    
    /**
     * 
     * @param x
     * @param y
     * @param text
     * @param action
     * @return 
     */
    public static MenuItemEntity createMenuItemEntity(int x, int y, 
            String text, Runnable action) {
        Rectangle menuBody = new Rectangle(x, y, MENU_ITEM_WIDTH, MENU_ITEM_HEIGHT);
        
        return createMenuItemEntity(menuBody, text, action);
    }  
    
    /**
     * 
     * @param rect
     * @param text
     * @param action
     * @return 
     */
    public static MenuItemEntity createMenuItemEntity(Rectangle rect, 
            String text, Runnable action) {
        
        int x = (int) rect.getX();
        int y = (int) rect.getY();
        
        MenuItemEntity menuItem = new MenuItemEntity();
        
        menuItem.setX(x);
        menuItem.setY(y);
                
        //Sets text properties.
        menuItem.setText(text);
        menuItem.setTextMarginX(MENU_TEXT_MARGIN_X);
        menuItem.setTextMarginY(MENU_TEXT_MARGIN_Y);
        menuItem.setColor(MENU_COLOR);
        menuItem.setFocusedColor(FOCUSED_MENU_COLOR);
        menuItem.setActiveColor(ACTIVE_MENU_COLOR);
        menuItem.setDisabledColor(DISABLED_MENU_COLOR);
        menuItem.setFont(MENU_ITEM_FONT);
        
        //Sets background properties.
        Rectangle background = new Rectangle(x, y, 
                (int) rect.getWidth(), (int) rect.getHeight());
        
        menuItem.setBackground(background);
        menuItem.setFillBackground(false);
        menuItem.setBackgroundColor(null);
        menuItem.setFocusedBackgroundColor(null);
        menuItem.setActiveBackgroundColor(null);
        
        menuItem.setAction(action);
        
        return menuItem;
        
    }
    
    /**
     * 
     * @param controller
     * @return 
     */
    public static MouseMotionListener getMenuMouseMotionListener(EntityController controller) {
        return new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                controller.setMouseCursorPosition(mouseEvent.getX(), mouseEvent.getY());
                
                GuiUtil.manageSetMenuItemFocused(mouseEvent.getX(), mouseEvent.getY(), 
                        controller.getEntities());
            }

            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                mouseMoved(mouseEvent);
            }
        };   
    }
    
    /**
     * 
     * @param controller
     * @return 
     */
    public static MouseListener getMenuMouseListener(EntityController controller) {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                GuiUtil.manageSetMenuItemActive(mouseEvent.getX(), mouseEvent.getY(), 
                        true, controller.getEntities());
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                GuiUtil.manageSetMenuItemActive(mouseEvent.getX(), mouseEvent.getY(),
                        false, controller.getEntities());
            }            
        }; 
    }
    
}