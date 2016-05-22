/*
 * Copyright 2016 Greg Whitaker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.gregwhitaker.catnap.core.query.model;

/**
 *
 */
public abstract class SimpleExpression implements Expression {
    protected String field;
    protected Operator operator;
    protected String operand;

    /**
     *
     * @param field
     * @param operator
     * @param operand
     */
    public SimpleExpression(String field, Operator operator, String operand) {
        this.field = field;
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public Operator getOperator() {
        return operator;
    }

    @Override
    public String getOperand() {
        return operand;
    }
}
