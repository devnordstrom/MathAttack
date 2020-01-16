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
import se.devnordstrom.mathattack.mathproblem.type.SubtractionProblem;

/**
 *
 * @author Orville Nordström
 */
public class SubtractionProblemFactory {
    
    public static final int STARTING_SCORE = 10;
    
    private static final int OPERAND_STARTING_MAX_VALUE = 5;

    private static final int MIN_MINUEND_VALUE = 3;
    
    static SubtractionProblem generateProblem(Random rand, 
            int difficultyLevel, boolean isBonusQuestion) {
        
        SubtractionProblem subProb = new SubtractionProblem(rand);
        
        subProb.setBonusQuestion(isBonusQuestion);
        
        subProb.setDifferenceMustBePossitive(true); //Since typing in a '-' might be difficult.
        
        subProb.setMaxMinuend(generateMaxOperandValue(difficultyLevel));
        
        subProb.setMinMinuend(MIN_MINUEND_VALUE);
        
        subProb.setMaxSubtrahend(generateMaxOperandValue(difficultyLevel));
        
        subProb.setMinSubtrahend(0);
        
        subProb.generateQuestion();
                                
        return subProb;
        
    }
    
    /**
     * Used for + and - operations.
     */ 
    private static int generateMaxOperandValue(int difficultyLevel) {
        
        int maxOperandValue = OPERAND_STARTING_MAX_VALUE;
                
        if(difficultyLevel <= 10) {
            maxOperandValue += difficultyLevel;
        } else {
            maxOperandValue += 5 + (int) (difficultyLevel * 0.50);
        }
        
        return maxOperandValue;
        
    }
    
}