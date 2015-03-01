package com.catnap.core.util;

import com.catnap.core.exception.QuerySyntaxException;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public class ConverterUtilTest
{
    @Test
    public void convertNullReturnsNull()
    {
        assertNull(ConverterUtil.convert(null, Boolean.class));
    }

    @Test
    public void convertingAssignableTypesDoesNotUseTypeCoercion()
    {
        assertEquals(123, (int) ConverterUtil.convert(123, Integer.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void unsupportedConversionTypeThrowsException()
    {
        ConverterUtil.convert(new Object(), Integer.class);
    }

    @Test(expected = QuerySyntaxException.class)
    public void incompatibleConversionThrowsException()
    {
        ConverterUtil.convert("abc", Integer.class);
    }

    @Test
    public void convertStringToByte()
    {
        Byte value = assertIsInstanceOf(Byte.class, ConverterUtil.convert("123", Byte.class));
        assertEquals((byte) 123, (byte) value);
    }

    @Test
    public void convertStringToDouble()
    {
        Double value = assertIsInstanceOf(Double.class, ConverterUtil.convert("123.45", Double.class));
        assertEquals(123.45, (double) value, 0.1);
    }

    @Test
    public void convertStringToFloat()
    {
        Float value = assertIsInstanceOf(Float.class, ConverterUtil.convert("123.45", Float.class));
        assertEquals(123.45f, (float) value, 0.1);
    }

    @Test
    public void convertStringToInteger()
    {
        Integer value = assertIsInstanceOf(Integer.class, ConverterUtil.convert("123", Integer.class));
        assertEquals(123, (int) value);
    }

    @Test
    public void convertStringToLong()
    {
        Long value = assertIsInstanceOf(Long.class, ConverterUtil.convert("123", Long.class));
        assertEquals(123, (long) value);
    }

    @Test
    public void convertStringToShort()
    {
        Short value = assertIsInstanceOf(Short.class, ConverterUtil.convert("123", Short.class));
        assertEquals(123, (short) value);
    }

    @Test
    public void convertStringToBigDecimal()
    {
        BigDecimal expected = new BigDecimal(123.45).setScale(2, RoundingMode.DOWN);
        BigDecimal value = assertIsInstanceOf(BigDecimal.class, ConverterUtil.convert("123.45", BigDecimal.class));
        assertEquals(expected, value);
    }

    @Test
    public void convertStringToBigInteger()
    {
        BigInteger value = assertIsInstanceOf(BigInteger.class, ConverterUtil.convert("123", BigInteger.class));
        assertEquals(new BigInteger("123"), value);
    }

    @Test
    public void convertStringToBoolean()
    {
        Boolean value1 = assertIsInstanceOf(Boolean.class, ConverterUtil.convert("true", Boolean.class));
        Boolean value2 = assertIsInstanceOf(Boolean.class, ConverterUtil.convert("T", Boolean.class));
        Boolean value3 = assertIsInstanceOf(Boolean.class, ConverterUtil.convert("1", Boolean.class));
        Boolean value4 = assertIsInstanceOf(Boolean.class, ConverterUtil.convert("false", Boolean.class));

        assertTrue(value1);
        assertTrue(value2);
        assertTrue(value3);
        assertFalse(value4);
    }

    /**
     * Helper method to assert that the supplied object is not null and is of the specified type.
     * @param expectedType the type that the supplied object is expected to be
     * @param value the object to assert
     * @return The supplied object cast to the specified type if object is not null and of the
     * specified type.
     */
    private static <T> T assertIsInstanceOf(Class<T> expectedType, Object value)
    {
        assertNotNull("Expected an instance of type: " + expectedType.getName() + " but was null.", value);

        assertTrue(String.format("Object should be a %s but was: %s with type: %s.",
                expectedType.getName(), value, value.getClass().getName()),
                expectedType.isInstance(value));

        return expectedType.cast(value);
    }
}
