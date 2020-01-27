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
import se.devnordstrom.mathattack.mathproblem.type.ModulusProblem;

/**
 *
 * @author Orville Nordström
 */
public class ModulusProblemFactory {
    
    private static final int DEFAULT_STARTING_SCORE = 30;
    private static final int DEFAULT_OPERAND_STARTING_MAX_VALUE = 5;
    private static final double DEFAULT_BONUS_SPEED_MODIFYER = 1.2;
    private static final double DEFAULT_BONUS_SCORE_MODIFYER = 2.0;
    
    private int startingScore, operandStartingMaxValue;
    
    private double bonusSpeedMod, bonusScoreMod;
    
    private final Random random;
    
    public ModulusProblemFactory(Random random) {
        this.random = random;
        this.startingScore = DEFAULT_STARTING_SCORE;
        this.operandStartingMaxValue = DEFAULT_OPERAND_STARTING_MAX_VALUE;
        this.bonusSpeedMod = DEFAULT_BONUS_SPEED_MODIFYER;
        this.bonusScoreMod = DEFAULT_BONUS_SCORE_MODIFYER;
    }
    
    public ModulusProblem generateProblem(int waveDifficultyLevel, boolean bonusQuestion) {
        ModulusProblem modulusProblem = new ModulusProblem(random);
        modulusProblem.setBonusQuestion(bonusQuestion);
        modulusProblem.setDividendMaxValue(getOperandMaxValue(waveDifficultyLevel));
        modulusProblem.setDivisorMaxValue(getOperandMaxValue(waveDifficultyLevel));
        modulusProblem.generateQuestion();
        
        setSpeed(modulusProblem, waveDifficultyLevel);
        setScore(modulusProblem, waveDifficultyLevel);
        
        return modulusProblem;
    }
    
    private int getOperandMaxValue(int difficultyLevel) {
        return getOperandStartingMaxValue() + (int) Math.round((difficultyLevel) * 0.75);
    }
    
    private void setSpeed(ModulusProblem modulusProb, int waveDifficultyLevel) {
        double speed = modulusProb.getMinMoveSpeed();
        int terms = modulusProb.getMathOperandList().size();
        
        if(terms <= 3) {
            speed += waveDifficultyLevel / 35.0;
        } else {
            speed += waveDifficultyLevel / 40.0;
        }
        
        modulusProb.setMoveSpeed(speed);
        
        if(modulusProb.isBonusQuestion()) {
            modulusProb.setSpeedModifyer(getBonusSpeedMod());
        }
    }
    
    private void setScore(ModulusProblem modulusProb, int waveDifficultyLevel) {
        int questionScore = this.getStartingScore() + waveDifficultyLevel;
        
        if(modulusProb.getMathOperandList().size() > 3) {
            int operandCount = (modulusProb.getMathOperandList().size() + 1) / 2;
            questionScore += (operandCount - 1) * questionScore;
        }
        
        if(modulusProb.isBonusQuestion()) {
            questionScore *= getBonusScoreMod();
        }
        
        modulusProb.setScore(questionScore);
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
}