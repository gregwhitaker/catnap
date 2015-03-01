package com.catnap.core.query.model;

import com.catnap.core.query.processor.Property;
import com.catnap.core.util.ConverterUtil;
import org.apache.commons.lang.NotImplementedException;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by Woody on 1/11/14.
 */
public class SimpleEqualityExpression extends SimpleExpression
{
    public enum EqualityOperator implements Operator
    {
        EQUAL("="),
        NOT_EQUAL("!="),
        LESS_THAN("<"),
        GREATER_THAN(">"),
        LESS_THAN_OR_EQUAL("<="),
        GREATER_THAN_OR_EQUAL(">=");

        private final String notation;

        private EqualityOperator(final String notation)
        {
            this.notation = notation;
        }

        @Override
        public String getNotation()
        {
            return notation;
        }
    }

    public SimpleEqualityExpression(String field, EqualityOperator operator, String operand)
    {
        super(field, operator, operand);
    }

    @Override
    public boolean evaluate(Property leftOperand)
    {
        if(leftOperand.isPrimitive())
        {
            switch((EqualityOperator) getOperator())
            {
                case EQUAL:
                    return isEqual(leftOperand.getValue());
                case NOT_EQUAL:
                    return isNotEqual(leftOperand.getValue());
                case LESS_THAN:
                    return isLessThan(leftOperand.getValue());
                case GREATER_THAN:
                    return isGreaterThan(leftOperand.getValue());
                case LESS_THAN_OR_EQUAL:
                    return isLessThanOrEqual(leftOperand.getValue());
                case GREATER_THAN_OR_EQUAL:
                    return isGreaterThanOrEqual(leftOperand.getValue());
                default:
                    throw new NotImplementedException("Unsupported equality operator [" + getOperator().getNotation() + "]");
            }
        }

        return false;
    }

    private boolean isEqual(Object value)
    {
        if(String.class.isAssignableFrom(value.getClass()))
        {
            return value.equals(getOperand());
        }
        else if(Boolean.class.isAssignableFrom(value.getClass()))
        {
            return (((Boolean) value).compareTo(ConverterUtil.convert(getOperand(), Boolean.class)) == 0) ? true : false;
        }
        else if(Integer.class.isAssignableFrom(value.getClass()))
        {
            return (((Integer) value).compareTo(ConverterUtil.convert(getOperand(), Integer.class)) == 0) ? true : false;
        }
        else if(Double.class.isAssignableFrom(value.getClass()))
        {
            return (((Double) value).compareTo(ConverterUtil.convert(getOperand(), Double.class)) == 0) ? true : false;
        }
        else if(Byte.class.isAssignableFrom(value.getClass()))
        {
            return (((Byte) value).compareTo(ConverterUtil.convert(getOperand(), Byte.class)) == 0) ? true : false;
        }
        else if(Float.class.isAssignableFrom(value.getClass()))
        {
            return (((Float) value).compareTo(ConverterUtil.convert(getOperand(), Float.class)) == 0) ? true : false;
        }
        else if(Long.class.isAssignableFrom(value.getClass()))
        {
            return (((Long) value).compareTo(ConverterUtil.convert(getOperand(), Long.class)) == 0) ? true : false;
        }
        else if(Short.class.isAssignableFrom(value.getClass()))
        {
            return (((Short) value).compareTo(ConverterUtil.convert(getOperand(), Short.class)) == 0) ? true : false;
        }
        else if(BigDecimal.class.isAssignableFrom(value.getClass()))
        {
            return (((BigDecimal) value).compareTo(ConverterUtil.convert(getOperand(), BigDecimal.class)) == 0) ? true : false;
        }
        else if(BigInteger.class.isAssignableFrom(value.getClass()))
        {
            return (((BigInteger) value).compareTo(ConverterUtil.convert(getOperand(), BigInteger.class)) == 0) ? true : false;
        }

        return true;
    }

    private boolean isNotEqual(Object value)
    {
        if(String.class.isAssignableFrom(value.getClass()))
        {
            return !value.equals(getOperand());
        }
        else if(Boolean.class.isAssignableFrom(value.getClass()))
        {
            return (((Boolean) value).compareTo(ConverterUtil.convert(getOperand(), Boolean.class)) != 0) ? true : false;
        }
        else if(Integer.class.isAssignableFrom(value.getClass()))
        {
            return (((Integer) value).compareTo(ConverterUtil.convert(getOperand(), Integer.class)) != 0) ? true : false;
        }
        else if(Double.class.isAssignableFrom(value.getClass()))
        {
            return (((Double) value).compareTo(ConverterUtil.convert(getOperand(), Double.class)) != 0) ? true : false;
        }
        else if(Byte.class.isAssignableFrom(value.getClass()))
        {
            return (((Byte) value).compareTo(ConverterUtil.convert(getOperand(), Byte.class)) != 0) ? true : false;
        }
        else if(Float.class.isAssignableFrom(value.getClass()))
        {
            return (((Float) value).compareTo(ConverterUtil.convert(getOperand(), Float.class)) != 0) ? true : false;
        }
        else if(Long.class.isAssignableFrom(value.getClass()))
        {
            return (((Long) value).compareTo(ConverterUtil.convert(getOperand(), Long.class)) != 0) ? true : false;
        }
        else if(Short.class.isAssignableFrom(value.getClass()))
        {
            return (((Short) value).compareTo(ConverterUtil.convert(getOperand(), Short.class)) != 0) ? true : false;
        }
        else if(BigDecimal.class.isAssignableFrom(value.getClass()))
        {
            return (((BigDecimal) value).compareTo(ConverterUtil.convert(getOperand(), BigDecimal.class)) != 0) ? true : false;
        }
        else if(BigInteger.class.isAssignableFrom(value.getClass()))
        {
            return (((BigInteger) value).compareTo(ConverterUtil.convert(getOperand(), BigInteger.class)) != 0) ? true : false;
        }

        return true;
    }

