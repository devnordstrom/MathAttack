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
package se.devnordstrom.mathattack.gui.controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;
import se.devnordstrom.mathattack.answer.Answer;
import se.devnordstrom.mathattack.gui.entity.PaintableEntity;
import se.devnordstrom.mathattack.highscore.HighscoreEntry;
import se.devnordstrom.mathattack.difficulty.GameDifficulty;
import se.devnordstrom.mathattack.gui.entity.AnswerTextInputEntity;
import se.devnordstrom.mathattack.gui.entity.TextEntity;
import se.devnordstrom.mathattack.mathproblem.MathProblem;
import se.devnordstrom.mathattack.mathproblem.MathProblemController;
import se.devnordstrom.mathattack.mathproblem.wave.MathWave;
import se.devnordstrom.mathattack.mathproblem.wave.cluster.MathCluster;
import se.devnordstrom.mathattack.player.Player;
import se.devnordstrom.mathattack.util.Callable;

/**
 *
 * @author Orville Nordström
 */
public class GameController extends EntityController {
    
    private static final Color ANSWER_COLOR = Color.WHITE;
    
    private static final int ANSWER_TEXT_FIELD_WIDTH = 150; 
    private static final int ANSWER_TEXT_FIELD_HEIGHT = 30;
    private static final int ANSWER_FONT_SIZE = 18;
    private static final int WAVE_NAME_FONT_SIZE = 24;
    
    private MathProblemController mathProblemController;
            
    private String waveName;
    
    private int difficultyLevel, waveCount, groundSurfaceY, 
            screenWidth, screenHeight, waveNameX, waveNameY, answerX, answerY;
    
    private long milliDellay, intervalMillis;
    
    private boolean running = true;
    
    private boolean paused;
        
    private Answer lastAnswer;
    
    private List<MathProblem> mathProblemList = new ArrayList<>();
    
    private Player player;
    
    private MathWave currentMathWave;
    
    private GameDifficulty difficulty = GameDifficulty.NORMAL;
    
    private PaintableEntity groundEntity;
    
    private AnswerTextInputEntity answerTextInputEntity;
    
    private Callable<HighscoreEntry> showHighscoreCallable;
    
    /**
     * 
     * @param screenWidth
     * @param screenHeight 
     * @param showHighscoreCallable 
     */
    public GameController(int screenWidth, int screenHeight, Callable<HighscoreEntry> showHighscoreCallable) {
        this.screenWidth = screenWidth;
        
        this.screenHeight = screenHeight;
        
        this.showHighscoreCallable = showHighscoreCallable;
        
        this.waveNameX = (screenWidth / 2) - 50;
    
        this.waveNameY = (screenHeight / 2) - 100;
        
        this.answerX = screenWidth / 2;
        
        this.answerY = screenHeight - 300;
        
        this.mathProblemController = new MathProblemController(screenWidth, screenHeight);
        
        this.player = new Player();
        
        this.player.setHitPoints(1);
        
        setGroundEntity();        
        
        setAnswerInputTextField();
    }
    
    private void setGroundEntity() {
        groundEntity = (Graphics g) -> {
            g.setColor(Color.WHITE);
            
            g.fillRect(0, groundSurfaceY, screenWidth, screenHeight);
        };
    }
    
    private void setAnswerInputTextField() {
        int answerTextFieldX = (screenWidth - ANSWER_TEXT_FIELD_WIDTH) / 2;
        
        int answerTextFieldY = screenHeight - (ANSWER_TEXT_FIELD_HEIGHT + 50);

        Rectangle textFieldBody = new Rectangle(answerTextFieldX, answerTextFieldY, ANSWER_TEXT_FIELD_WIDTH, ANSWER_TEXT_FIELD_HEIGHT);

        answerTextInputEntity = new AnswerTextInputEntity(textFieldBody, (String input) -> {
                fireAnswer(input);
            });        
    }
    
    public KeyListener getKeyListener() {
        return answerTextInputEntity.getKeyListener();
    }
    
    /**
     * Creates the next MathWave and also increments the difficultyScore.
     * @return 
     */
    private MathWave nextMathWave() {
        
        MathWave mathWave = new MathWave(mathProblemController, getDifficultyLevel());
        
        return mathWave;
        
    }
    
    private int getDifficultyLevel() {
        
        int returnDifficultyLevel = 0;
        
        switch(difficulty) {
            
            case NORMAL:
                
                return difficultyLevel++;
                
            case HARD:
                
                returnDifficultyLevel = difficultyLevel + 3;
                
                difficultyLevel += 2;
                
                return returnDifficultyLevel;
                
            case EXTREME:
                
                returnDifficultyLevel = difficultyLevel + 5;
                
                difficultyLevel += 3;
                
                return returnDifficultyLevel;
                
            default:
                
                throw new UnsupportedOperationException("difficulty " + difficulty + " not supported.");
            
        }
        
    }
    
