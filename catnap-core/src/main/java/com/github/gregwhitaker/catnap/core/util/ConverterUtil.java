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

package com.github.gregwhitaker.catnap.core.util;

import com.github.gregwhitaker.catnap.core.exception.QuerySyntaxException;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public final class ConverterUtil {

    static class ConverterLookupKey {
        private final Class<?> fromClazz;
        private final Class<?> toClazz;

        public ConverterLookupKey(final Class<?> fromClazz, final Class<?> toClazz) {
            this.fromClazz = fromClazz;
            this.toClazz = toClazz;
        }

        public Class<?> getFromClazz() {
            return fromClazz;
        }

        public Class<?> getToClazz() {
            return toClazz;
        }

        @Override
        public String toString() {
            return fromClazz.getName() + "_" + toClazz.getName();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ConverterLookupKey that = (ConverterLookupKey) o;

            if (!fromClazz.equals(that.fromClazz)) return false;
            if (!toClazz.equals(that.toClazz)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = fromClazz.hashCode();
            result = 31 * result + toClazz.hashCode();
            return result;
        }
    }

    private static final Map<ConverterLookupKey, Method> CONVERTERS = new HashMap<ConverterLookupKey, Method>(20);

    static {
        //Load Converters
        for (Method method : ConverterUtil.class.getDeclaredMethods()) {
            if (method.getName() != "convert") {
                CONVERTERS.put(new ConverterLookupKey(method.getParameterTypes()[0], method.getReturnType()), method);
            }
        }
    }

    private ConverterUtil() {
        //Hiding default constructor
    }

    /**
     * Converts the supplied value to the supplied type.
     *
     * @param value   the value to convert
     * @param toClazz the type to which to convert
     * @param <T>     the type to which to convert
     * @return
     */
    public static <T> T convert(Object value, Class<T> toClazz) {
        if (value == null) {
            return null;
        }

        //If we can straight up cast then just go ahead and do it.
        if (toClazz.isAssignableFrom(value.getClass())) {
            return toClazz.cast(value);
        }

        Method converter = CONVERTERS.get(new ConverterLookupKey(value.getClass(), toClazz));

        if (converter == null) {
            throw new UnsupportedOperationException(String.format("Converter that supports converting " +
                    "from [%s] to [%s] does not exist.", value.getClass().getName(), toClazz.getName()));
        }

        try {
            return toClazz.cast(converter.invoke(toClazz, value));
        } catch (Exception e) {
            throw new QuerySyntaxException(String.format("Cannot convert value [%s] from [%s] to [%s]",
                    value, value.getClass().getName(), toClazz.getName()));
        }
    }

    /**
     * Converts {@link java.lang.String} to {@link java.lang.Byte}
     *
     * @param value String value to be converted
     * @return converted Byte
     */
    public static Byte toByte(String value) {
        return Byte.parseByte(value);
    }

    /**
     * Converts {@link java.lang.String} to {@link java.lang.Double}
     *
     * @param value String value to be converted
     * @return converted Double
     */
    public static Double toDouble(String value) {
        return Double.parseDouble(value);
    }

    /**
     * Converts {@link java.lang.String} to {@link java.lang.Float}
     *
     * @param value String value to be converted
     * @return converted Float
     */
    public static Float toFloat(String value) {
        return Float.parseFloat(value);
    }

    /**
     * Converts {@link java.lang.String} to {@link java.lang.Integer}
     *
     * @param value String value to be converted
     * @return converted Integer
     */
    public static Integer toInteger(String value) {
        return Integer.parseInt(value);
    }

    /**
     * Converts {@link java.lang.String} to {@link java.lang.Long}
     *
     * @param value String value to be converted
     * @return converted Long
     */
    public static Long toLong(String value) {
        return Long.parseLong(value);
    }

    /**
     * Converts {@link java.lang.String} to {@link java.lang.Short}
     *
     * @param value String value to be converted
     * @return converted Short
     */
    public static Short toShort(String value) {
        return Short.parseShort(value);
    }

    /**
     * Converts {@link java.lang.String} to {@link java.math.BigDecimal}
     *
     * @param value String value to be converted
     * @return converted BigDecimal
     */
    public static BigDecimal toBigDecimal(String value) {
        return BigDecimal.valueOf(toDouble(value));
    }

    /**
     * Converts {@link java.lang.String} to {@link java.math.BigInteger}
     *
     * @param value String value to be converted
     * @return converted BigInteger
     */
    public static BigInteger toBigInteger(String value) {
        return BigInteger.valueOf(toLong(value));
    }

    /**
     * Converts {@link java.lang.String} to {@link java.lang.Boolean}
     *
     * @param value String value to be converted
     * @return converted Boolean
     */
    public static Boolean toBoolean(String value) {
        if (value.equalsIgnoreCase("true") ||
                value.equalsIgnoreCase("T") ||
                value.equalsIgnoreCase("1")) {
            return true;
        }

        return false;
    }
}
