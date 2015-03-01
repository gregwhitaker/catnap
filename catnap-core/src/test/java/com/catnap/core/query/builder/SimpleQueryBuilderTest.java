package com.catnap.core.query.builder;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public class SimpleQueryBuilderTest
{
    @Test
    public void verifyDefaultQueryParameterName()
    {
        SimpleQueryBuilder builder = new SimpleQueryBuilder();
        assertEquals("fields", builder.getQueryParameter());
    }

    @Test
    public void verifyCustomQueryParameterName()
    {
        SimpleQueryBuilder builder = new SimpleQueryBuilder();
        builder.setQueryParameter("myFields");

        assertEquals("myFields", builder.getQueryParameter());
    }
}
