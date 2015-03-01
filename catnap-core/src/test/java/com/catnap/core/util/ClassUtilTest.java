package com.catnap.core.util;

import static org.junit.Assert.*;
import org.junit.Test;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public class ClassUtilTest
{
    static abstract class BaseMockModel
    {
        private String baseProperty = "basePropertyValue";

        public String getBaseProperty() { return baseProperty; }

        public void setBaseProperty(String baseProperty) { this.baseProperty = baseProperty; }
    }

    static class MockModel extends BaseMockModel
    {
        private String property = "propertyValue";
        private String unreadableProperty = "unreadablePropertyValue";

        public String getProperty() { return property; }

        public void setProperty(String property) { this.property = property; }
    }

    @Test
    public void nullIsNotPrimitive()
    {
        assertFalse(ClassUtil.isPrimitiveType(null));
    }

    @Test
    public void booleanIsPrimitive()
    {
        assertTrue(ClassUtil.isPrimitiveType(boolean.class));
    }

    @Test
    public void stringIsPrimitive()
    {
        assertTrue(ClassUtil.isPrimitiveType(String.class));
    }

    @Test
    public void numberIsPrimitive()
    {
        assertTrue(ClassUtil.isPrimitiveType(Number.class));
    }

    @Test
    public void characterIsPrimitive()
    {
        assertTrue(ClassUtil.isPrimitiveType(Character.class));
    }

    @Test
    public void dateIsPrimitive()
    {
        //Date is considered a primitive type according to Catnap, even though it is not.
        assertTrue(ClassUtil.isPrimitiveType(Date.class));
    }

    @Test
    public void getAllReadablePropertiesAsMap()
    {
        Map<String, PropertyDescriptor> props = ClassUtil.getReadablePropertiesAsMap(MockModel.class);

        assertEquals(2, props.size());
        assertTrue(props.containsKey("baseProperty"));
        assertTrue(props.containsKey("property"));
    }

    @Test
    public void getReadableProperty()
    {
        PropertyDescriptor descriptor = ClassUtil.getReadableProperty("property", MockModel.class);

        assertEquals("property", descriptor.getName());
    }

    @Test
    public void getReadablePropertyForUnknownPropertyReturnsNull()
    {
        assertNull(ClassUtil.getReadableProperty("invalidProperty", MockModel.class));
    }

    @Test
    public void loadingNullInstanceOfClassReturnsNull()
    {
        assertNull(ClassUtil.loadClass(null));
    }

    @Test
    public void loadClass()
    {
        assertTrue(ClassUtil.loadClass(new Boolean(true)).isAssignableFrom(Boolean.class));
    }
}
