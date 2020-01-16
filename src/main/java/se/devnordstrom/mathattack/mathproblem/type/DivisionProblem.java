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
 * This class only supports a / b = c type problems.
 *
 * @author Orville Nordström
 */
public class DivisionProblem extends MathProblem {
    
    private int divisorMaxValue = MathProblem.EASY_OPERAND_MAX;
    
    private int divisorMinValue = 1;    //Since the value 1 is set if this is ever generated to zero.
    
    private int quotientMaxValue = MathProblem.EASY_OPERAND_MAX;
    
    private int quotientMinValue = 1;
            
    public static int baseScore = 60;

    public DivisionProblem(Random rand) {
        super(rand);
    }
    
    @Override
    public void generateQuestion() {
        
        /**
         * This method is going to start with the divisor and answer to get the dividend.
         */
        int divisor = rand.nextInt(divisorMaxValue) + divisorMinValue;
        
        if(divisor == 0) {
            divisor = 1;
        }
        
        int quotient = rand.nextInt(quotientMaxValue) + quotientMinValue;
        
        int dividend = divisor * quotient;
        
        MathOperand dividiendOperand = new MathOperand(dividend);
        
        MathOperand divisionOperator = new MathOperand(MathProblem.OPERATOR_DIVIDE);
        
        divisionOperator.setOperator(true);
        
        MathOperand divisorOperand = new MathOperand(divisor);
        
        mathOperandList.add(dividiendOperand);
        
        mathOperandList.add(divisionOperator);
        
        mathOperandList.add(divisorOperand);
        
        MathProblemUtil.setMathProblemAnswer(this);
        
    }

    /**
     * @return the divisorMaxValue
     */
    public int getDivisorMaxValue() {
        return divisorMaxValue;
    }

    /**
     * @param divisorMaxValue the divisorMaxValue to set
     */
    public void setDivisorMaxValue(int divisorMaxValue) {
        this.divisorMaxValue = divisorMaxValue;
    }
    
    /**
     * @return the divisorMinValue
     */
    public int getDivisorMinValue() {
        return divisorMinValue;
    }

    /**
     * @param divisorMinValue the divisorMinValue to set
     */
    public void setDivisorMinValue(int divisorMinValue) {
        this.divisorMinValue = divisorMinValue;
    }

    /**
     * @return the answerMaxValue
     */
    public int getQuotientMaxValue() {
        return quotientMaxValue;
    }

    /**
     * @param answerMaxValue the answerMaxValue to set
     */
    public void setQuotientMaxValue(int answerMaxValue) {
        this.quotientMaxValue = answerMaxValue;
    }

    /**
     * @return the answerMinValue
     */
    public int getQuotientMinValue() {
        return quotientMinValue;
    }

    /**
     * @param answerMinValue the answerMinValue to set
     */
    public void setQuotientMinValue(int answerMinValue) {
        this.quotientMinValue = answerMinValue;
    }
    
}
