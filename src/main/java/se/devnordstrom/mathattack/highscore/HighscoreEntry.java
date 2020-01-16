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
package se.devnordstrom.mathattack.highscore;

import java.io.Serializable;
import java.util.Date;
import se.devnordstrom.mathattack.difficulty.GameDifficulty;

/**
 *
 * @author Orville Nordström
 */
public class HighscoreEntry implements Serializable, Comparable<HighscoreEntry> {
    
    private Date createdAt;
    
    private String playerName;
    
    private GameDifficulty difficulty;
    
    private int score, answerCount, solvedProblemCount, 
            bonusQuestionCount, powerupCount, completedWaves;
    
    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the playerName
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * @param playerName the playerName to set
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * @return the difficulty
     */
    public GameDifficulty getDifficulty() {
        return difficulty;
    }

    /**
     * @param difficulty the difficulty to set
     */
    public void setDifficulty(GameDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return the answerCount
     */
    public int getAnswerCount() {
        return answerCount;
    }

    /**
     * @param answerCount the answerCount to set
     */
    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    /**
     * @return the solvedProblemCount
     */
    public int getSolvedProblemCount() {
        return solvedProblemCount;
    }

    /**
     * @param solvedProblemCount the solvedProblemCount to set
     */
    public void setSolvedProblemCount(int solvedProblemCount) {
        this.solvedProblemCount = solvedProblemCount;
    }

    /**
     * @return the bonusQuestionCount
     */
    public int getBonusQuestionCount() {
        return bonusQuestionCount;
    }

    /**
     * @param bonusQuestionCount the bonusQuestionCount to set
     */
    public void setBonusQuestionCount(int bonusQuestionCount) {
        this.bonusQuestionCount = bonusQuestionCount;
    }

    /**
     * @return the powerupCount
     */
    public int getPowerupCount() {
        return powerupCount;
    }

    /**
     * @param powerupCount the powerupCount to set
     */
    public void setPowerupCount(int powerupCount) {
        this.powerupCount = powerupCount;
    }
    
    /**
     * @return the completedWaves
     */
    public int getCompletedWaves() {
        return completedWaves;
    }

    /**
     * @param completedWaves the completedWaves to set
     */
    public void setCompletedWaves(int completedWaves) {
        this.completedWaves = completedWaves;
    }
    
    public void incrementCompleatedWaves() {
        this.completedWaves++;
    }
    
    /**
     * Compares the high score entries first by score, 
     * then by answers and finally by when they were created.
     * 
     * 
     * <b>NOTE</b> this will <b>NOT</b> take difficulty into account.
     * 
     * @param compareHighscore
     * @return 
     */
    @Override
    public int compareTo(HighscoreEntry compareHighscore) {
        if(compareHighscore == null) throw new NullPointerException();
        
        if(this.getScore() != compareHighscore.getScore()) {
            Integer score = getScore();
            Integer compareScore = compareHighscore.getScore();
            
            return score.compareTo(compareScore);
        } else if(getCreatedAt() != null && compareHighscore.getCreatedAt() != null) {
            return this.getCreatedAt().compareTo(compareHighscore.getCreatedAt());
        } else {
            return 0;       
        }
    }
}