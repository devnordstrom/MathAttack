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
package se.devnordstrom.mathattack.player;

/**
 *
 * @author Orville Nordström
 */
public class Player {
    
    public static final int DEFAULT_HITPOINTS = 4;
    
    private static final double DEFAULT_SCORE_MODIFYER = 1.0;
    
    private int bonusLifeCount, powerUpCount, scoreCount, hitPoints, hitCount, 
            answerCount, dryShotCount, solvedProblemsCount, solvedBonusProblemsCount;
    
    /**
     * This is used if the player for example has a powerup that gives 
     * them 50 percent more points that would be 1.5;
     */
    private double scoreModifyer = 1.0;
    
    private String name;
        
    public Player() {
        hitPoints = DEFAULT_HITPOINTS;
    }
    
    public int getScoreCount() {
        return scoreCount;
    }
    
    public void addScore(double score) {
        scoreCount += (int) scoreModifyer * score;
    }
    
    public void setScoreModifyer(double newScoreModifyer) {
        this.scoreModifyer = newScoreModifyer;
    }
    
    public double getScoreModifyer() {
        return this.scoreModifyer;
    }
    
    public void resetScoreModifyer() {
        this.scoreModifyer = DEFAULT_SCORE_MODIFYER;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isAlive() {
        return hitPoints > 0;
    }
    
    public int getHitPoints() {
        return hitPoints;
    }
    
    public void setHitPoints(int newHitPoints)
    {
        this.hitPoints = newHitPoints;
    }
    
    public void hit() {
        
        hitPoints--;
        
        incrementHitCount();
        
    }
    
    public void incrementAnswerCount() {
        answerCount++;
    }
    
    public int getAnswerCount() {
        return answerCount;
    }
    
    public void incrementSolvedProblemCount() {
        solvedProblemsCount++;
    }
    
    public int getSolvedProblemsCount() {
        return solvedProblemsCount;
    }
    
    public void incrementHitCount() {
        hitCount++;
    }
    
    public int getHitCount() {
        return hitCount;
    }
    
    public void incrementDryHitCount() {
        dryShotCount++;
    }
    
    public int getDryHitCount() {
        return dryShotCount;
    }
    
    public void incrementBonusLifeCount() {
        bonusLifeCount++;
    }

    public int getBonusLifeCount() {
        return bonusLifeCount;
    }
    
    public void incrementPowerUpCount() {
        powerUpCount++;
    }
    
    public int getPowerUpCount() {
        return powerUpCount;
    }

    /**
     * @return the solvedBonusProblemsCount
     */
    public int getSolvedBonusProblemsCount() {
        return solvedBonusProblemsCount;
    }

    /**
     * @param solvedBonusProblemsCount the solvedBonusProblemsCount to set
     */
    public void setSolvedBonusProblemsCount(int solvedBonusProblemsCount) {
        this.solvedBonusProblemsCount = solvedBonusProblemsCount;
    }
    
}