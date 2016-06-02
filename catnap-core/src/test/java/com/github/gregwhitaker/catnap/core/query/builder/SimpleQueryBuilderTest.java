package com.github.gregwhitaker.catnap.core.query.builder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleQueryBuilderTest {

    @Test
    public void verifyDefaultQueryParameterName() {
        CatnapQueryBuilder builder = new CatnapQueryBuilder();
        assertEquals("fields", builder.getQueryParameter());
    }

    @Test
    public void verifyCustomQueryParameterName() {
        CatnapQueryBuilder builder = new CatnapQueryBuilder();
        builder.setQueryParameter("myFields");

        assertEquals("myFields", builder.getQueryParameter());
    }
}
