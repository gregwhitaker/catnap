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

package com.github.gregwhitaker.catnap.core.query.processor;

import com.github.gregwhitaker.catnap.core.exception.ViewRenderException;
import com.github.gregwhitaker.catnap.core.util.ClassUtil;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests verifying correct sorting of field declarations.
 */
public class FieldDeclarationComparatorTest {

    static abstract class MockBaseModel {
        String property1 = "value1";

        public String getProperty1() {
            return property1;
        }
    }

    static class MockModel extends MockBaseModel {
        String property2 = "value2";

        public String getProperty2() {
            return property2;
        }
    }

    static class MockProperty implements Property<MockModel> {

        @Override
        public String getName() {
            return "unknownProperty";
        }

        @Override
        public String getRenderName() {
            return getName();
        }

        @Override
        public Object getValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Method getReadMethod() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isPrimitive() {
            throw new UnsupportedOperationException();
        }
    }

    private FieldDeclarationComparator<MockModel> comparator = new FieldDeclarationComparator<>(MockModel.class);
    private Property<MockModel> property1;
    private Property<MockModel> property2;

    @Before
    public void setup() {
        MockModel model = new MockModel();
        property1 = new CatnapProperty<>(model, ClassUtil.getReadableProperty("property1", MockModel.class));
        property2 = new CatnapProperty<>(model, ClassUtil.getReadableProperty("property2", MockModel.class));
    }

    @Test
    public void compareLessThan() {
        assertEquals(-1, comparator.compare(property1, property2));
    }

    @Test
    public void compareEqualTo() {
        assertEquals(0, comparator.compare(property1, property1));
    }

    @Test
    public void compareGreaterThan() {
        assertEquals(1, comparator.compare(property2, property1));
    }

    @Test(expected = ViewRenderException.class)
    public void unknownProperty1ThrowsException() {
        comparator.compare(new MockProperty(), property2);
    }

    @Test(expected = ViewRenderException.class)
    public void unknownProperty2ThrowsException() {
        comparator.compare(property1, new MockProperty());
    }
}
