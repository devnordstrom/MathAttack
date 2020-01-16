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
package se.devnordstrom.mathattack.logic;

import se.devnordstrom.mathattack.gui.GameScreenController;
import se.devnordstrom.mathattack.gui.controller.GameController;
import se.devnordstrom.mathattack.util.DoubleCallable;

/**
 * This class manages the loop
 *
 * This is a modified version of the loop found here http://www.java-gaming.org/index.php?topic=24220.0
 * 
 */
public class GameLoopController implements Runnable {
    
    private long lastLoopTimeNanos = System.nanoTime();
    
    private final int TARGET_FPS = 60;
    
    private final long OPTIMAL_TIME_NANOS = 100_000_0000 / TARGET_FPS;

    private long showFps;
   
    private volatile boolean paused;
    
    private volatile boolean running = true;
    
    private GameScreenController gameScreenController;
        
    public GameLoopController(GameScreenController gameScreenController) {
        this.gameScreenController = gameScreenController;
    }
    
    @Override
    public void run() {         
        while(isRunning()) {
            if(!isPaused()) {

                long currentNanos = System.nanoTime();
                
                long updateLengthNanos = currentNanos - lastLoopTimeNanos;
                
                lastLoopTimeNanos = currentNanos;
                                
                double delta = updateLengthNanos / ((double)OPTIMAL_TIME_NANOS);
                
                gameScreenController.moveEntities(delta, updateLengthNanos);
                                                
                long delayTimeNanos = (lastLoopTimeNanos - System.nanoTime()) + OPTIMAL_TIME_NANOS;
                
                delay(delayTimeNanos/1000_000);
                
            } else {
                
                delay(1);
                
            }
        }
        
    }
    
    /**
     * Sleeps the execution using Thread.sleep(millis) also catching the 
     * InterruptedException itself (In this program it is assumed that 
     * InterruptedException will never be thrown and thus it is ignored).
     * 
     * If the value of millis is less than or equal to zero then the 
     * thread will sleep for 1 millisecond.
     * 
     * @param millis Milliseconds for the thread to "sleep".
     */
    private void delay(long millis) {
        try {

            if(millis <= 0) millis = 1; 

            Thread.sleep(millis);
            
        } catch (InterruptedException ex) {
        }
    }

    public long getFps() {
        return showFps;
    }

    /**
     * @return the paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * @param paused the paused to set
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    
    /**
     * 
     */
    public void stop() {
        running = false;
    }
    
    public boolean isRunning() {
        return running;
    }
}