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

package com.github.gregwhitaker.catnap.core.query.parser;

import com.github.gregwhitaker.catnap.core.exception.QuerySyntaxException;
import com.github.gregwhitaker.catnap.core.query.QuerySyntax;
import com.github.gregwhitaker.catnap.core.query.model.CatnapEqualityExpression;
import com.github.gregwhitaker.catnap.core.query.model.CatnapExpression;

public class CatnapExpressionParser implements ExpressionParser<CatnapExpression> {
    public static final char EXPRESSION_OPEN = '[';
    public static final char EXPRESSION_CLOSE = ']';

    @Override
    public CatnapExpression parse(String expression) {
        String buffer = expression;

        //Check buffer for equality expressions (i.e. =, !=, <, >, <=, >=)
        CatnapExpression parsedExpression = findEqualityExpression(buffer);

        if (parsedExpression == null) {
            throw new QuerySyntaxException(buffer, "No valid expression operators found");
        }

        return parsedExpression;
    }

    private CatnapEqualityExpression findEqualityExpression(String buffer) {
        if (buffer.contains(CatnapEqualityExpression.EqualityOperator.NOT_EQUAL.getNotation())) {
            String[] operands = buffer.split(CatnapEqualityExpression.EqualityOperator.NOT_EQUAL.getNotation(), 2);
            return new CatnapEqualityExpression(operands[0], CatnapEqualityExpression.EqualityOperator.NOT_EQUAL, operands[1]);
        } else if (buffer.contains(CatnapEqualityExpression.EqualityOperator.LESS_THAN_OR_EQUAL.getNotation())) {
            String[] operands = buffer.split(CatnapEqualityExpression.EqualityOperator.LESS_THAN_OR_EQUAL.getNotation(), 2);
            return new CatnapEqualityExpression(operands[0], CatnapEqualityExpression.EqualityOperator.LESS_THAN_OR_EQUAL, operands[1]);
        } else if (buffer.contains(CatnapEqualityExpression.EqualityOperator.GREATER_THAN_OR_EQUAL.getNotation())) {
            String[] operands = buffer.split(CatnapEqualityExpression.EqualityOperator.GREATER_THAN_OR_EQUAL.getNotation(), 2);
            return new CatnapEqualityExpression(operands[0], CatnapEqualityExpression.EqualityOperator.GREATER_THAN_OR_EQUAL, operands[1]);
        } else if (buffer.contains(CatnapEqualityExpression.EqualityOperator.LESS_THAN.getNotation())) {
            String[] operands = buffer.split(CatnapEqualityExpression.EqualityOperator.LESS_THAN.getNotation(), 2);
            return new CatnapEqualityExpression(operands[0], CatnapEqualityExpression.EqualityOperator.LESS_THAN, operands[1]);
        } else if (buffer.contains(CatnapEqualityExpression.EqualityOperator.GREATER_THAN.getNotation())) {
            String[] operands = buffer.split(CatnapEqualityExpression.EqualityOperator.GREATER_THAN.getNotation(), 2);
            return new CatnapEqualityExpression(operands[0], CatnapEqualityExpression.EqualityOperator.GREATER_THAN, operands[1]);
        } else if (buffer.contains(CatnapEqualityExpression.EqualityOperator.EQUAL.getNotation())) {
            String[] operands = buffer.split(CatnapEqualityExpression.EqualityOperator.EQUAL.getNotation(), 2);
            return new CatnapEqualityExpression(operands[0], CatnapEqualityExpression.EqualityOperator.EQUAL, operands[1]);
        }

        return null;
    }

    @Override
    public QuerySyntax getSyntax() {
        return QuerySyntax.CATNAP;
    }
}
