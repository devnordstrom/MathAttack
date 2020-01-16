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
public class MathProblemController {
    
    /**
     * Used to determine the changed speed for normal questions in a cluster of problems 
     * with more than one normal question.
     * 
     * This is so that the player won't be overwhelmed by an array of questions moving to quickly.
     * 
     * If the number of normal questions are zero or one then this value won't be used.
     * With x questions the speed modifier will be set to this value to the power of(x - 1).
     * 
     * If you don't want to decrease the speed for a number of questions set this to 1.0.
     * If for some reason you want to increase it set this to greater than 1.0.
     * 
     * If this value is set to less than zero then this value will be ignored.
     * 
     */
    private static final double CLUSTER_SPEED_MODIFIER_FACTOR = 0.8;
    
    private int xDefaultMinValue;
    
    private int xDefaultMaxValue;
    
    private Random rand;
    
    private int screenWidth;
    
    private int screenHeight;
             
    private MathProblemFactory mathProblemFactory;
    
    /**
     * This constructor will create a new instance of Random and use the regular constructor.
     * 
     * If you already have a Random object you are using then that constructor is recommended.
     * @param screenWidth
     * @param screenHeight
     */
    public MathProblemController(int screenWidth, int screenHeight) {
        this(new Random(), screenWidth, screenHeight);
    }
    
    /**
     * So that the same Random object may be used if so desired to get better random values.
     * @param rand 
     * @param screenWidth 
     * @param screenHeight 
     * 
     */
    public MathProblemController(Random rand, int screenWidth, int screenHeight) {
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
    
    private MathProblem nextMathProblem(int difficultyLevel) {
        MathProblem mathProb = mathProblemFactory.generateMathProblem(difficultyLevel);

        return mathProb;
    }
    
    public MathCluster nextMathCluster(int difficultyLevel) {
        
        MathCluster mathCluster = new MathCluster();
                
        int problemCount = generateProblemCount(difficultyLevel);
                
        for(int i = 0; i < problemCount; i++) {
            mathCluster.addMathProblem(nextMathProblem(difficultyLevel));
        }
        
        setClusterSpeedModifyer(mathCluster);
        
        generateStartingPositionForCluster(mathCluster);
                
        return mathCluster;
        
    }
    
    private int generateProblemCount(int difficultyLevel) {
        
        if(difficultyLevel <= 3) {
            
            return 1;
            
        } else if(difficultyLevel <= 10) {
            
            if(rand.nextInt(101) < 75) {
                return 1;
            } else {
                return 2;
            }
            
        } else {
            
            switch(rand.nextInt(101) / 10) {
                
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    return 1;
                case 6:
                case 7:
                    return 2;
                case 8:
                case 9:
                    return 3;
                default:
                    return 4;
                
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
        return rand.nextInt(maxValue-minValue) + minValue;
    }
    
    private static void setClusterSpeedModifyer(MathCluster mathCluster) {
        
        int normalQuestionsCount = mathCluster.getNormalQuestionsCount();
        
        if(normalQuestionsCount <= 1) {
            return;
        }        
        
        double speedModifier = 1.0;
        
        speedModifier *= Math.pow(CLUSTER_SPEED_MODIFIER_FACTOR, normalQuestionsCount - 1);
        
        System.out.println("Now setting speedModifyer to " + String.format("%.4f", speedModifier) 
                + " since normalQuestionCount: " + normalQuestionsCount);
        
        for(MathProblem mathProblem : mathCluster.getMathProblems()) {
            if(!mathProblem.isBonusQuestion()) mathProblem.setSpeedModifyer(speedModifier);
        }
        
    }
    
}