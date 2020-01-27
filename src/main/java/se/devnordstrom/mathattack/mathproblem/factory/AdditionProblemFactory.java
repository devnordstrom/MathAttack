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
import se.devnordstrom.mathattack.mathproblem.MathOperand;
import se.devnordstrom.mathattack.mathproblem.MathProblem;
import se.devnordstrom.mathattack.mathproblem.MathProblemUtil;
import se.devnordstrom.mathattack.mathproblem.type.AdditionProblem;

/**
 * Factory class for generating AdditionProblems.
 * @author Orville Nordström
 */
public class AdditionProblemFactory {
    
    /**
     * 
     */
    private static final double DEFAULT_BONUS_QUESTION_SCORE_MODIFYER = 2.0;
    
    /**
     * 
     */
    private static final double DEFAULT_BONUS_QUESTION_SPEED_MOD = 1.2;
    
    /**
     * 
     */
    private static final double MULTIPLE_NUMBERS_SPEED_MOD = 0.6;
    
    /**
     * 
     */
    private static final int DEFAULT_STARTING_SCORE = 10;
    
    /**
     * The number at which operands for addition operands are to start.
     * If this is set to 5 then the addition problems in the first 
     * wave will have operands ranging from 0 to 5.
     */
    private static final int DEFAULT_OPERAND_STARTING_MAX_VALUE = 5;
    
    private boolean enableMultipleOperands = true;
    
    private int operandStartingMaxValue, startingScore;
    
    private double bonusQuestionSpeedMod, bonusQuestionScoreMod;
    
    private final Random random;
    
    public AdditionProblemFactory(Random random)
    {
        this.random = random;
        this.operandStartingMaxValue = DEFAULT_OPERAND_STARTING_MAX_VALUE;
        this.bonusQuestionSpeedMod = DEFAULT_BONUS_QUESTION_SPEED_MOD;
        this.bonusQuestionScoreMod = DEFAULT_BONUS_QUESTION_SCORE_MODIFYER;
        this.startingScore = DEFAULT_STARTING_SCORE;
    }
    
    /**
     * Generates an addition problem based on the values provided.
     * 
     * @param waveDifficultyLevel
     * @param bonusQuestion
     * @return 
     */
    public AdditionProblem generateProblem(
            int waveDifficultyLevel, boolean bonusQuestion) {
        
        AdditionProblem addProb = new AdditionProblem(random);
        addProb.setMaxValue(generateMaxOperandValue(waveDifficultyLevel));
        addProb.setNumberCount(generateOperandNumberCount(waveDifficultyLevel));
        addProb.generateQuestion();
        addProb.setBonusQuestion(bonusQuestion);
        setSpeed(addProb, waveDifficultyLevel);
        setScore(addProb, waveDifficultyLevel);
        
        return addProb;

    }
    
    /**
     *
     */ 
    private int generateMaxOperandValue(int waveDifficultyLevel) {
        
        int maxOperandValue = getOperandStartingMaxValue();
        
        if(waveDifficultyLevel <= 10) {
            maxOperandValue += waveDifficultyLevel;
        } else {
            maxOperandValue += 5 + (int) (waveDifficultyLevel * 0.50);
        }
        
        return maxOperandValue;
        
    }
    
    /**
     * 
     * @param waveDifficultyLevel
     * @return 
     */
    private int generateOperandNumberCount(int waveDifficultyLevel) {
        
        if(!isEnableMultipleOperands() || waveDifficultyLevel <= 10) {
            
            return 2;
            
        } else if(waveDifficultyLevel <= 15) {
            
            return random.nextInt(101) < 75 ? 2 : 3; 

        } else {
            
            switch(random.nextInt(101) / 10) {
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
    
    private void setSpeed(AdditionProblem addProb, int waveDifficultyLevel) {
        double speed = addProb.getMinMoveSpeed();
        int numbers = addProb.countNumbers();
        
        speed += (waveDifficultyLevel - 1.0) / 30.0;
        
        //So the more terms the slower it will be and the less the speed will increase.
        if(numbers > 2) {
            speed *= Math.pow(MULTIPLE_NUMBERS_SPEED_MOD, numbers-2);
        }
        
        if(addProb.isBonusQuestion()) {
            addProb.setSpeedModifyer(getBonusQuestionSpeedMod());            
        }
        
        addProb.setMoveSpeed(speed);
        
    }
    
    /**
     * 
     * @param addProb
     * @param waveDifficultyLevel 
     */
    private void setScore(AdditionProblem addProb, int waveDifficultyLevel) {
        int questionScore = this.getStartingScore() + waveDifficultyLevel;
        
        if(addProb.getMathOperandList().size() > 3) {
            int operandCount = (addProb.getMathOperandList().size() + 1) / 2;
            questionScore += (operandCount - 1) * questionScore;
        }
        
        if(addProb.isBonusQuestion()) {
            questionScore *= getBonusQuestionScoreMod();
        }
        
        addProb.setScore(questionScore);
    }
    
    /**
     * @return the enableMultipleOperands
     */
    public boolean isEnableMultipleOperands() {
        return enableMultipleOperands;
    }

    /**
     * @param enableMultipleOperands the enableMultipleOperands to set
     */
    public void setEnableMultipleOperands(boolean enableMultipleOperands) {
        this.enableMultipleOperands = enableMultipleOperands;
    }

    /**
     * @return the operandStartingMaxValue
     */
    public int getOperandStartingMaxValue() {
        return operandStartingMaxValue;
    }

    /**
     * @param operandStartingMaxValue the operandStartingMaxValue to set
     */
    public void setOperandStartingMaxValue(int operandStartingMaxValue) {
        this.operandStartingMaxValue = operandStartingMaxValue;
    }

    /**
     * @return the startingScore
     */
    public int getStartingScore() {
        return startingScore;
    }

    /**
     * @param startingScore the startingScore to set
     */
    public void setStartingScore(int startingScore) {
        this.startingScore = startingScore;
    }

    /**
     * @return the bonusQuestionSpeedMod
     */
    public double getBonusQuestionSpeedMod() {
        return bonusQuestionSpeedMod;
    }

    /**
     * @param bonusQuestionSpeedMod the bonusQuestionSpeedMod to set
     */
    public void setBonusQuestionSpeedMod(double bonusQuestionSpeedMod) {
        this.bonusQuestionSpeedMod = bonusQuestionSpeedMod;
    }

    /**
     * @return the bonusQuestionScoreMod
     */
    public double getBonusQuestionScoreMod() {
        return bonusQuestionScoreMod;
    }

    /**
     * @param bonusQuestionScoreMod the bonusQuestionScoreMod to set
     */
    public void setBonusQuestionScoreMod(double bonusQuestionScoreMod) {
        this.bonusQuestionScoreMod = bonusQuestionScoreMod;
    }
}