    /**
     * This method will be used for painting/moving/managing MathQuestions and the like. 
     * @param delta
     * @param updateLengthNanos
     */ 
    @Override
    public void moveEntities(double delta, long updateLengthNanos) {
        intervalMillis += updateLengthNanos / 1000_000;

        if(!running) {
            endGame();
            return;
        }
        
        if(currentMathWave == null ||
                (!currentMathWave.hasActiveQuestion()
                && !currentMathWave.hasNextCluster())
                && !hasActiveQuestions()) {
                        
            setWaveCount(getWaveCount() + 1);

            currentMathWave = nextMathWave();
            
            milliDellay = currentMathWave.getNewWaveDelayMillis();
            
            waveName = getWaveCount() + "";
            
            System.out.println("waveName -> " + waveName);
        }

        if(intervalMillis >= milliDellay) {
                        
            intervalMillis -= milliDellay;
            
            MathCluster mathCluster = currentMathWave.nextMathCluster();
            
            if(mathCluster != null) {
                for(MathProblem mathQuestion: mathCluster.getMathProblems()) {
                    addMathProblem(mathQuestion);
                }
            }
            
            if(waveName != null) {
                waveName = null;
                System.out.println("waveName has been reset.");
            }
            
            milliDellay = currentMathWave.getBaseQuestionIntervalMs();
        }
        
        evaluateAnswer();
        
        moveMathProblems(delta);
    }
    
    /**
     * Moves the MathProblems and also removes
     * @param delta 
     */
    private void moveMathProblems(double delta) {
        
        Iterator<MathProblem> mathProblemIterator = mathProblemList.iterator();
        
        while(mathProblemIterator.hasNext()) {
            
            MathProblem mathProblem = mathProblemIterator.next();
            
            if(mathProblem.isDestroyed()) {
                
                mathProblemIterator.remove();
                
                continue;
    
            }
            
            if(mathProblem.isBonusQuestion() 
                    && mathProblem.getBonusQuestionDirection() == MathProblem.BONUS_DIRECTION_RIGHT
                    && mathProblem.getX() < 0) {

                mathProblemIterator.remove();
                
                continue;
                
            } else if(mathProblem.isBonusQuestion() && (mathProblem.getX() > screenWidth)) {
                
                mathProblemIterator.remove();
                
                continue;
                
            }
            
            mathProblem.moveProblem(delta);
            
            if(getGroundSurfaceY() <= mathProblem.getY() 
                    && !mathProblem.isCrashed()) {
                
                mathProblem.setCrash();
                                
                player.hit();
                
                if(!player.isAlive()) {
                    stopGame();
                }
                
                continue;
                
            }
            
        }
        
    }

    /**
     * 
     * @param mathProblem 
     */
    private void addMathProblem(MathProblem mathProblem) {
        mathProblemList.add(mathProblem);
    }
    
    /**
     * 
     * @return 
     */
    public boolean hasActiveQuestions() {
        
        for (MathProblem mathProblem : mathProblemList) {
            if (mathProblem.isAlive() && !mathProblem.isBonusQuestion()) {
                return true;
            }
        }
        
        return false;
        
    }
    
    public boolean hasCrashedProblems() {
        
        for (MathProblem mathProblem : mathProblemList) {
            if(mathProblem.isCrashed()) {
                return true;
            }
        }
        
        return false;
        
    }
    
    public int getActiveQuestionsCount() {
        
        int count = 0;
        
        for(MathProblem mathProb: mathProblemList) {
            if(mathProb.isAlive() && !mathProb.isBonusQuestion()) count++;
        }
        
        return count;
        
    }
    
    /**
     * May be more than one answer in the future.
     */
    public void checkIfAnswersShouldBeAlive() {
        if(null != lastAnswer && lastAnswer.isAlive()) {
            lastAnswer.manageShouldKillAnswer();
        }
    }
    
    public void fireAnswer(String answer) {
        lastAnswer = new Answer(player, answer);
        
        player.incrementAnswerCount();                
    }
    
    public void evaluateAnswer() {
        
        checkIfAnswersShouldBeAlive();
        
        if(lastAnswer == null || !lastAnswer.isAlive()) {
            return;
        }
        
        
        for(MathProblem mathProblem : mathProblemList) {
            if(mathProblem.compareAnswer(lastAnswer) &&mathProblem.isAlive()) {
                mathProblem.hit();
                
                player.incrementSolvedProblemCount();
                
                player.addScore(mathProblem.getScore());
            }
        }
        
        /*
            for(int i = 0; i < mathProblemList.size(); i++) {  
                if(mathProblemList.get(i).compareAnswer(lastAnswer) && mathProblemList.get(i).isAlive()) {

                    mathProblemList.get(i).hit();

                    player.incrementSolvedProblemCount();

                    player.addScore(mathProblemList.get(i).getScore());

                }
            }
        */
    }
    
    private void stopGame() {
        System.out.println("stopGame() started in GameController.");
        setRunning(false);
    }
    
