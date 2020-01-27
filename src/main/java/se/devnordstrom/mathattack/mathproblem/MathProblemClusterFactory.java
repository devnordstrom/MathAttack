/*
 * Copyright (C) 2016 Orville Nordström
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
import java.util.Random;
import se.devnordstrom.mathattack.mathproblem.factory.MathProblemFactory;
import se.devnordstrom.mathattack.mathproblem.wave.cluster.MathCluster;

/**
 *
 * @author Orville Nordström
 */
public class MathProblemClusterFactory {
    
    /**
     * Used to determine the changed speed for normal questions in a cluster of problems 
     * with more than one normal question.
     * 
     * This is so that the player won't be overwhelmed by an array of questions moving too quickly.
     * 
     * If the number of normal questions are zero or one then this value won't be used.
     * With x questions the speed modifier will be set to this value to the power of(x - 1).
     * 
     * If you don't want to decrease the speed for a number of questions set this to 1.0.
     * In order to increase the speed set this to greater than 1.0.
     * 
     * If this value is set to less than zero then this value will be ignored.
     * 
     */
    private static final double CLUSTER_SPEED_MODIFIER_FACTOR = 0.6;
    
    private Random rand;
    
    private int xDefaultMinValue, xDefaultMaxValue, screenWidth, screenHeight;
             
    private MathProblemFactory mathProblemFactory;
    
    /**
     * This constructor will create a new instance of Random and use the regular constructor.
     * 
     * If you already have a Random object you are using then that constructor is recommended.
     * @param screenWidth
     * @param screenHeight
     */
    public MathProblemClusterFactory(int screenWidth, int screenHeight) {
        this(new Random(), screenWidth, screenHeight);
    }
    
    /**
     * So that the same Random object may be used if so desired to get better random values.
     * @param rand 
     * @param screenWidth 
     * @param screenHeight 
     * 
     */
    public MathProblemClusterFactory(Random rand, int screenWidth, int screenHeight) {
        this.mathProblemFactory = new MathProblemFactory(rand);
        
        this.rand = rand;
        
        this.screenWidth = screenWidth;
        
        this.screenHeight = screenHeight;
        
        this.xDefaultMinValue = 50;
        
        this.xDefaultMaxValue = screenWidth - 250;        
    }
    
    public boolean isInRange(int x, int min, int max) {
        return min <= x && x <= max;
    }
    
    private MathProblem generateMathProblem(int waveDifficultyLevel) {
        return mathProblemFactory.generateMathProblem(waveDifficultyLevel);
    }
    
    public MathCluster generateMathCluster(int waveDifficultyLevel) {
        
        MathCluster mathCluster = new MathCluster();
                
        int problemCount = generateMathClusterProblemCount(waveDifficultyLevel);
                
        for(int i = 0; i < problemCount; i++) {
            mathCluster.addMathProblem(generateMathProblem(waveDifficultyLevel));
        }
        
        setClusterSpeedModifyer(mathCluster);
        
        generateStartingPositionForCluster(mathCluster);
                
        return mathCluster;
        
    }
    
    private int generateMathClusterProblemCount(int waveDifficultyLevel) {
        
        if(waveDifficultyLevel <= 20) {
            
            return 1;
            
        } else if(waveDifficultyLevel <= 40) {
            
            if(rand.nextInt(8) != 7) {
                return 1;
            } else {
                return 2;
            }
            
        } else {
            
            switch(rand.nextInt(11)) {
                
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    return 1;
                case 7:
                case 8:
                case 9:
                    return 2;
                default:
                    return 3;
                
            }
            
        }
    }
    
    private void generateStartingPositionForCluster(MathCluster mathCluster) {
        
        List<MathProblem> problemList = mathCluster.getMathProblems();
        
        if(problemList.isEmpty()) {
            return;
        }
        
        if(problemList.size() == 1) {
            
            generateStartingPosition(problemList.get(0), xDefaultMinValue, xDefaultMaxValue);
        
        } else {
            
            int xPositionIncrement = 100;
            
            for(int i = 0; i < problemList.size(); i++) {
                int xMin = xDefaultMinValue + (i * xPositionIncrement);
                
                //Decrementing 50 so that the questions won't collide.
                int xMax = xMin + xPositionIncrement - 50;
                
                generateStartingPosition(problemList.get(i), xMin, xMax);
            }
        }
    }
    
    private void generateStartingPosition(MathProblem mathProb, int xMinValue, int xMaxValue) {
        int x = 0;
        int y = 0;
        
        if(mathProb.isBonusQuestion()) {
            int bonusDirection = (rand.nextBoolean()) ? MathProblem.BONUS_DIRECTION_LEFT : MathProblem.BONUS_DIRECTION_RIGHT;
            
            y = rand.nextInt(screenHeight - 200) + 100; 
            
            if(bonusDirection == MathProblem.BONUS_DIRECTION_RIGHT) {
                x = this.screenWidth - 100;
            } else {
                x = 0;  //For compleatness since it is set to 0 allready.
            }

            mathProb.setBonusQuestionDirection(bonusDirection);
        } else {
            x = generateStartingX(xMinValue, xMaxValue);            
        }
            
        mathProb.setY(y);

        mathProb.setX(x);
        
    }
    
    private int generateStartingX(int minValue, int maxValue) {
        int bounds = maxValue-minValue;
        
        if(bounds <= 0) {
            bounds = 1;
        }
        
        return rand.nextInt(bounds) + minValue;
    }
    
    private static void setClusterSpeedModifyer(MathCluster mathCluster) {
        
        int normalQuestionsCount = mathCluster.getNormalQuestionsCount();
        
        if(normalQuestionsCount <= 1) {
            return;
        }        
        
        double speedModifier = 1.0;
        
        //This will reduce the speed depending on how many problems are generated at once.
        speedModifier *= Math.pow(CLUSTER_SPEED_MODIFIER_FACTOR, normalQuestionsCount - 1);
        
        for(MathProblem mathProblem : mathCluster.getMathProblems()) {
            if(!mathProblem.isBonusQuestion()) mathProblem.setSpeedModifyer(speedModifier);
        }
        
    }
    
}