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
import se.devnordstrom.mathattack.mathproblem.MathProblem;
import se.devnordstrom.mathattack.mathproblem.type.AdditionProblem;
import se.devnordstrom.mathattack.mathproblem.type.DivisionProblem;
import se.devnordstrom.mathattack.mathproblem.type.ModulusProblem;
import se.devnordstrom.mathattack.mathproblem.type.MultiplicationProblem;
import se.devnordstrom.mathattack.mathproblem.type.SubtractionProblem;

/**
 * Class responsible for generating MathProblems.
 * 
 * This class is 
 * 
 * @author Orville Nordström
 */
public class MathProblemFactory {
    
    private static final double BONUS_QUESTION_PERCENTAGE = 20;
        
    private static final int BONUS_QUESTION_SCORE_MODIFYER = 4;
    
    private Random rand;
    
    private boolean enableBonusQuestions = true;
        
    private int currentDifficultyLevel;
    
    public MathProblemFactory(Random rand) {
        this.rand = rand;
    }
    
    /**
     * Generates a MathProblem.
     * 
     * @param difficultyLevel
     * @return 
     */
    public MathProblem generateMathProblem(int difficultyLevel) {
        currentDifficultyLevel = difficultyLevel;
        
        MathProblem mathProb = createMathProblem();
        
        setMathProblemSpeed(mathProb);
        
        setProblemPoints(mathProb);

        return mathProb;
    }
    
    /**
     * 
     * @param difficultyLevel
     * @return 
     */
    private MathProblem createMathProblem() {
        MathProblem mathProb;
        
        if(currentDifficultyLevel < 5) {
            if(rand.nextBoolean()) {
                mathProb = generateAdditionProblem();
            } else {
                mathProb = generateSubstractionProblem();
            }
        } else if(currentDifficultyLevel < 10) {
            switch(rand.nextInt(10)) {
                case 0:
                case 1:
                case 2:
                case 3:
                    mathProb = generateAdditionProblem();
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                    mathProb = generateSubstractionProblem();
                    break;
                case 8:
                    mathProb = generateDivisionProblem();
                    break;
                default:
                    mathProb = generateMultiplicationProblem();
                    break;
            }
        } else {
            switch(rand.nextInt(8)) {
                case 1:
                case 2:
                    mathProb = generateAdditionProblem();
                    break;
                case 3:
                case 4:
                    mathProb = generateSubstractionProblem();
                    break;
                case 5:
                    mathProb = generateMultiplicationProblem();
                    break;
                case 6:
                    mathProb = generateDivisionProblem();
                    break;
                default:
                    mathProb = generateModulusProblem();
                    break;
            }
        }
        
        return mathProb;
    }
    
    /**
     * Generates an addition problem based on the values provided.
     * @param isBonusQuestion
     * @return 
     */
    private AdditionProblem generateAdditionProblem() {
        return AdditionProblemFactory.generateProblem(rand, currentDifficultyLevel, shouldBeBonusQuestion());
    }

    private SubtractionProblem generateSubstractionProblem() {
        return SubtractionProblemFactory.generateProblem(rand, currentDifficultyLevel, shouldBeBonusQuestion());
    }
    
    private MultiplicationProblem generateMultiplicationProblem() {
        return MultiplicationProblemFactory.generateProblem(rand, currentDifficultyLevel, shouldBeBonusQuestion());
    }
    
    private DivisionProblem generateDivisionProblem() {
        return DivisionProblemFactory.generateProblem(rand, currentDifficultyLevel, shouldBeBonusQuestion());
    }
    
    private ModulusProblem generateModulusProblem() {
        return ModulusProblemFactory.generateProblem(rand, currentDifficultyLevel, shouldBeBonusQuestion());
    }
    
    /**
     * Note that all MathProblems have a maximum speed.
     * @return 
     */
    private void setMathProblemSpeed(MathProblem mathProb) {
        
        double minSpeed = mathProb.getMinMoveSpeed();
        
        double speed = minSpeed;
        
        if(mathProb instanceof AdditionProblem || mathProb instanceof SubtractionProblem) {
            
            int terms = mathProb.getMathOperandList().size();
            
            //So the more terms the slower it will be and the less the speed will increase.
            if(terms <= 3) {
                speed += (double) (currentDifficultyLevel - 1) / 5.0;
            } else if(terms <= 5) {
                speed += (double) (currentDifficultyLevel - 1) / 7.5;
            } else {
                speed += (double) (currentDifficultyLevel - 1) / 10.5;
            }
            
        } else if(mathProb instanceof MultiplicationProblem) {
            
            int terms = mathProb.getMathOperandList().size();
            
            if(terms <= 3) {
                speed += (double) (currentDifficultyLevel - 1) / 10.0;
            } else {
                speed += (double) (currentDifficultyLevel - 1) / 15.0;
            }
            
        } else {
            
            speed += (double) (currentDifficultyLevel - 1) / 15.0;
            
        }
           
        if(mathProb.isBonusQuestion()) {
            speed *= 2;
        }
        
        mathProb.setMoveSpeed(speed);
                
    }
    
    /**
     * Calculates and assigns a score.
     * @param mathProb 
     */
    private void setProblemPoints(MathProblem mathProb) {
        
        int questionScore = 0;
                
        if(mathProb instanceof AdditionProblem) {
            questionScore = AdditionProblemFactory.STARTING_SCORE;
        } else if(mathProb instanceof SubtractionProblem) {
            questionScore = SubtractionProblemFactory.STARTING_SCORE;
        } else if(mathProb instanceof MultiplicationProblem) {
            questionScore = MultiplicationProblemFactory.STARTING_SCORE;
        } else if(mathProb instanceof DivisionProblem) {
            questionScore = DivisionProblemFactory.STARTING_SCORE;
        } else if(mathProb instanceof ModulusProblem) {
            questionScore = ModulusProblemFactory.STARTING_SCORE;
        }
        
        if(mathProb.getMathOperandList().size() > 3) {
        
            int operandCount = (mathProb.getMathOperandList().size() + 1) / 2; //Since for a + b this would be 2, not 3.

            questionScore += (operandCount - 1) * questionScore;

        }
        
        questionScore += currentDifficultyLevel + Math.round(currentDifficultyLevel * 0.50);
        
        
        if(mathProb.isBonusQuestion()) {
            questionScore *= BONUS_QUESTION_SCORE_MODIFYER;
        }
           
        mathProb.setScore(questionScore);
        
    }
    
    /**
     * 
     * @return 
     */
    private boolean shouldBeBonusQuestion() {
        return isEnableBonusQuestions() && rand.nextInt(101) <= BONUS_QUESTION_PERCENTAGE;
    }

    /**
     * @return the enableBonusQuestions
     */
    public boolean isEnableBonusQuestions() {
        return enableBonusQuestions;
    }

    /**
     * @param enableBonusQuestions the enableBonusQuestions to set
     */
    public void setEnableBonusQuestions(boolean enableBonusQuestions) {
        this.enableBonusQuestions = enableBonusQuestions;
    }
    
}