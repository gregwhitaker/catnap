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

import com.github.gregwhitaker.catnap.core.query.model.CatnapQuery;
import com.github.gregwhitaker.catnap.core.query.model.Expression;
import com.github.gregwhitaker.catnap.core.query.model.Query;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class QueryProcessorFactoryTest {

    static class MockQuery implements Query {

        @Override
        public void addParameter(String name) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addParameter(String name, Query subquery) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addParameter(String name, Expression expression) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addParameter(String name, Query subquery, Expression expression) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Map getParameters() {
            return null;
        }

        @Override
        public List<String> getParameterNames() {
            return null;
        }

        @Override
        public int getParameterCount() {
            return 0;
        }

        @Override
        public boolean containsParameter(String name) {
            return false;
        }

        @Override
        public boolean containsExpression(String name) {
            return false;
        }

        @Override
        public boolean containsSubquery(String name) {
            return false;
        }

        @Override
        public Query getSubquery(String name) {
            return null;
        }

        @Override
        public Expression getExpression(String name) {
            return null;
        }
    }

    @Test
    public void createNullQueryProcessor() {
        QueryProcessor processor = QueryProcessorFactory.createQueryProcessor(null, Object.class);
        assertTrue(processor instanceof NullQueryProcessor);
    }

    @Test
    public void createSimpleQueryProcessor() {
        QueryProcessor processor = QueryProcessorFactory.createQueryProcessor(new CatnapQuery(), Object.class);
        assertTrue(processor instanceof CatnapQueryProcessor);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void unknownQueryTypeThrowsException() {
        QueryProcessorFactory.createQueryProcessor(new MockQuery(), Object.class);
    }
}
