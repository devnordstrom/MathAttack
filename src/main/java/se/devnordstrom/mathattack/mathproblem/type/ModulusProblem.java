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
package se.devnordstrom.mathattack.mathproblem.type;

import java.util.Random;
import se.devnordstrom.mathattack.mathproblem.MathOperand;
import se.devnordstrom.mathattack.mathproblem.MathProblem;
import se.devnordstrom.mathattack.mathproblem.MathProblemUtil;

/**
 *
 * @author Orville Nordström
 */
public class ModulusProblem extends MathProblem {
    
    private int dividendMinValue = 5;
    
    private int dividendMaxValue = 0;
    
    private int divisorMinValue = 1;
    
    private int divisorMaxValue = 0;

    public ModulusProblem(Random rand) {
        super(rand);
    }

    @Override
    public void generateQuestion() {
        
        int dividend = rand.nextInt(getDividendMaxValue()) + getDividendMinValue();
        
        int divisor = rand.nextInt(getDivisorMaxValue()) + getDivisorMinValue();
        
        
        MathOperand dividendOperand = new MathOperand(dividend);
        
        MathOperand modulusOperator = new MathOperand(MathProblem.OPERATOR_MODULUS, true);
        
        MathOperand divisorOperand = new MathOperand(divisor);

        
        mathOperandList.add(dividendOperand);
        
        mathOperandList.add(modulusOperator);
        
        mathOperandList.add(divisorOperand);
        
        MathProblemUtil.setMathProblemAnswer(this);
        
    }

    /**
     * @return the dividendMinValue
     */
    public int getDividendMinValue() {
        return dividendMinValue;
    }

    /**
     * @param dividendMinValue the dividendMinValue to set
     */
    public void setDividendMinValue(int dividendMinValue) {
        this.dividendMinValue = dividendMinValue;
    }

    /**
     * @return the dividendMaxValue
     */
    public int getDividendMaxValue() {
        return dividendMaxValue;
    }

    /**
     * @param dividendMaxValue the dividendMaxValue to set
     */
    public void setDividendMaxValue(int dividendMaxValue) {
        this.dividendMaxValue = dividendMaxValue;
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
    
}