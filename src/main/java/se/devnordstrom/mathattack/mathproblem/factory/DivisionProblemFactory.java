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
package se.devnordstrom.mathattack.mathproblem.factory;

import java.util.Random;
import se.devnordstrom.mathattack.mathproblem.type.DivisionProblem;

/**
 *
 * @author Orville Nordström
 */
public class DivisionProblemFactory {
    
    public static final int STARTING_SCORE = 20;
    
    private static final int OPERAND_STARTING_MAX_VALUE = 5;
    
    static DivisionProblem generateProblem(Random rand, 
            int difficultyLevel, boolean bonusQuestion) {
        
        DivisionProblem divProv = new DivisionProblem(rand);
        
        divProv.setBonusQuestion(bonusQuestion);
        
        int operandMaxValue = generateOperandMaxValue(difficultyLevel);
                
        divProv.setDivisorMaxValue(operandMaxValue);
                
        divProv.setQuotientMaxValue(operandMaxValue);
        
        divProv.generateQuestion();
        
        return divProv;
        
    }
    
    /**
     * 
     * @return 
     */
    private static int generateOperandMaxValue(int difficultyLevel) {
        return OPERAND_STARTING_MAX_VALUE + (int) Math.round((difficultyLevel) * 0.2);
    }
    
}