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
    
    private static final int DEFAULT_STARTING_SCORE = 20;
    private static final int DEFAULT_OPERAND_STARTING_MAX_VALUE = 5;
    private static final double DEFAULT_BONUS_SCORE_MODIFYER = 2.0;
    private static final double DEFAULT_BONUS_SPEED_MOD = 1.2;
    
    private int startingScore, operandStartingMaxValue;
    
    private double bonusScoreMod, bonusSpeedMod;

    private final Random random;
    
    public MultiplicationProblemFactory(Random random) {
        this.random = random;
        this.startingScore = DEFAULT_STARTING_SCORE;
        this.operandStartingMaxValue = DEFAULT_OPERAND_STARTING_MAX_VALUE;
        this.bonusScoreMod = DEFAULT_BONUS_SCORE_MODIFYER;
        this.bonusSpeedMod = DEFAULT_BONUS_SPEED_MOD;   
    }
    
    public MultiplicationProblem generateProblem(int waveDifficultyLevel, boolean isBonusQuestion) {
        
        MultiplicationProblem multiProb = new MultiplicationProblem(random);
        multiProb.setMaxValue(getMultiplyerOperandMaxValue(waveDifficultyLevel));
        multiProb.setBonusQuestion(isBonusQuestion);
        multiProb.generateQuestion();
        setSpeed(multiProb, waveDifficultyLevel);
        setScore(multiProb, waveDifficultyLevel);
        
        return multiProb;
        
    }
    
    /**
     * This method will return the maximum operand value for a multiplier problem.
     * @return 
     */
    private int getMultiplyerOperandMaxValue(int waveDifficultyLevel) {
        return operandStartingMaxValue + (int) Math.round((waveDifficultyLevel) * 0.25);
    }

    private void setSpeed(MultiplicationProblem multiProb, int waveDifficultyLevel) {
        double speed = multiProb.getMinMoveSpeed();
        int terms = multiProb.getMathOperandList().size();

        speed += (double) (waveDifficultyLevel - 1) / 25.0;
        if(terms <= 3) {
            speed *= 0.5;
        }
        
        multiProb.setMoveSpeed(speed);
        
        if(multiProb.isBonusQuestion()) {
            multiProb.setSpeedModifyer(bonusSpeedMod);
        }
    }
    
    /**
     * 
     * @param addProb
     * @param waveDifficultyLevel 
     */
    private void setScore(MultiplicationProblem multiProb, int waveDifficultyLevel) {
        int questionScore = this.getStartingScore() + waveDifficultyLevel;
        
        if(multiProb.getMathOperandList().size() > 3) {
            int operandCount = (multiProb.getMathOperandList().size() + 1) / 2;
            questionScore += (operandCount - 1) * questionScore;
        }
        
        if(multiProb.isBonusQuestion()) {
            questionScore *= getBonusScoreMod();
        }
        
        multiProb.setScore(questionScore);
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
     * @return the bonusScoreMod
     */
    public double getBonusScoreMod() {
        return bonusScoreMod;
    }

    /**
     * @param bonusScoreMod the bonusScoreMod to set
     */
    public void setBonusScoreMod(double bonusScoreMod) {
        this.bonusScoreMod = bonusScoreMod;
    }

    /**
     * @return the bonusSpeedMod
     */
    public double getBonusSpeedMod() {
        return bonusSpeedMod;
    }

    /**
     * @param bonusSpeedMod the bonusSpeedMod to set
     */
    public void setBonusSpeedMod(double bonusSpeedMod) {
        this.bonusSpeedMod = bonusSpeedMod;
    }
}
