package com.catnap.core.query.processor;

import com.catnap.core.exception.ViewRenderException;
import com.catnap.core.util.ClassUtil;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests verifying correct sorting of field declarations.
 *
 * @author gwhit7
 */
public class FieldDeclarationComparatorTest
{
    static abstract class MockBaseModel
    {
        String property1 = "value1";

        public String getProperty1()
        {
            return property1;
        }
    }

    static class MockModel extends MockBaseModel
    {
        String property2 = "value2";

        public String getProperty2()
        {
            return property2;
        }
    }

    static class MockProperty implements Property<MockModel>
    {
        @Override
        public String getName()
        {
            return "unknownProperty";
        }

        @Override
        public Object getValue()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public Method getReadMethod()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isPrimitive()
        {
            throw new UnsupportedOperationException();
        }
    }

    private FieldDeclarationComparator<MockModel> comparator = new FieldDeclarationComparator<MockModel>(MockModel.class);
    private Property<MockModel> property1;
    private Property<MockModel> property2;

    @Before
    public void setup()
    {
        MockModel model = new MockModel();
        property1 = new SimpleProperty<MockModel>(model, ClassUtil.getReadableProperty("property1", MockModel.class));
        property2 = new SimpleProperty<MockModel>(model, ClassUtil.getReadableProperty("property2", MockModel.class));
    }

    @Test
    public void compareLessThan()
    {
        assertEquals(-1, comparator.compare(property1, property2));
    }

    @Test
    public void compareEqualTo()
    {
        assertEquals(0, comparator.compare(property1, property1));
    }

    @Test
    public void compareGreaterThan()
    {
        assertEquals(1, comparator.compare(property2, property1));
    }

    @Test(expected = ViewRenderException.class)
    public void unknownProperty1ThrowsException()
    {
        comparator.compare(new MockProperty(), property2);
    }

    @Test(expected = ViewRenderException.class)
    public void unknownProperty2ThrowsException()
    {
        comparator.compare(property1, new MockProperty());
    }
}
