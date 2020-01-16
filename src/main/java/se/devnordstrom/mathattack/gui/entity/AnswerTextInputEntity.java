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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import se.devnordstrom.mathattack.util.Callable;

/**
 *
 * @author Orville Nordström
 */
public class AnswerTextInputEntity implements PaintableEntity {
    
    private static final int INPUT_CHARACTER_WIDTH = 15;
    
    private static final Font FONT = new java.awt.Font("MS Gothic", Font.PLAIN, INPUT_CHARACTER_WIDTH);
    
    private static final Color COLOR = Color.WHITE;
    
    private static final Color BORDER_COLOR = Color.WHITE;
    
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    
    private static final int TEXT_FIELD_BORDER_WIDTH = 2;
    
    private static final int DEFAULT_MAX_CHAR_LENGTH = 10;
    
    private static final boolean CARET_ENABLED = true;
    
    private static final char CARET_CHAR = '|';
    
    private static final long CARET_DISPLAY_INTERVAL_MILLIS = 500;
    
    private static final long CARET_DISPLAY_AFTER_INPUT_MILLIS = 250;
    
    private volatile long lastKeyInputTimeMillis = 0;
    
    private boolean visible = true;
    
    private int maxCharLength;
    
    private Rectangle rectangle;
    
    private Callable<String> answerCallable;
    
    private Callable<String> keyPressedCallable;
    
    private List<Character> characterList = new ArrayList<>();
    
    private KeyListener keyListener;
    
    public AnswerTextInputEntity(Rectangle rectangle) {
        this(rectangle, null);
    }
    
    /**
     * 
     * @param rectangle
     * @param answerCallable 
     */
    public AnswerTextInputEntity(Rectangle rectangle, Callable<String> answerCallable) {
        
        if(rectangle == null) {
            throw new IllegalArgumentException("The rectangle must not be null!");
        }
        
        this.rectangle = rectangle;
        
        this.answerCallable = answerCallable;
        
        this.maxCharLength = DEFAULT_MAX_CHAR_LENGTH;
        
        setKeyListener();
        
    }

    public int getX() {
        return (int) rectangle.getX();
    }
    
    public int getY() {
        return (int) rectangle.getY();
    }

    @Override
    public void paint(Graphics g) {
        
        Color orgColor = g.getColor();
        Font orgFont = g.getFont();
        
        paintTextField(g);
        
        g.setColor(orgColor);
        g.setFont(orgFont);
        
    }
    
    private void paintTextField(Graphics g) {
                
        //Paints border
        g.setColor(BORDER_COLOR);
        g.fillRect(getX(), getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight());
        
        
        //Paints background
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(getX() + TEXT_FIELD_BORDER_WIDTH, 
                getY() + TEXT_FIELD_BORDER_WIDTH, 
                (int) rectangle.getWidth() - (TEXT_FIELD_BORDER_WIDTH * 2), 
                (int) rectangle.getHeight() - (TEXT_FIELD_BORDER_WIDTH * 2));
        
        paintInput(g);
        
    }
    
    private void paintInput(Graphics g) {
        
        String text = getText();
        
        if(shouldShowCaret()) {
            text += CARET_CHAR;
        }

        int x = getX() + INPUT_CHARACTER_WIDTH + TEXT_FIELD_BORDER_WIDTH;
        int y = getY()  + INPUT_CHARACTER_WIDTH + TEXT_FIELD_BORDER_WIDTH;
        
        g.setFont(FONT);
        g.setColor(COLOR);
        g.drawString(text, x, y);
        
    }
    
    private boolean shouldShowCaret() {
        
        if(!CARET_ENABLED) {
            return false;
        }
        
        if(isUserTyping()) {
            return true;
        }
        
        return (System.currentTimeMillis() / CARET_DISPLAY_INTERVAL_MILLIS) % 2 == 0;        
        
    }
    
    private void setKeyListener() {
                
        keyListener = new KeyAdapter() {
    
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                
                if(!visible) {
                    System.err.println("Returning from AnswerInputTextField keyPressed because visible: false;");
                    return;
                }
                
                lastKeyInputTimeMillis = System.currentTimeMillis();
                                
                switch(keyEvent.getKeyCode()) {
                    
                    case KeyEvent.VK_ENTER:
                        fireInput();
                        break;
                    
                    case KeyEvent.VK_BACK_SPACE:
                    case KeyEvent.VK_DELETE:
                        removeLastCharacter();
                        break;
                        
                    default:
                        if(isLegalChar(keyEvent.getKeyChar())) {
                            addCharacter(keyEvent.getKeyChar());
                        }
                        
                }
                
                if(keyPressedCallable != null) keyPressedCallable.call(getText());
                        
            }
            
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                
                if(!visible) return;
                
                //Does nothing as of now.
                
            }
            
        };
        
    }
    
    public KeyListener getKeyListener() {
        if(keyListener == null) throw new IllegalStateException();
        return keyListener;
    }
    
    private boolean isLegalChar(char c) {
        return c == ' '
                || c == '"'
                || c == '\''
                || Character.isDigit(c) 
                || Character.isAlphabetic(c);
    }
    
    private boolean isUserTyping() {
        
        long compareToNow = (lastKeyInputTimeMillis + CARET_DISPLAY_AFTER_INPUT_MILLIS);
        
        long now = System.currentTimeMillis();
        
        return compareToNow >= now;
                
    }
    
    public String getText() {
        
        StringBuilder sb = new StringBuilder();
        
        for(Character character : characterList) {
            sb.append(character);
        }
        
        return sb.toString();
        
    }
    
    private void clearText() {
        characterList.clear();
    }
    
    private void removeLastCharacter() {
        
        if(characterList.isEmpty()) {
            return;
        }
        
        characterList.remove(characterList.size() - 1);
        
    }
    
    private void addCharacter(char c) {
        
        if(characterList.size() > getMaxCharLength()) {
            
            removeLastCharacter();
            
            return;
            
        }
        
        characterList.add(c);
        
    }
    
    private void fireInput() {
        
        if(answerCallable == null) {
            return;
        }
        
        answerCallable.call(getText());
        
        clearText();
        
    }

    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * @return the maxCharLength
     */
    public int getMaxCharLength() {
        return maxCharLength;
    }

    /**
     * @param maxCharLength the maxCharLength to set
     */
    public void setMaxCharLength(int maxCharLength) {
        this.maxCharLength = maxCharLength;
    }
    
    @Override
    public String toString() {
        return getText();
    }
    
}