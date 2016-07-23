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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.gregwhitaker.catnap.core.query.model.Query;
import com.github.gregwhitaker.catnap.core.util.ClassUtil;
import org.junit.Test;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class QueryProcessorTest {

    static class MockQueryProcessor extends QueryProcessor {

        private List<String> processOrder = new ArrayList<>(3);

        @Override
        public boolean supports(Class<? extends Query<?>> query) {
            return true;
        }

        @Override
        protected <T> void preProcess(Query query, T instance, Class<T> instanceClazz) {
            super.preProcess(query, instance, instanceClazz);
            processOrder.add("preProcess");
        }

        @Override
        protected <T> List<Property<T>> processInternal(Query query, T instance, Class<T> instanceClazz) {
            processOrder.add("processInternal");
            return null;
        }

        @Override
        protected <T> void postProcess(List<Property<T>> properties, Query query, T instance, Class<T> instanceClazz) {
            super.postProcess(properties, query, instance, instanceClazz);
            processOrder.add("postProcess");
        }

        @Override
        protected boolean ignoreProperty(PropertyDescriptor descriptor) {
            return super.ignoreProperty(descriptor);
        }

        public List<String> getProcessOrder() {
            return processOrder;
        }
    }

    static class MockModel {
        private String property1 = "value1";
        private String property2 = "value2";    //Ignored by @CatnapIgnore
        private String property3 = "value3";    //Ignored by @JsonIgnore
        private String property4 = "value4";    //Ignored by "No Getter"

        public String getProperty1() {
            return property1;
        }

        @JsonIgnore
        public String getProperty3() {
            return property3;
        }
    }

    @Test
    public void validateProcessOrder() {
        MockQueryProcessor processor = new MockQueryProcessor();
        processor.process(null, new MockModel(), MockModel.class);

        assertEquals("preProcess", processor.getProcessOrder().get(0));
        assertEquals("processInternal", processor.getProcessOrder().get(1));
        assertEquals("postProcess", processor.getProcessOrder().get(2));
    }

    @Test
    public void doNotIgnoreMethodsWithoutIgnoreAnnotations() {
        PropertyDescriptor descriptor = ClassUtil.getReadableProperty("property1", MockModel.class);

        MockQueryProcessor processor = new MockQueryProcessor();
        assertFalse(processor.ignoreProperty(descriptor));
    }

    @Test
    public void ignoreMethodsAnnotatedWithCatnapIgnore() {
        PropertyDescriptor descriptor = ClassUtil.getReadableProperty("property2", MockModel.class);

        MockQueryProcessor processor = new MockQueryProcessor();
        assertTrue(processor.ignoreProperty(descriptor));
    }

    @Test
    public void ignoreMethodsAnnotatedWithJsonIgnore() {
        PropertyDescriptor descriptor = ClassUtil.getReadableProperty("property3", MockModel.class);

        MockQueryProcessor processor = new MockQueryProcessor();
        assertTrue(processor.ignoreProperty(descriptor));
    }

    @Test
    public void ignoreMethodsWithoutGetters() {
        PropertyDescriptor descriptor = ClassUtil.getReadableProperty("property4", MockModel.class);

        MockQueryProcessor processor = new MockQueryProcessor();
        assertTrue(processor.ignoreProperty(descriptor));
    }

    @Test
    public void ignoreMethodsWithNullDescriptors() {
        MockQueryProcessor processor = new MockQueryProcessor();
        assertTrue(processor.ignoreProperty(null));
    }
}
