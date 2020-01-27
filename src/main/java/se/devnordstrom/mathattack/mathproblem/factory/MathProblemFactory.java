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
    private static final double BONUS_QUESTION_PERCENTAGE = 10;
    
    private Random rand;
    
    private boolean enableBonusQuestions = true;
        
    private int currentWaveDifficultyLevel;
    
    private AdditionProblemFactory additionProblemFactory;
    private SubtractionProblemFactory subtractionProblemFactory;
    private MultiplicationProblemFactory multiplicationProblemFactory;
    private DivisionProblemFactory divisionProblemFactory;
    private ModulusProblemFactory modulusProblemFactory;
    
    public MathProblemFactory(Random rand) {
        this.rand = rand;
        this.additionProblemFactory = new AdditionProblemFactory(rand);
        this.subtractionProblemFactory = new SubtractionProblemFactory(rand);
        this.divisionProblemFactory = new DivisionProblemFactory(rand);
        this.multiplicationProblemFactory = new MultiplicationProblemFactory(rand);
        this.modulusProblemFactory = new ModulusProblemFactory(rand);
    }
    
    /**
     * Generates a MathProblem.
     * 
     * @param waveDifficultyLevel
     * @return 
     */
    public MathProblem generateMathProblem(int waveDifficultyLevel) {
        currentWaveDifficultyLevel = waveDifficultyLevel;
        
        MathProblem mathProb = generateMathProblem();
        
        return mathProb;
    }
    
    /**
     * 
     * @param difficultyLevel
     * @return 
     */
    private MathProblem generateMathProblem() {
        MathProblem mathProb;
        
        if(currentWaveDifficultyLevel < 5) {
            if(rand.nextBoolean()) {
                mathProb = generateAdditionProblem();
            } else {
                mathProb = generateSubstractionProblem();
            }
        } else if(currentWaveDifficultyLevel < 20) {
            
            switch(rand.nextInt(9)) {
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
                default:
                    
                    if(rand.nextBoolean()) {
                        mathProb = generateMultiplicationProblem();
                    } else {
                        mathProb = generateDivisionProblem();
                    }
                    
                    break;
            }
            
        } else if(currentWaveDifficultyLevel < 40) {
            switch(rand.nextInt(10)) {
                case 0:
                case 1:
                case 2:
                    mathProb = generateAdditionProblem();
                    break;
                case 3:
                case 4:
                case 5:
                    mathProb = generateSubstractionProblem();
                    break;
                case 6:
                case 7:
                    mathProb = generateMultiplicationProblem();
                    break;
                default:
                    mathProb = generateDivisionProblem();
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
        return additionProblemFactory
                .generateProblem(currentWaveDifficultyLevel, shouldBeBonusQuestion());
    }
    
    private SubtractionProblem generateSubstractionProblem() {
        return subtractionProblemFactory
                .generateProblem(currentWaveDifficultyLevel, shouldBeBonusQuestion());
    }
    
    private MultiplicationProblem generateMultiplicationProblem() {        
        return multiplicationProblemFactory
                .generateProblem(currentWaveDifficultyLevel, shouldBeBonusQuestion());
    }
    
    private DivisionProblem generateDivisionProblem() {
        return divisionProblemFactory
                .generateProblem(currentWaveDifficultyLevel, shouldBeBonusQuestion());
    }
    
    private ModulusProblem generateModulusProblem() {
        return modulusProblemFactory
                .generateProblem(currentWaveDifficultyLevel, shouldBeBonusQuestion());
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