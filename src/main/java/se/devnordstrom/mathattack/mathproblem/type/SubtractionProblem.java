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
 * @author Orville Nordström
 */
public class SubtractionProblem extends MathProblem {
    
    private boolean differenceMustBePossitive = true;
     
    private int minMinuend = 2;
    
    private int maxMinuend = EASY_OPERAND_MAX;
    
    private int minSubtrahend = 2;
    
    private int maxSubtrahend = EASY_OPERAND_MAX - 2;
    
    private int numberCount = 2;
    
    public static int baseScore = 20;

    public SubtractionProblem(Random rand) {
        super(rand);
    }
    
    @Override
    public void generateQuestion() {
        
        MathOperand operand = null;
        
        int maxCount = ((numberCount * 2) - 1); //Since the number of operators + operands will always be this number.
        
        int totalValue = 0;
        
        int subtrahendVal = 0;
        
        for(int i = 0; i < maxCount; i++) {
            
            if(i > 0 && (i + 1) % 2 == 0) {   //i > 0 since zero % 2 is also 0.

                operand = new MathOperand(MathProblem.OPERATOR_MINUS);
                
                operand.setOperator(true);
                                
            } else if(i == 0) {
                
                operand = new MathOperand(rand.nextInt(maxMinuend) + minMinuend);
                
                totalValue += operand.getValue();
                
            } else {    //that means it is a subtrahend. Or not the first value.
                
                subtrahendVal = rand.nextInt(maxMinuend) + minMinuend;

                if((totalValue - subtrahendVal) < 0) {
                    subtrahendVal += (totalValue - subtrahendVal);     //Since this is always negative this will decrease the value.
                }
                
                operand = new MathOperand(subtrahendVal);
                
            }
         
            mathOperandList.add(operand);
            
        }
        
        MathProblemUtil.setMathProblemAnswer(this);
        
    }
    
    public void setNumberCount(int newNumberCount) {
        this.numberCount = newNumberCount;
    }
    
    public int getNumberCount() {
        return this.numberCount;
    }

    /**
     * @return the differenceMustBePossitive
     */
    public boolean isDifferenceMustBePossitive() {
        return differenceMustBePossitive;
    }

    /**
     * @param differenceMustBePossitive the differenceMustBePossitive to set
     */
    public void setDifferenceMustBePossitive(boolean differenceMustBePossitive) {
        this.differenceMustBePossitive = differenceMustBePossitive;
    }

    /**
     * @return the minMinuend
     */
    public int getMinMinuend() {
        return minMinuend;
    }

    /**
     * @param minMinuend the minMinuend to set
     */
    public void setMinMinuend(int minMinuend) {
        this.minMinuend = minMinuend;
    }

    /**
     * @return the maxMinuend
     */
    public int getMaxMinuend() {
        return maxMinuend;
    }

    /**
     * @param maxMinuend the maxMinuend to set
     */
    public void setMaxMinuend(int maxMinuend) {
        this.maxMinuend = maxMinuend;
    }

    /**
     * @return the minSubtrahend
     */
    public int getMinSubtrahend() {
        return minSubtrahend;
    }

    /**
     * @param minSubtrahend the minSubtrahend to set
     */
    public void setMinSubtrahend(int minSubtrahend) {
        this.minSubtrahend = minSubtrahend;
    }

    /**
     * @return the maxSubtrahend
     */
    public int getMaxSubtrahend() {
        return maxSubtrahend;
    }

    /**
     * @param maxSubtrahend the maxSubtrahend to set
     */
    public void setMaxSubtrahend(int maxSubtrahend) {
        this.maxSubtrahend = maxSubtrahend;
    }
    
}
