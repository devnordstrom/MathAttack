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
package se.devnordstrom.mathattack.gui.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Orville Nordström
 */
public class MenuItemEntity implements PaintableEntity {

    private static final boolean PAINT_OUTLINE = false;
    
    private int x, y, textMarginX, textMarginY;
    
    private String text;
    
    private boolean focused, active, disabled, fillBackground;
    
    private Runnable action;
    
    private Font font;
        
    private Color color, focusedColor, activeColor, disabledColor, backgroundColor, 
            focusedBackgroundColor, activeBackgroundColor, disabledBackgroundColor;

    private Rectangle background;
       
    public MenuItemEntity() {
        
    }
    
    public MenuItemEntity(String text) {
        this.text = text;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * @return the textMarginX
     */
    public int getTextMarginX() {
        return textMarginX;
    }
    
    /**
     * @param textMarginX the textMarginX to set
     */
    public void setTextMarginX(int textMarginX) {
        this.textMarginX = textMarginX;
    }
    
    /**
     * @return the textMarginY
     */
    public int getTextMarginY() {
        return textMarginY;
    }

    /**
     * @param textMarginY the textMarginY to set
     */
    public void setTextMarginY(int textMarginY) {
        this.textMarginY = textMarginY;
    }
    
    @Override
    public void paint(Graphics g) {
        
        Color originalColor = g.getColor();
        
        Font originalFont = g.getFont();
        
        if(font != null) g.setFont(font);
        
        if(isDisabled()) {
            paintDisabledEntity(g);
        } else if(isActive()) {
            paintActiveEntity(g);
        } else if(isFocused()) {
            paintFocusedEntity(g);
        } else {
            paintEntity(g);
        }
          
        if(PAINT_OUTLINE) {
            g.setColor(Color.RED);
            g.drawRect((int)background.getX(), (int)background.getY(),
                    (int)background.getWidth(), (int)background.getHeight());
        }
        
        g.setColor(originalColor);
        
        g.setFont(originalFont);
    }
    
    private void paintDisabledEntity(Graphics g) {
        
        if(getDisabledBackgroundColor() != null) {
            paintBackground(g, getDisabledBackgroundColor());
        }
        
        paintText(g, disabledColor);
        
    }
    
    private void paintActiveEntity(Graphics g) {
        
        if(activeBackgroundColor != null) {
            paintBackground(g, activeBackgroundColor);
        }
        
        paintText(g, activeColor);
        
    }
    
    private void paintFocusedEntity(Graphics g) {
        
        if(focusedBackgroundColor != null) {
            paintBackground(g, focusedBackgroundColor);
        }
        
        paintText(g, focusedColor);
        
    }
    
    private void paintEntity(Graphics g) {
        
        if(backgroundColor != null) {
            paintBackground(g, backgroundColor);
        }
        
        paintText(g, color);
        
    }
    
    private void paintBackground(Graphics g, Color color) {
        
        if(background == null || color == null) {
            return;
        }
        
        g.setColor(color);
        
        if(isFillBackground()) {
            g.fillRect(x, y, (int) background.getWidth(), (int) background.getHeight());
        } else {
            g.drawRect(x, y, (int) background.getWidth(), (int) background.getHeight());
        }
            
    }
    
    private void paintText(Graphics g, Color argColor) {
        
        if(argColor != null) {
            g.setColor(argColor);
        } else if(color != null) {
            g.setColor(color);
        }

        g.drawString(text, x + textMarginX, y + textMarginY);
        
    }
    
    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the focused
     */
    public boolean isFocused() {
        return focused;
    }

    /**
     * @param focused the focused to set
     */
    public void setFocused(boolean focused) {
        this.focused = focused;
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
    
    /**
     * @return the disabled
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * @param disabled the disabled to set
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
    
    /**
     * @return the fillBackground
     */
    public boolean isFillBackground() {
        return fillBackground;
    }

    /**
     * @param fillBackground the fillBackground to set
     */
    public void setFillBackground(boolean fillBackground) {
        this.fillBackground = fillBackground;
    }
    
    /**
     * @return the action
     */
    public Runnable getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(Runnable action) {
        this.action = action;
    }

    /**
     * @return the font
     */
    public Font getFont() {
        return font;
    }

    /**
     * @param font the font to set
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the focusedColor
     */
    public Color getFocusedColor() {
        return focusedColor;
    }

    /**
     * @param focusedColor the focusedColor to set
     */
    public void setFocusedColor(Color focusedColor) {
        this.focusedColor = focusedColor;
    }
    
    /**
     * @return the activeColor
     */
    public Color getActiveColor() {
        return activeColor;
    }

    /**
     * @param activeColor the activeColor to set
     */
    public void setActiveColor(Color activeColor) {
        this.activeColor = activeColor;
    }
    
    /**
     * @return the disabledColor
     */
    public Color getDisabledColor() {
        return disabledColor;
    }

    /**
     * @param disabledColor the disabledColor to set
     */
    public void setDisabledColor(Color disabledColor) {
        this.disabledColor = disabledColor;
    }
    
    /**
     * @return the rectanlge
     */
    public Rectangle getBackground() {
        return background;
    }
    
    /**
     * @param background the background to set
     */
    public void setBackground(Rectangle background) {
        this.background = background;
    }
    
    /**
     * @return the focusedBackgroundColor
     */
    public Color getFocusedBackgroundColor() {
        return focusedBackgroundColor;
    }

    /**
     * @param focusedBackgroundColor the focusedBackgroundColor to set
     */
    public void setFocusedBackgroundColor(Color focusedBackgroundColor) {
        this.focusedBackgroundColor = focusedBackgroundColor;
    }
    
    /**
     * @return the backgroundColor
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * @param backgroundColor the backgroundColor to set
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    /**
     * @return the activeBackgroundColor
     */
    public Color getActiveBackgroundColor() {
        return activeBackgroundColor;
    }

    /**
     * @param activeRectangleColor the activeBackgroundColor to set
     */
    public void setActiveBackgroundColor(Color activeRectangleColor) {
        this.activeBackgroundColor = activeRectangleColor;
    }
    
    /**
     * @return the disabledBackgroundColor
     */
    public Color getDisabledBackgroundColor() {
        return disabledBackgroundColor;
    }

    /**
     * @param disabledBackgroundColor the disabledBackgroundColor to set
     */
    public void setDisabledBackgroundColor(Color disabledBackgroundColor) {
        this.disabledBackgroundColor = disabledBackgroundColor;
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return 
     */
    public boolean isOverlapping(int x, int y) {
        
        if(background == null) return false;
        
        return background.contains(x, y);
        
    }
    
}