    private boolean isLessThan(Object value)
    {
        if(String.class.isAssignableFrom(value.getClass()))
        {
            return (((String) value).compareTo(getOperand()) < 0) ? true : false;
        }
        else if(Integer.class.isAssignableFrom(value.getClass()))
        {
            return (((Integer) value).compareTo(ConverterUtil.convert(getOperand(), Integer.class)) < 0) ? true : false;
        }
        else if(Double.class.isAssignableFrom(value.getClass()))
        {
            return (((Double) value).compareTo(ConverterUtil.convert(getOperand(), Double.class)) < 0) ? true : false;
        }
        else if(Byte.class.isAssignableFrom(value.getClass()))
        {
            return (((Byte) value).compareTo(ConverterUtil.convert(getOperand(), Byte.class)) < 0) ? true : false;
        }
        else if(Float.class.isAssignableFrom(value.getClass()))
        {
            return (((Float) value).compareTo(ConverterUtil.convert(getOperand(), Float.class)) < 0) ? true : false;
        }
        else if(Long.class.isAssignableFrom(value.getClass()))
        {
            return (((Long) value).compareTo(ConverterUtil.convert(getOperand(), Long.class)) < 0) ? true : false;
        }
        else if(Short.class.isAssignableFrom(value.getClass()))
        {
            return (((Short) value).compareTo(ConverterUtil.convert(getOperand(), Short.class)) < 0) ? true : false;
        }
        else if(BigDecimal.class.isAssignableFrom(value.getClass()))
        {
            return (((BigDecimal) value).compareTo(ConverterUtil.convert(getOperand(), BigDecimal.class)) < 0) ? true : false;
        }
        else if(BigInteger.class.isAssignableFrom(value.getClass()))
        {
            return (((BigInteger) value).compareTo(ConverterUtil.convert(getOperand(), BigInteger.class)) < 0) ? true : false;
        }

        return true;
    }

    private boolean isGreaterThan(Object value)
    {
        if(String.class.isAssignableFrom(value.getClass()))
        {
            return (((String) value).compareTo(getOperand()) > 0) ? true : false;
        }
        else if(Integer.class.isAssignableFrom(value.getClass()))
        {
            return (((Integer) value).compareTo(ConverterUtil.convert(getOperand(), Integer.class)) > 0) ? true : false;
        }
        else if(Double.class.isAssignableFrom(value.getClass()))
        {
            return (((Double) value).compareTo(ConverterUtil.convert(getOperand(), Double.class)) > 0) ? true : false;
        }
        else if(Byte.class.isAssignableFrom(value.getClass()))
        {
            return (((Byte) value).compareTo(ConverterUtil.convert(getOperand(), Byte.class)) > 0) ? true : false;
        }
        else if(Float.class.isAssignableFrom(value.getClass()))
        {
            return (((Float) value).compareTo(ConverterUtil.convert(getOperand(), Float.class)) > 0) ? true : false;
        }
        else if(Long.class.isAssignableFrom(value.getClass()))
        {
            return (((Long) value).compareTo(ConverterUtil.convert(getOperand(), Long.class)) > 0) ? true : false;
        }
        else if(Short.class.isAssignableFrom(value.getClass()))
        {
            return (((Short) value).compareTo(ConverterUtil.convert(getOperand(), Short.class)) > 0) ? true : false;
        }
        else if(BigDecimal.class.isAssignableFrom(value.getClass()))
        {
            return (((BigDecimal) value).compareTo(ConverterUtil.convert(getOperand(), BigDecimal.class)) > 0) ? true : false;
        }
        else if(BigInteger.class.isAssignableFrom(value.getClass()))
        {
            return (((BigInteger) value).compareTo(ConverterUtil.convert(getOperand(), BigInteger.class)) > 0) ? true : false;
        }

        return true;
    }

    private boolean isLessThanOrEqual(Object value)
    {
        return (isLessThan(value) || isEqual(value));
    }

    private boolean isGreaterThanOrEqual(Object value)
    {
        return (isGreaterThan(value) || isEqual(value));
    }
}
