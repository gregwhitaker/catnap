package com.github.gregwhitaker.catnap.core.query.model;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SimpleQueryTest {

    @Test
    public void addParameter() {
        SimpleQuery query = new SimpleQuery();
        query.addParameter("param1");

        assertTrue(query.getParameters().containsKey("param1"));
    }

    @Test
    public void addParameterWithSubquery() {
        SimpleQuery subquery = new SimpleQuery();
        subquery.addParameter("subParam1");

        SimpleQuery query = new SimpleQuery();
        query.addParameter("param1", subquery);

        assertNotNull(query.getParameters().get("param1"));
        assertTrue(query.getParameters().get("param1").getParameters().containsKey("subParam1"));
    }

    @Test
    public void getParameterNames() {
        SimpleQuery query = new SimpleQuery();
        query.addParameter("param1");
        query.addParameter("param2");
        query.addParameter("param3");

        assertTrue(query.getParameterNames().contains("param1"));
        assertTrue(query.getParameterNames().contains("param2"));
        assertTrue(query.getParameterNames().contains("param3"));
    }

    @Test
    public void getParameterCount() {
        SimpleQuery query = new SimpleQuery();
        query.addParameter("param1");
        query.addParameter("param2");
        query.addParameter("param3");

        assertEquals(3, query.getParameterCount());
    }

    @Test
    public void containsParameter() {
        SimpleQuery query = new SimpleQuery();
        query.addParameter("param1");

        assertTrue(query.containsParameter("param1"));
        assertFalse(query.containsParameter("param2"));
    }

    @Test
    public void containsSubquery() {
        SimpleQuery query = new SimpleQuery();
        query.addParameter("param1", new SimpleQuery());

        assertTrue(query.containsSubquery("param1"));
        assertFalse(query.containsSubquery("param2"));
    }
}
