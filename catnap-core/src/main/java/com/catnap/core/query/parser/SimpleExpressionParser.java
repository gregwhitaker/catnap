package com.catnap.core.query.parser;

import com.catnap.core.exception.QuerySyntaxException;
import com.catnap.core.query.QuerySyntax;
import com.catnap.core.query.model.*;

/**
 * Created by Woody on 1/11/14.
 */
public class SimpleExpressionParser implements ExpressionParser<SimpleExpression>
{
    public static final char EXPRESSION_OPEN = '[';
    public static final char EXPRESSION_CLOSE = ']';

    @Override
    public SimpleExpression parse(String expression)
    {
        String buffer = new String(expression);
        SimpleExpression parsedExpression = null;

        //Check buffer for equality expressions (i.e. =, !=, <, >, <=, >=)
        if(parsedExpression == null)
        {
            parsedExpression = findEqualityExpression(buffer);
        }

        if(parsedExpression == null)
        {
            throw new QuerySyntaxException(buffer, "No valid expression operators found");
        }

        return parsedExpression;
    }

    private SimpleEqualityExpression findEqualityExpression(String buffer)
    {
        if(buffer.indexOf(SimpleEqualityExpression.EqualityOperator.NOT_EQUAL.getNotation()) != -1)
        {
            String[] operands = buffer.split(SimpleEqualityExpression.EqualityOperator.NOT_EQUAL.getNotation(), 2);
            return new SimpleEqualityExpression(operands[0], SimpleEqualityExpression.EqualityOperator.NOT_EQUAL, operands[1]);
        }
        else if(buffer.indexOf(SimpleEqualityExpression.EqualityOperator.LESS_THAN_OR_EQUAL.getNotation()) != -1)
        {
            String[] operands = buffer.split(SimpleEqualityExpression.EqualityOperator.LESS_THAN_OR_EQUAL.getNotation(), 2);
            return new SimpleEqualityExpression(operands[0], SimpleEqualityExpression.EqualityOperator.LESS_THAN_OR_EQUAL, operands[1]);
        }
        else if(buffer.indexOf(SimpleEqualityExpression.EqualityOperator.GREATER_THAN_OR_EQUAL.getNotation()) != -1)
        {
            String[] operands = buffer.split(SimpleEqualityExpression.EqualityOperator.GREATER_THAN_OR_EQUAL.getNotation(), 2);
            return new SimpleEqualityExpression(operands[0], SimpleEqualityExpression.EqualityOperator.GREATER_THAN_OR_EQUAL, operands[1]);
        }
        else if(buffer.indexOf(SimpleEqualityExpression.EqualityOperator.LESS_THAN.getNotation()) != -1)
        {
            String[] operands = buffer.split(SimpleEqualityExpression.EqualityOperator.LESS_THAN.getNotation(), 2);
            return new SimpleEqualityExpression(operands[0], SimpleEqualityExpression.EqualityOperator.LESS_THAN, operands[1]);
        }
        else if(buffer.indexOf(SimpleEqualityExpression.EqualityOperator.GREATER_THAN.getNotation()) != -1)
        {
            String[] operands = buffer.split(SimpleEqualityExpression.EqualityOperator.GREATER_THAN.getNotation(), 2);
            return new SimpleEqualityExpression(operands[0], SimpleEqualityExpression.EqualityOperator.GREATER_THAN, operands[1]);
        }
        else if(buffer.indexOf(SimpleEqualityExpression.EqualityOperator.EQUAL.getNotation()) != -1)
        {
            String[] operands = buffer.split(SimpleEqualityExpression.EqualityOperator.EQUAL.getNotation(), 2);
            return new SimpleEqualityExpression(operands[0], SimpleEqualityExpression.EqualityOperator.EQUAL, operands[1]);
        }

        return null;
    }

    @Override
    public QuerySyntax getSyntax()
    {
        return QuerySyntax.Simple;
    }
}
