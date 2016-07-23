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

package com.github.gregwhitaker.catnap.core.query.model;

import org.junit.Test;

import static org.junit.Assert.*;

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
