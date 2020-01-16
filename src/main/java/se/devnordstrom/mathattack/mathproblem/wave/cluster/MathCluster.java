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
package se.devnordstrom.mathattack.mathproblem.wave.cluster;

import java.util.ArrayList;
import java.util.List;
import se.devnordstrom.mathattack.mathproblem.MathProblem;

/**
 *
 * @author Orville Nordström
 */
public class MathCluster {
    
    private List<MathProblem> mathProblemList;
    
    public MathCluster() {
        mathProblemList = new ArrayList<>();
    }
    
    public int getMathProblemCount() {
        return mathProblemList.size();
    }

    public void addMathProblem(MathProblem mathQuestion) {
        mathProblemList.add(mathQuestion);       
    }

    /**
     * 
     * @return 
     */
    public List<MathProblem> getMathProblems() {
        return mathProblemList;
    }
    
    /**
     * 
     * @return 
     */
    public int getNormalQuestionsCount() {
        
        int normalQuestionsCount = 0;
        
        for(MathProblem mathProblem : mathProblemList) {
            if(!mathProblem.isBonusQuestion()) normalQuestionsCount++;
        }
        
        return normalQuestionsCount;
        
    }
    
    /**
     * 
     * @return 
     */
    public int getBonusQuestionsCount() {
        
        int bonusQuestionsCount = 0;
        
        for(MathProblem mathProblem : mathProblemList) {
            if(mathProblem.isBonusQuestion()) bonusQuestionsCount++;
        }
        
        return bonusQuestionsCount;
        
    }
    
}
