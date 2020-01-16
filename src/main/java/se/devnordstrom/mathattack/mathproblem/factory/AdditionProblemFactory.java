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
import se.devnordstrom.mathattack.mathproblem.type.AdditionProblem;

/**
 * Factory class for generating AdditionProblems.
 * @author Orville Nordström
 */
public class AdditionProblemFactory {
    
    public static final int STARTING_SCORE = 10;
    
    /**
     * The number at which operands for addition operands are to start.
     * If this is set to 5 then the addition problems in the first 
     * wave will have operands ranging from 0 to 5.
     */
    private static final int OPERAND_STARTING_MAX_VALUE = 5;
    
    /**
     * Generates an addition problem based on the values provided.
     * @param isBonusQuestion
     * @return 
     */
    static AdditionProblem generateProblem(Random random, 
            int difficultyLevel, boolean bonusQuestion) {
        
        AdditionProblem addProb = new AdditionProblem(random);
        
        addProb.setMaxValue(generateMaxOperandValue(difficultyLevel));
        
        addProb.setNumberCount(generateOperandNumberCount(random, difficultyLevel));
        
        addProb.generateQuestion();
        
        addProb.setBonusQuestion(bonusQuestion);
        
        return addProb;

    }
    
    /**
     *
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
    
    /**
     * 
     * @param difficultyLevel
     * @return 
     */
    private static int generateOperandNumberCount(Random rand, int difficultyLevel) {
        
        if(difficultyLevel <= 10) {
            
            return 2;
            
        } else if(difficultyLevel <= 15) {
            
            return rand.nextInt(101) < 75 ? 2 : 3; 

        } else {
            
            switch(rand.nextInt(101) / 10) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                    return 2;
                case 5:
                case 6:
                    return 3;
                case 7:
                    return 4;
                case 8:
                    return 5;
                case 9:
                    return 6;
                default:
                    return 7;
                
            }
            
        }
        
    }
}
