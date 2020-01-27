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
package se.devnordstrom.mathattack.mathproblem.wave;

import java.util.ArrayList;
import java.util.List;
import se.devnordstrom.mathattack.mathproblem.MathProblem;
import se.devnordstrom.mathattack.mathproblem.MathProblemClusterFactory;
import se.devnordstrom.mathattack.mathproblem.wave.cluster.MathCluster;

/**
 *
 * @author Orville Nordström
 */
public class MathWave implements Wave {
    
    private static final int DEFAULT_PROBLEM_COUNT = 8;
    
    private static final int BASE_QUESTION_INTERVAL_MILLIS = 2 * 1000;
    
    private static final double INTERVAL_DECREASE_RATE = 0.95;
    
    private static final long DEFAULT_NEW_WAVE_DELAY = 5 * 1000;
    
    private int mathClusterCount;
    
    private static int waveCount;
        
    private final int waveIndex;
    
    private long questionIntervalMillis = BASE_QUESTION_INTERVAL_MILLIS;
    
    private long newWaveDelayMs = DEFAULT_NEW_WAVE_DELAY;
    
    private int waveDifficultyLevel, currentMathProblemIndex;
            
    private MathProblemClusterFactory mathProblemController;
    
    private List<MathCluster> mathClusterList;
    
    public MathWave(MathProblemClusterFactory mathProblemController, int waveDifficultyLevel) {
            
        this.waveIndex = ++waveCount;
                
        this.waveDifficultyLevel = waveDifficultyLevel;
        
        this.mathClusterList = new ArrayList<>();
        
        this.mathProblemController = mathProblemController;
                
        setQuestionIntevalMillis(waveDifficultyLevel);
        
        generateMathProblemClusters();
        
    }
    
    private void setQuestionIntevalMillis(int difficultyLevel) {
        questionIntervalMillis = Math.round(BASE_QUESTION_INTERVAL_MILLIS 
                * Math.pow(INTERVAL_DECREASE_RATE, difficultyLevel));
    }
    
    /**
     * Uses the mathProblemController object to generate math problems based on the wave index and the baaseProblemCount.
     */
    private void generateMathProblemClusters() {
        
        mathClusterCount = getProblemCount();
                
        for(int i = 0; i < mathClusterCount; i++) {            
            mathClusterList.add(mathProblemController.generateMathCluster(waveDifficultyLevel));
        }

    }
    
    private int getProblemCount() {
        return DEFAULT_PROBLEM_COUNT + (int)Math.round(Math.log(waveDifficultyLevel-1));
    }
    
    @Override
    public int getWaveIndex() {
        return waveIndex;
    }
 
    public void setDifficulty(int difficulty) {
        this.waveDifficultyLevel = difficulty;
    }
    
    @Override
    public int getDifficulty() {
        return this.waveDifficultyLevel;
    }

    @Override
    public boolean hasNextCluster() {
        return mathClusterList.size() > currentMathProblemIndex;
    }
    
    @Override
    public MathCluster nextMathCluster() {
        
        if(!hasNextCluster()) {
            return null;
        }
        
        MathCluster mathCluster = mathClusterList.get(currentMathProblemIndex);
        
        currentMathProblemIndex++;
        
        return mathCluster;
        
    }
    
    public long getBaseQuestionIntervalMs() {
        return this.questionIntervalMillis;
    }
    
    public long getNewWaveDelayMillis() {
        return newWaveDelayMs;
    }
    
    public int getRemainingMathQuestionsCount() {
        return this.mathClusterList.size() - (currentMathProblemIndex + 1);
    }
    
    public boolean hasActiveQuestion() {
                
        for(MathCluster mathCluster : mathClusterList) {
            for(MathProblem mathProb : mathCluster.getMathProblems()) {
                if(mathProb.isAlive() && !mathProb.isBonusQuestion()) return true;
            }
        }
        
        return false;
        
    }
    
    public boolean hasActiveBonusQuestion() {
        
        for(MathCluster mathCluster : mathClusterList) {
            for(MathProblem mathProb : mathCluster.getMathProblems()) {
                if(mathProb.isAlive() && mathProb.isBonusQuestion()) return true;
            }
        }
        
        return false;
        
    }
    
}