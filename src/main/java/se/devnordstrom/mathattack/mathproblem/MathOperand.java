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

import java.util.ArrayList;
import java.util.List;

/**
 * This may represent a number, an operator, a variable 
 * or an expression containing several sub operands.
 * @author Orville Nordström
 */
public class MathOperand {
    
    private boolean number, operator, variable, expression;
    
    private int value;
    
    private List<MathOperand> subOperandList = new ArrayList<>();   //Used if this is an expression.
    
    /**
     * Instantiates a MathOperand that is a number with the value zero.
     */
    public MathOperand() {
        this(0, false);
    }
    
    /**
     *  The value can be either a magic constant representing an operand or a double representing the value.
     *  
     *  As per default the MathOperand object will be set to be a number
     * 
     * @param value 
     */
    public MathOperand(int value) {
        this(value, false);
    }
    
    /**
     * 
     * @param value
     * @param operator 
     */
    public MathOperand(int value, boolean operator) {
        
        this.value = value;
        
        this.number = !operator;
        
        this.operator = operator;
                
    }
    
    public boolean isNumber() {
        return number;
    }
    
    public boolean isOperator() {
        return operator;
    }
    
    public boolean isVariable() {
        return variable;
    }
    
    public int getValue() {
        return value;
    }
    
    public void setNumber(boolean isnumber) {
        
        this.number = isnumber;
        
        this.operator = !isnumber;    //It should never be possible to have both of these to the same value.
        
    }
    
    public void setOperator(boolean isoperator) {        
        
        this.operator = isoperator;
        
        this.number = !isoperator;
        
    }
    
    public void setVariable(boolean isvariable) {
        this.variable = isvariable;
    }

    /**
     * @return the expression
     */
    public boolean isExpression() {
        return expression;
    }

    /**
     * @param expression the expression to set
     */
    public void setExpression(boolean expression) {
        this.expression = expression;
    }
    
    public void addSubOperand(MathOperand operand) {
        subOperandList.add(operand);
    }
    
    public List<MathOperand> getSubOperandList() {
        return subOperandList;
    }
    
}