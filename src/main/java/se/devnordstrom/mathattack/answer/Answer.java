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
package se.devnordstrom.mathattack.answer;

import se.devnordstrom.mathattack.player.Player;

/**
 *
 * @author Orville Nordström
 */
public class Answer {
    
    private String answerText;
    
    private Player player;
    
    /**
     * That is how many problems were "hit" or solved by this answer object.
     */
    private int hitProblemCount = 0;
    
    private long answerCreatedAt;
    
    private static final long ANSWER_LIFE_MILLI = 500; //So answer should be alive for this time in milli seconds.
    
    private boolean isAlive;
    
    public Answer(Player player, String answertext) {
        
        answerText = answertext;
        
        isAlive = true;
        
        this.player = player;
        
        answerCreatedAt = System.currentTimeMillis();
        
    }
    
    public String getAnswerAsString() {
        return answerText;
    }
    
    /**
     * @return Double so as to allow null values(If the user didn't enter a valid number)
     */
    public Double getAnswerAsDouble() {
        try {
            return Double.valueOf(answerText);
        } catch(Exception ex) {
            return Double.NaN;
        }
    }
    
    public boolean isAlive() {
        return isAlive;
    }
    
    public void setIsAlive(boolean newIsalive) {
        
        isAlive = newIsalive;
        
        if(!isAlive && hitProblemCount == 0) {
            player.incrementDryHitCount();
        }
        
    }
    
    /**
     * 
     * @return 
     */
    public Integer getAnswerAsInteger() {
        try {
            return Integer.valueOf(answerText);
        } catch(Exception ex) {
            return null;
        }
    }
    
    /**
     * Evaluates if enough time has passed for the answer to become "dead" or "inactive" 
     * and in that case kills the answer.
     */
    public void manageShouldKillAnswer() {
        
       long currentMillis = System.currentTimeMillis();
       
       if((answerCreatedAt + ANSWER_LIFE_MILLI) <= currentMillis) {
            setIsAlive(false);
       }
        
    }
}
