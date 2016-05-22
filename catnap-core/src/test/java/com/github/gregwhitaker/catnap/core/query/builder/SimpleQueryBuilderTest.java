package com.github.gregwhitaker.catnap.core.query.builder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleQueryBuilderTest {

    @Test
    public void verifyDefaultQueryParameterName() {
        SimpleQueryBuilder builder = new SimpleQueryBuilder();
        assertEquals("fields", builder.getQueryParameter());
    }

    @Test
    public void verifyCustomQueryParameterName() {
        SimpleQueryBuilder builder = new SimpleQueryBuilder();
        builder.setQueryParameter("myFields");

        assertEquals("myFields", builder.getQueryParameter());
    }
}
