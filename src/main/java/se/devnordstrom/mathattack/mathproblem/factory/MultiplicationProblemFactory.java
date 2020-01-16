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
import se.devnordstrom.mathattack.mathproblem.type.MultiplicationProblem;

/**
 *
 * @author Orville Nordström
 */
public class MultiplicationProblemFactory {
    
    public static final int STARTING_SCORE = 20;
    
    private static final int OPERAND_STARTING_MAX_VALUE = 5;
    
    static MultiplicationProblem generateProblem(Random rand, 
            int difficultyLevel, boolean isBonusQuestion) {
        
        MultiplicationProblem multiProb = new MultiplicationProblem(rand);
        
        multiProb.setMaxValue(getMultiplyerOperandMaxValue(difficultyLevel));
                
        multiProb.setBonusQuestion(isBonusQuestion);
        
        multiProb.generateQuestion();
        
        return multiProb;
        
    }
    
    /**
     * This method will return the maximum operand value for a multiplier problem.
     * @return 
     */
    private static int getMultiplyerOperandMaxValue(int difficultyLevel) {
        return OPERAND_STARTING_MAX_VALUE + (int) Math.round((difficultyLevel) * 0.25);
    }

}
