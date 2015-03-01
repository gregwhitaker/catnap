package com.catnap.core.query.model;

/**
 * Created by Woody on 1/11/14.
 */
public abstract class SimpleExpression implements Expression
{
    protected String field;
    protected Operator operator;
    protected String operand;

    public SimpleExpression(String field, Operator operator, String operand)
    {
        this.field = field;
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public String getField()
    {
        return field;
    }

    @Override
    public Operator getOperator()
    {
        return operator;
    }

    @Override
    public String getOperand()
    {
        return operand;
    }
}
