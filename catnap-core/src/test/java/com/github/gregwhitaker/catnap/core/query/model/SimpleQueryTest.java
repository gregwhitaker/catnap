package com.github.gregwhitaker.catnap.core.query.model;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SimpleQueryTest {

    @Test
    public void addParameter() {
        CatnapQuery query = new CatnapQuery();
        query.addParameter("param1");

        assertTrue(query.getParameters().containsKey("param1"));
    }

    @Test
    public void addParameterWithSubquery() {
        CatnapQuery subquery = new CatnapQuery();
        subquery.addParameter("subParam1");

        CatnapQuery query = new CatnapQuery();
        query.addParameter("param1", subquery);

        assertNotNull(query.getParameters().get("param1"));
        assertTrue(query.getParameters().get("param1").getParameters().containsKey("subParam1"));
    }

    @Test
    public void getParameterNames() {
        CatnapQuery query = new CatnapQuery();
        query.addParameter("param1");
        query.addParameter("param2");
        query.addParameter("param3");

        assertTrue(query.getParameterNames().contains("param1"));
        assertTrue(query.getParameterNames().contains("param2"));
        assertTrue(query.getParameterNames().contains("param3"));
    }

    @Test
    public void getParameterCount() {
        CatnapQuery query = new CatnapQuery();
        query.addParameter("param1");
        query.addParameter("param2");
        query.addParameter("param3");

        assertEquals(3, query.getParameterCount());
    }

    @Test
    public void containsParameter() {
        CatnapQuery query = new CatnapQuery();
        query.addParameter("param1");

        assertTrue(query.containsParameter("param1"));
        assertFalse(query.containsParameter("param2"));
    }

    @Test
    public void containsSubquery() {
        CatnapQuery query = new CatnapQuery();
        query.addParameter("param1", new CatnapQuery());

        assertTrue(query.containsSubquery("param1"));
        assertFalse(query.containsSubquery("param2"));
    }
}
