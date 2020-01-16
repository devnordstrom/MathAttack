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

/**
 *
 * @author Orville Nordström
 */
public class TextEntity implements PaintableEntity {

    private Color color;

    private Font font;

    private String text;

    /**
     * Used if you want to change fontSize without changing font.
     */
    private int fontSize;

    private int x, y;

    public TextEntity(String text, int x, int y) {
        this(text, x, y, null);
    }

    public TextEntity(String text, int x, int y, Color color) {

        this.text = text;

        this.x = x;

        this.y = y;

        this.color = color;

    }

    public int getX() {
        return this.x;
    }
    
    public void setX(int newX) {
        this.x = newX;
    }

    public int getY() {
        return this.y;
    }
    
    public void setY(int newY) {
        this.y = newY;
    }
    
    @Override
    public void paint(Graphics g) {

        Color originalColor = g.getColor();

        Font originalFont = g.getFont();

        if (color != null) {
            g.setColor(color);
        }

        if (font != null) {
            g.setFont(font);
        }

        if (fontSize != 0) {
            Font increasedSizeFont = new Font(originalFont.getName(), originalFont.getStyle(), fontSize);
            
            g.setFont(increasedSizeFont);
        }

        g.drawString(text, x, y);

        g.setColor(originalColor);

        g.setFont(originalFont);

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
     * @return the fontSize
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * @param fontSize the fontSize to set
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

}
