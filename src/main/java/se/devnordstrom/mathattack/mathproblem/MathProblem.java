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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import se.devnordstrom.mathattack.answer.Answer;
import se.devnordstrom.mathattack.gui.entity.PaintableEntity;

/**
 *
 * @author Orville Nordström
 */
public abstract class MathProblem implements PaintableEntity {
    
    public static final int OPERATOR_PLUS = 1;
    
    public static final int OPERATOR_MINUS = 2;
    
    public static final int OPERATOR_DIVIDE = 3;
    
    public static final int OPERATOR_MULTIPLY = 4;
    
    public static final int OPERATOR_MODULUS = 5;
    
    public static final int OPERATOR_EQUALS = 6;
    
    public static final int OPERATOR_SQUARE_ROOT = 7;
    
    
    protected List<MathOperand> mathOperandList;
    
    protected double x, y;
    
    protected int width, height, hp;
    
    private int score;
    
    protected Color mainColor, textColor;
    
    protected Random rand;
        
    protected int firstOperand;
    
    protected int secondOperand;
    
    private long hitTimeStamp;
    
    protected double moveSpeed = 0.75;
    
    protected double moveMinSpeed = 0.75;
    
    protected double moveMaxSpeed = 4.0;
    
    public static int baseScore = 10;
    
    protected double answer;
    
    protected String questionString;
    
    protected static final int EASY_OPERAND_MAX = 5;
    
    protected static final Color DEFAULT_COLOR = Color.white;
        
    public static final int BONUS_DIRECTION_LEFT = 1;
    
    public static final int BONUS_DIRECTION_RIGHT = -1;
    
    private boolean isBonusQuestion;
    
    private boolean crashed = false;
    
    private int bonusQuestionDirection = 1;
    
    private double speedModifyer = 1.0;
    
    public MathProblem(Random rand) {
        
        mainColor = DEFAULT_COLOR;
        
        textColor = Color.WHITE;
        
        mathOperandList = new ArrayList<>();
        
        hp = 1;
        
        this.rand = rand;
        
        isBonusQuestion = false;
        
        score = baseScore;
        
    }

    private Color getBonusColor() {
        
        int r = rand.nextInt(256);
        
        int g = rand.nextInt(256);
        
        int b = rand.nextInt(256);
        
        return new Color(r, g, b);
        
    }
    
    private Color getCrashedColor() {
        
        int whiteVal = rand.nextInt(156) + 100;
        
        return new Color(whiteVal, whiteVal, whiteVal);
        
    }
    
    public int getX() {
        return (int) x;
    }

    public void setX(double newX) {
        this.x = newX;
    }
    
    public int getY() {
        return (int) y;
    }
    
    public void setY(double newY) {
        this.y = newY;
    }
    
    public double getMinMoveSpeed() {
        return this.moveMinSpeed;
    }
    
    public double getMaxMoveSpeed() {
        return this.moveMaxSpeed;
    }
    
    public double getMoveSpeed() {
        return this.moveSpeed * speedModifyer; 
    }
    
    public void setMoveSpeed(double newMoveSpeed) {
        
        this.moveSpeed = newMoveSpeed;
       
        if(this.moveSpeed > this.moveMaxSpeed) this.moveSpeed = this.moveMaxSpeed;

    }
    
    public void setColor(Color c) {
        this.mainColor = c;
    }
    
    @Override
    public void paint(Graphics g) {
            
        if(isDestroyed()) {
            return;
        }
        
        Color originalColor = g.getColor();
        
        Font originalFont = g.getFont();
        
        g.setColor(mainColor);
        
        if(!isBonusQuestion) {
            g.setColor(Color.WHITE);
        } else {
            g.setColor(getBonusColor());
        }
        
        g.setFont(new Font("default", Font.BOLD, 26));
        
        if(isAlive()) {
             
            g.drawString(getQuestionString(), getX(), getY());
            
        } else if(isCrashed()) {
                                
            g.setColor(getCrashedColor());

            g.drawString("CRASHED", getX(), getY());

            g.fillOval(getX(), (int)y, 50, 50);
                
        } else {
                
            g.drawString(this.getScore() + " +", getX(), getY());
                                        
        }
        
        g.setColor(originalColor);
                
        g.setFont(originalFont);
        
    }
    
