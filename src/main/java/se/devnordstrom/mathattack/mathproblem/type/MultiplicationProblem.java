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
import se.devnordstrom.mathattack.mathproblem.*;

/**
 *
 * @author Orville Nordström
 */
public class MultiplicationProblem extends MathProblem {
    
    private int maxValue = EASY_OPERAND_MAX;
    
    private int minValue = 0;   

    private int numberCount = 2;
    
    public static int baseScore = 40;

    public MultiplicationProblem(Random rand) {
        super(rand);
    }
    
    @Override
    public void generateQuestion() {
        
        MathOperand operand = null;
        
        for(int i = 0; i < 3; i++) {
            
            if(i > 0 && (i + 1) % 2 == 0) {
                                                
                operand = new MathOperand(MathProblem.OPERATOR_MULTIPLY);
                                
                operand.setOperator(true);
                
            } else {
                
                operand = new MathOperand(rand.nextInt(getMaxValue()) + getMinValue());
                
            }
         
            mathOperandList.add(operand);
            
        }
                
        MathProblemUtil.setMathProblemAnswer(this);
        
    }

    /**
     * @return the maxValue
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * @param maxValue the maxValue to set
     */
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * @return the minValue
     */
    public int getMinValue() {
        return minValue;
    }

    /**
     * @param minValue the minValue to set
     */
    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    /**
     * @return the numberCount
     */
    public int getNumberCount() {
        return numberCount;
    }

    /**
     * @param numberCount the numberCount to set
     */
    public void setNumberCount(int numberCount) {
        this.numberCount = numberCount;
    }
    
    
    
}
