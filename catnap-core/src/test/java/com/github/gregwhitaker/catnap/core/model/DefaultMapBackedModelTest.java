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

package com.github.gregwhitaker.catnap.core.model;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class DefaultMapBackedModelTest {

    @Test
    public void addSingleValueToModel() {
        DefaultMapBackedModel model = new DefaultMapBackedModel();
        model.addValue("test", "value");

        assertEquals(1, model.getResult().size());
        assertEquals("value", model.getResult().get("test"));
    }

    @Test
    public void createChildMap() {
        DefaultMapBackedModel model = new DefaultMapBackedModel();
        model.createChildMap("childMap");

        assertEquals(1, model.getResult().size());
        assertTrue(model.getResult().get("childMap") instanceof LinkedHashMap);
    }

    @Test
    public void createChildList() {
        DefaultMapBackedModel model = new DefaultMapBackedModel();
        model.createChildList("childList");

        assertEquals(1, model.getResult().size());
        assertTrue(model.getResult().get("childList") instanceof LinkedList);
    }

    @Test
    public void modelWithoutElementsIsEmpty() {
        assertTrue(new DefaultMapBackedModel().isEmpty());
    }

    @Test
    public void modelWithElementsIsNotEmpty() {
        DefaultMapBackedModel model = new DefaultMapBackedModel();
        model.addValue("test", "value");

        assertFalse(model.isEmpty());
    }
}