    /**
     * This could be quicker if the argument was specified to be a double.
     * 
     * But some subclasses may have a letter such as X as answer. And the performance penalty is
     * insignificant.
     * 
     * @param answer
     * @return 
     */
    public boolean compareAnswer(Answer answer) {
        
        if(Double.isNaN(answer.getAnswerAsDouble())) return false;
        
        return this.answer == answer.getAnswerAsDouble();
        
    }
    
    public void setAnswer(double newAnswer) {
        answer = newAnswer;
    }
    
    public void setQuestionString(String newQuestionString) {
        this.questionString = newQuestionString;
    }
    
    public void moveProblem(double delta) {
                
        if(!isAlive()) {
            return;
        }
           
        if(isBonusQuestion()) {
                        
            this.x += this.getMoveSpeed() * delta * getBonusQuestionDirection();
                        
        } else {
            
            this.y += this.getMoveSpeed() * delta;
            
        }
        
    }

    public void hit() {
        
        hp--;
        
        if(hp == 0) {
            hitTimeStamp = System.currentTimeMillis();
        }
        
    }
    
    public void setCrash() {
        
        crashed = true;
        
        hp = 0;
        
        hitTimeStamp = System.currentTimeMillis();
        
    }
    
    public boolean isCrashed() {
        return crashed;
    }
    
    public void generateQuestion() {
        
        MathOperand operand = null;
        
        int maxValue = EASY_OPERAND_MAX;
        
        for(int i = 0; i < 3; i++) {
            
            if(i % 2 == 0) {
    
                operand = new MathOperand(MathProblem.OPERATOR_PLUS);
                
                operand.setOperator(true);
                
            } else {
                
                //Evaluates so that the answer will not become negative.
                if(mathOperandList.size() == 2 && mathOperandList.get(1).isOperator() && mathOperandList.get(1).getValue() == MathProblem.OPERATOR_MINUS) {
                    
                    int firstOperandValue = (int) mathOperandList.get(0).getValue();
                    
                    maxValue = EASY_OPERAND_MAX - firstOperandValue;
                    
                }
                
                operand = new MathOperand(rand.nextInt(maxValue) + 1);
                
            }
         
            mathOperandList.add(operand);
            
        }
        
        mathOperandList.add(new MathOperand(rand.nextInt(EASY_OPERAND_MAX)));        
        
        MathProblemUtil.setMathProblemAnswer(this);
        
    }

    public List<MathOperand> getMathOperandList() {
        return this.mathOperandList;
    }

    public boolean isAlive() {
        return hp > 0;
    }
 
    public boolean isDestroyed() {
        
        if(isAlive()) return false;
        
        long value = System.currentTimeMillis() - hitTimeStamp;
        
        return value >= 500;

    }
    
    public boolean isBonusQuestion() {
        return isBonusQuestion;
    }
    
    public void setBonusQuestion(boolean isBonusQuestion) {
        this.isBonusQuestion = isBonusQuestion;
    }
    
    public String getQuestionString() {
        
        if(questionString == null) {
            throw new IllegalStateException("The questionString has not been set!");
        }
        
        return this.questionString;
    }
    
    public int getScore() {
        return (int) Math.round(score * speedModifyer);
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public static int getBaseScore() {
        return baseScore;
    }

    /**
     * @return the bonusQuestionDirection
     */
    public int getBonusQuestionDirection() {
        return bonusQuestionDirection;
    }

    /**
     * @param bonusQuestionDirection the bonusQuestionDirection to set
     */
    public void setBonusQuestionDirection(int bonusQuestionDirection) {
        this.bonusQuestionDirection = bonusQuestionDirection;
    }

    /**
     * @return the speedModifyer
     */
    public double getSpeedModifyer() {
        return speedModifyer;
    }

    /**
     * @param speedModifyer the speedModifyer to set
     */
    public void setSpeedModifyer(double speedModifyer) {
        this.speedModifyer = speedModifyer;
    }
    
    @Override
    public String toString() {
        return getQuestionString();
    }
    
}