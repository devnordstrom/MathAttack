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
package se.devnordstrom.mathattack.mathproblem.type;

import java.util.Random;
import se.devnordstrom.mathattack.mathproblem.MathOperand;
import se.devnordstrom.mathattack.mathproblem.MathProblem;
import se.devnordstrom.mathattack.mathproblem.MathProblemUtil;

/**
 *
 * This class represents an addition problem that consists of n numbers combined with a + operator.
 * 
 * The number of numbers is 2 per default but may be set to a greater value.
 * 
 * @author Orville Nordström
 */
public class AdditionProblem extends MathProblem {
    
    @Deprecated //Should be handled by factory class.
    private int minValue, maxValue;
    
    private int numberCount = 2;
    
    public static int baseScore = 10;

    public AdditionProblem(Random rand) {
        super(rand);
    }
    
    @Override
    public void generateQuestion() {
        
        MathOperand operand = null;
        
        int maxCount = ((getNumberCount() * 2) - 1); //Since the number of operators + operands will always be this number.
        
        for(int i = 0; i < maxCount; i++) {
            
            if(i > 0 && (i + 1) % 2 == 0) {   //i > 0 since zero % 2 is also 0.

                operand = new MathOperand(MathProblem.OPERATOR_PLUS);
                
                operand.setOperator(true);
                
            } else {                
                
                operand = new MathOperand(rand.nextInt(maxValue) + minValue);
                
            }
         
            mathOperandList.add(operand);
                        
        }
        
        MathProblemUtil.setMathProblemAnswer(this);
        
    }
    
    public void setMinValue(int newMinValue) {
        this.minValue = newMinValue;
    }
    
    public int getMinValue() {
        return minValue;
    }
    
    public void setMaxValue(int newMaxValue) {
        this.maxValue = newMaxValue;
    }
    
    public int getMaxValue() {
        return maxValue;
    }
    
    public void setNumberCount(int newNumberCount) {
        this.numberCount = newNumberCount;
    }
    
    public int getNumberCount() {
        return this.numberCount;
    }
    
}