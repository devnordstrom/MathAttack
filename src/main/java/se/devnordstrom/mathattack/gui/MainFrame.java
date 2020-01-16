/*
 * Copyright (C) 2016 Orville Nordström
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JTextField;
import se.devnordstrom.mathattack.gui.controller.GuiController;

/**
 *
 * @author Orville Nordström
 */
/**
 *
 * @author Developer
 */
public class MainFrame extends JFrame {
    
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.BLACK;
    
    private int width, height;
        
    public MainFrame() {
        super("Math Attack");
        
        this.width = 900;
        this.height = 700;
    }
    
    public void createGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        setBackground(DEFAULT_BACKGROUND_COLOR);
        setResizable(false);
        
        setLayout(new BorderLayout());
        
        setLocationRelativeTo(null);    //Centers the frame.
        
        setCursor(Cursor.HAND_CURSOR);
        
        setVisible(true);
        
        
        
        requestFocus();
    }
}