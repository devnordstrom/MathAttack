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
package se.devnordstrom.mathattack.mathproblem;

import java.util.List;

/**
 *
 * @author Orville Nordström
 */
public class MathProblemUtil {
    
    /**
     * Computes and sets the mathproblem's answer.
     * @param mathproblem 
     */
    public static void setMathProblemAnswer(MathProblem mathproblem) {
        
        List<MathOperand> mathOperandList = mathproblem.getMathOperandList();
                
        String questionAsString = "";    //To be displayed to the screen.
        
        int answer = 0;
                
        int lastOperator = MathProblem.OPERATOR_PLUS;
        
        for(MathOperand mathOp : mathOperandList) {
                        
            int value = mathOp.getValue();
            
            if(mathOp.isNumber()) {
                                
                switch(lastOperator) {
                    
                    case MathProblem.OPERATOR_PLUS:
                        answer += value;
                        if(!questionAsString.isEmpty()) questionAsString += " + " + value;
                        break;
                    case MathProblem.OPERATOR_MINUS:
                        answer -= value; 
                        if(!questionAsString.isEmpty()) questionAsString += " - " + value;
                                                
                        break;
                    case MathProblem.OPERATOR_DIVIDE:
                        answer /= value;
                        if(!questionAsString.isEmpty()) questionAsString += " / " + value;
                        break;
                    case MathProblem.OPERATOR_MULTIPLY:
                        answer *= value;
                        if(!questionAsString.isEmpty()) questionAsString += " X " + value;
                        break;
                    case MathProblem.OPERATOR_MODULUS:
                        answer %= value;
                        if(!questionAsString.isEmpty()) questionAsString += " % " + value;
                        break;
                        
                }
                
                if(questionAsString.isEmpty()) {
                    questionAsString += value;
                }
                
            } else {
                
                lastOperator = value;
                                     
            }
        }
        
        mathproblem.setAnswer(answer);

        mathproblem.setQuestionString(questionAsString);
                
    }
    
}