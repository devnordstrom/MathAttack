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
package se.devnordstrom.mathattack.mathproblem;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * This interface represents the math problems.
 * 
 * @author Orville Nordström
 */
public interface MathProblemInterface {
    
    public static final int DIFFICULTY_EASY = 1;
    
    public static final int DIFFICULTY_NORMAL = 2;
    
    public static final int DIFFICULTY_HARD = 3;
    
    public static final int DIFFICULTY_VERY_HARD = 4;
    
    public static final int DIFFICULTY_EXTREME = 5;
    
    public static final int DIFFICULTY_INSANE = 6;
    
    public static final int OPERATOR_PLUS = 1;
    
    public static final int OPERATOR_MINUS = 2;
    
    public static final int OPERATOR_DIVIDE = 3;
    
    public static final int OPERATOR_MULTIPLY = 4;
    
    public static final int OPERATOR_REMAINDER = 5;
    
    public static final int OPERATOR_EQUALS = 6;
    
    public int getX();
    
    public void setX(int newX);
    
    public int getY();
    
    public void setY(int newY);
    
    public void drawComponent(Graphics g);
    
    public boolean compareAnswer(Object answer);
    
    public void setAnswerString(String newAnswer);
    
    public String getAnswerString();
    
    public void setAnswer(double answer);
    
    public void moveProblem(double delta);
    
    public void hit();
    
    public int getHp();
    
    public int getDifficult();
    
    public boolean isAlive();
    
    public ArrayList<MathOperand> getMathOperands();
    
}
