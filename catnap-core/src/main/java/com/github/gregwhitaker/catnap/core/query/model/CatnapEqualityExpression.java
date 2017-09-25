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

import com.github.gregwhitaker.catnap.core.query.processor.Property;
import com.github.gregwhitaker.catnap.core.util.ConverterUtil;
import org.apache.commons.lang.NotImplementedException;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Supports evaluation of equality expressions (key=value, key!=value, etc.).
 */
public class CatnapEqualityExpression extends CatnapExpression {

    /**
     * Equality operators used in the Catnap Query Language.
     */
    public enum EqualityOperator implements Operator {
        EQUAL("="),
        NOT_EQUAL("!="),
        LESS_THAN("<"),
        GREATER_THAN(">"),
        LESS_THAN_OR_EQUAL("<="),
        GREATER_THAN_OR_EQUAL(">=");

        private final String notation;

        EqualityOperator(final String notation) {
            this.notation = notation;
        }

        @Override
        public String getNotation() {
            return notation;
        }
    }

    public CatnapEqualityExpression(String field, EqualityOperator operator, String value) {
        super(field, operator, value);
    }

    @Override
    public boolean evaluate(Property propertyToCompare) {
        if (propertyToCompare.isPrimitive()) {
            switch ((EqualityOperator) getOperator()) {
                case EQUAL:
                    return isEqual(propertyToCompare.getValue());
                case NOT_EQUAL:
                    return isNotEqual(propertyToCompare.getValue());
                case LESS_THAN:
                    return isLessThan(propertyToCompare.getValue());
                case GREATER_THAN:
                    return isGreaterThan(propertyToCompare.getValue());
                case LESS_THAN_OR_EQUAL:
                    return isLessThanOrEqual(propertyToCompare.getValue());
                case GREATER_THAN_OR_EQUAL:
                    return isGreaterThanOrEqual(propertyToCompare.getValue());
                default:
                    throw new NotImplementedException("Unsupported equality operator [" + getOperator().getNotation() + "]");
            }
        }

        return false;
    }

    private boolean isEqual(Object valueToCheck) {
        if (valueToCheck != null) {
            if (String.class.isAssignableFrom(valueToCheck.getClass())) {
                return valueToCheck.equals(getValue());
            } else if (Boolean.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Boolean) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Boolean.class)) == 0;
            } else if (Integer.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Integer) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Integer.class)) == 0;
            } else if (Double.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Double) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Double.class)) == 0;
            } else if (Byte.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Byte) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Byte.class)) == 0;
            } else if (Float.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Float) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Float.class)) == 0;
            } else if (Long.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Long) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Long.class)) == 0;
            } else if (Short.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Short) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Short.class)) == 0;
            } else if (BigDecimal.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((BigDecimal) valueToCheck).compareTo(ConverterUtil.convert(getValue(), BigDecimal.class)) == 0;
            } else if (BigInteger.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((BigInteger) valueToCheck).compareTo(ConverterUtil.convert(getValue(), BigInteger.class)) == 0;
            }
        }

        return false;
    }

    private boolean isNotEqual(Object valueToCheck) {
        if (valueToCheck != null) {
            if (String.class.isAssignableFrom(valueToCheck.getClass())) {
                return !valueToCheck.equals(getValue());
            } else if (Boolean.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Boolean) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Boolean.class)) != 0;
            } else if (Integer.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Integer) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Integer.class)) != 0;
            } else if (Double.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Double) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Double.class)) != 0;
            } else if (Byte.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Byte) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Byte.class)) != 0;
            } else if (Float.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Float) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Float.class)) != 0;
            } else if (Long.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Long) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Long.class)) != 0;
            } else if (Short.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Short) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Short.class)) != 0;
            } else if (BigDecimal.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((BigDecimal) valueToCheck).compareTo(ConverterUtil.convert(getValue(), BigDecimal.class)) != 0;
            } else if (BigInteger.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((BigInteger) valueToCheck).compareTo(ConverterUtil.convert(getValue(), BigInteger.class)) != 0;
            }
        }

        return false;
    }

    private boolean isLessThan(Object valueToCheck) {
        if (valueToCheck != null) {
            if (String.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((String) valueToCheck).compareTo(getValue()) < 0;
            } else if (Integer.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Integer) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Integer.class)) < 0;
            } else if (Double.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Double) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Double.class)) < 0;
            } else if (Byte.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Byte) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Byte.class)) < 0;
            } else if (Float.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Float) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Float.class)) < 0;
            } else if (Long.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Long) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Long.class)) < 0;
            } else if (Short.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Short) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Short.class)) < 0;
            } else if (BigDecimal.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((BigDecimal) valueToCheck).compareTo(ConverterUtil.convert(getValue(), BigDecimal.class)) < 0;
            } else if (BigInteger.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((BigInteger) valueToCheck).compareTo(ConverterUtil.convert(getValue(), BigInteger.class)) < 0;
            }
        }

        return false;
    }

    private boolean isGreaterThan(Object valueToCheck) {
        if (valueToCheck != null) {
            if (String.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((String) valueToCheck).compareTo(getValue()) > 0;
            } else if (Integer.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Integer) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Integer.class)) > 0;
            } else if (Double.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Double) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Double.class)) > 0;
            } else if (Byte.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Byte) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Byte.class)) > 0;
            } else if (Float.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Float) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Float.class)) > 0;
            } else if (Long.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Long) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Long.class)) > 0;
            } else if (Short.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((Short) valueToCheck).compareTo(ConverterUtil.convert(getValue(), Short.class)) > 0;
            } else if (BigDecimal.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((BigDecimal) valueToCheck).compareTo(ConverterUtil.convert(getValue(), BigDecimal.class)) > 0;
            } else if (BigInteger.class.isAssignableFrom(valueToCheck.getClass())) {
                return ((BigInteger) valueToCheck).compareTo(ConverterUtil.convert(getValue(), BigInteger.class)) > 0;
            }
        }

        return false;
    }

    private boolean isLessThanOrEqual(Object valueToCheck) {
        return (isLessThan(valueToCheck) || isEqual(valueToCheck));
    }

    private boolean isGreaterThanOrEqual(Object valueToCheck) {
        return (isGreaterThan(valueToCheck) || isEqual(valueToCheck));
    }
}
