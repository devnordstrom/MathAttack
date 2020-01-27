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
import se.devnordstrom.mathattack.mathproblem.type.SubtractionProblem;

/**
 *
 * @author Orville Nordström
 */
public class SubtractionProblemFactory {
    
    private static final double DEFAULT_BONUS_QUESTION_SCORE_MODIFYER = 2.0;
    private static final double DEFAULT_BONUS_QUESTION_SPEED_MOD = 1.2;
    private static final double MULTIPLE_NUMBERS_SPEED_MOD = 0.6;
    public static final int DEFAULT_STARTING_SCORE = 10;
    private static final int DEFAULT_OPERAND_STARTING_MAX_VALUE = 5;
    private static final int DEFAULT_MIN_MINUEND_VALUE = 3;
    
    private final Random random;
    
    private double bonusQuestionSpeedMod, bonusQuestionScoreMod;
    
    private int startingScore, operandStartingMaxValue, minMinuendValue;
    
    public SubtractionProblemFactory(Random random)
    {
        this.random = random;
        this.bonusQuestionSpeedMod = DEFAULT_BONUS_QUESTION_SPEED_MOD;
        this.bonusQuestionScoreMod = DEFAULT_BONUS_QUESTION_SCORE_MODIFYER;
        this.startingScore = DEFAULT_STARTING_SCORE;
        this.startingScore = DEFAULT_STARTING_SCORE;
        this.operandStartingMaxValue = DEFAULT_OPERAND_STARTING_MAX_VALUE;
        this.minMinuendValue = DEFAULT_MIN_MINUEND_VALUE;
    }
    
    public SubtractionProblem generateProblem(int waveDifficultyLevel, boolean bonusQuestion) {
        
        SubtractionProblem subProb = new SubtractionProblem(random);
        
        subProb.setBonusQuestion(bonusQuestion);
        subProb.setDifferenceMustBePossitive(true); //Since typing in a '-' might be difficult.
        subProb.setMaxMinuend(generateMaxOperandValue(waveDifficultyLevel));
        subProb.setMinMinuend(getMinMinuendValue());
        subProb.setMaxSubtrahend(generateMaxOperandValue(waveDifficultyLevel));
        subProb.setMinSubtrahend(0);
        subProb.generateQuestion();
                              
        setSpeed(subProb, waveDifficultyLevel);
        setScore(subProb, waveDifficultyLevel);
        
        return subProb;
        
    }
    
    /**
     * Used for + and - operations.
     */ 
    private int generateMaxOperandValue(int waveDifficultyLevel) {
        
        int maxOperandValue = operandStartingMaxValue;
                
        if(waveDifficultyLevel <= 10) {
            maxOperandValue += waveDifficultyLevel;
        } else {
            maxOperandValue += 5 + (int) (waveDifficultyLevel * 0.50);
        }
        
        return maxOperandValue;
        
    }
    
    private void setSpeed(SubtractionProblem subProb, int waveDifficultyLevel) {
        double speed = subProb.getMinMoveSpeed();
        speed += (waveDifficultyLevel - 1.0) / 30.0;
        
        int numbers = subProb.countNumbers();
        if(numbers > 2) {
            speed *= Math.pow(MULTIPLE_NUMBERS_SPEED_MOD, numbers-2);;
        }
        
        subProb.setMoveSpeed(speed);
        
        if(subProb.isBonusQuestion()) {
            subProb.setSpeedModifyer(bonusQuestionSpeedMod);
        }
    }
    
   
    private void setScore(SubtractionProblem subProb, int waveDifficultyLevel)
    {
        int questionScore = this.getStartingScore() + waveDifficultyLevel;
        
        if(subProb.getMathOperandList().size() > 3) {
            int operandCount = (subProb.getMathOperandList().size() + 1) / 2;
            questionScore += (operandCount - 1) * questionScore;
        }
        
        if(subProb.isBonusQuestion()) {
            questionScore *= getBonusQuestionScoreMod();
        }
        
        subProb.setScore(questionScore);
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
     * @return the minMinuendValue
     */
    public int getMinMinuendValue() {
        return minMinuendValue;
    }

    /**
     * @param minMinuendValue the minMinuendValue to set
     */
    public void setMinMinuendValue(int minMinuendValue) {
        this.minMinuendValue = minMinuendValue;
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