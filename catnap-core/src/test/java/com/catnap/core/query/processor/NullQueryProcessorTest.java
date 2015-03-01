package com.catnap.core.query.processor;

import static org.junit.Assert.*;

import com.catnap.core.query.model.Query;
import com.catnap.core.query.model.SimpleQuery;
import org.junit.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public class NullQueryProcessorTest
{
    static class MockModel
    {
        private String property1 = "value1";
        private String property2 = "value2";

        public String getProperty1()
        {
            return property1;
        }

        public String getProperty2()
        {
            return property2;
        }
    }

    @Test
    public void supports()
    {
        NullQueryProcessor processor = new NullQueryProcessor();
        assertTrue(processor.supports(null));
    }

    @Test
    public void doesNotSupport()
    {
        NullQueryProcessor processor = new NullQueryProcessor();
        assertFalse(processor.supports(SimpleQuery.class));
    }

    @Test
    public void processInternalReturnsAllFields()
    {
        NullQueryProcessor processor = new NullQueryProcessor();
        List<Property<MockModel>> properties = processor.processInternal(null, new MockModel(), MockModel.class);

        assertEquals(2, properties.size());
    }
}