    /**
     * @return the isRunning
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @param running the running to set
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * @return the isPaused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * @param paused the isPaused to set
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * @return the lastAnswer
     */
    public Answer getLastAnswer() {
        return lastAnswer;
    }

    /**
     * @param lastAnswer the lastAnswer to set
     */
    public void setLastAnswer(Answer lastAnswer) {
        this.lastAnswer = lastAnswer;
    }

    /**
     * @return the mathProblemList
     */
    public List<MathProblem> getMathProblems() {
        return mathProblemList;
    }
    
    private PaintableEntity getWaveNameEntity() {
        TextEntity waveNameEntity = new TextEntity("Wave " + waveName, waveNameX, waveNameY);
        
        waveNameEntity.setColor(Color.WHITE);
        
        waveNameEntity.setFontSize(WAVE_NAME_FONT_SIZE);
        
        return waveNameEntity;
    }
    
    private List<PaintableEntity> getPlayerInfoEntities() {
        int fontSize = 14;
        Color color = Color.WHITE;
        
        TextEntity hpTextEntity = new TextEntity(player.getHitPoints() + " HP", 10, 50);
        hpTextEntity.setFontSize(fontSize);
        hpTextEntity.setColor(color);
        
        
        TextEntity waveCountTextEntity = new TextEntity("Wave: " + waveCount, 10, 75);
        waveCountTextEntity.setFontSize(fontSize);
        waveCountTextEntity.setColor(color);
        
        
        TextEntity scoreTextEntity = new TextEntity("Score: " + player.getScoreCount(), 10, 100);
        scoreTextEntity.setFontSize(fontSize);
        scoreTextEntity.setColor(color);
        
        
        
        List<PaintableEntity> entityList = new ArrayList<>();
        
        entityList.add(hpTextEntity);
        entityList.add(waveCountTextEntity);
        entityList.add(scoreTextEntity);
        
        return entityList;
    }
    
    private PaintableEntity getAnswerEntity() {
        if(lastAnswer == null || !lastAnswer.isAlive()) {
            return null;
        }
        
        TextEntity answerTextEntity = new TextEntity(lastAnswer.getAnswerAsString(), 
                answerX, answerY, ANSWER_COLOR);

        answerTextEntity.setFontSize(ANSWER_FONT_SIZE);
        
        return answerTextEntity;
    }
    
    @Override
    public List<PaintableEntity> getEntities() {
        
        List<PaintableEntity> entityList = new ArrayList<>();
        
        entityList.addAll(getMathProblems());
        
        if(waveName != null) {
            entityList.add(getWaveNameEntity());
        }
            
        entityList.addAll(getPlayerInfoEntities());
        
        
        PaintableEntity answerEntity = getAnswerEntity();
        
        if(null != answerEntity) entityList.add(answerEntity);
        
        entityList.add(groundEntity);
        
        entityList.add(answerTextInputEntity);
        
        //May add powerups or some other entities later on.
        
        return entityList;
    }

    /**
     * @param mathProblemList the mathProblemList to set
     */
    public void setMathProblemList(List<MathProblem> mathProblemList) {
        this.mathProblemList = mathProblemList;
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }
    

    
    /**
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return the waveName
     */
    public String getWaveName() {
        return waveName;
    }

    /**
     * @param waveName the waveName to set
     */
    public void setWaveName(String waveName) {
        this.waveName = waveName;
    }

    /**
     * @return the groundSurfaceY
     */
    public int getGroundSurfaceY() {
        return groundSurfaceY;
    }

    /**
     * @param groundSurfaceY the groundSurfaceY to set
     */
    public void setGroundSurfaceY(int groundSurfaceY) {
        this.groundSurfaceY = groundSurfaceY;
    }
    
    public void setDifficulty(GameDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * @return the waveCount
     */
    public int getWaveCount() {
        return waveCount;
    }

    /**
     * @param waveCount the waveCount to set
     */
    public void setWaveCount(int waveCount) {
        this.waveCount = waveCount;
    }
    
    /**
     * 
     * @return 
     */
    public HighscoreEntry createHighscoreEntry() {
        Player player = this.getPlayer();
        
        HighscoreEntry highscoreEntry = new HighscoreEntry();
        
        highscoreEntry.setCreatedAt(new Date());
        
        highscoreEntry.setDifficulty(difficulty);
        highscoreEntry.setScore(player.getScoreCount());        
        highscoreEntry.setAnswerCount(player.getAnswerCount());
        highscoreEntry.setCompletedWaves(waveCount-1);
        highscoreEntry.setSolvedProblemCount(player.getSolvedProblemsCount());
        
        return highscoreEntry;        
    }
    
    /**
     * This should/will be called by the GameLoopController and will call the highscore callable.
     */
    public void endGame() {
        showHighscoreCallable.call(createHighscoreEntry());
    }
    
    @Override
    public EventListener[] getEventListeners() {
        return new EventListener[]{answerTextInputEntity.getKeyListener()};
    }
}