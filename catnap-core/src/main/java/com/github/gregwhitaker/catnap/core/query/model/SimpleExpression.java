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
