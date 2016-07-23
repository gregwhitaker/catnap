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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DefaultListBackedModelTest {

    @Test
    public void addSingleValueToModel() {
        DefaultListBackedModel model = new DefaultListBackedModel();
        model.addValue("test");

        assertEquals(1, model.getResult().size());
        assertEquals("test", model.getResult().get(0));
    }

    @Test
    public void createChildMap() {
        DefaultListBackedModel model = new DefaultListBackedModel();
        model.createChildMap();

        assertEquals(1, model.getResult().size());
        assertTrue(model.getResult().get(0) instanceof LinkedHashMap);
    }

    @Test
    public void modelWithoutElementsIsEmpty() {
        assertTrue(new DefaultListBackedModel().isEmpty());
    }

    @Test
    public void modelWithElementsIsNotEmpty() {
        DefaultListBackedModel model = new DefaultListBackedModel();
        model.addValue("test");

        assertFalse(model.isEmpty());
    }
}
