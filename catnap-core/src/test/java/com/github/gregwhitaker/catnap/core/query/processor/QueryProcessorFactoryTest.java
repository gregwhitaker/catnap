package com.github.gregwhitaker.catnap.core.query.processor;

import com.github.gregwhitaker.catnap.core.query.model.Expression;
import com.github.gregwhitaker.catnap.core.query.model.Query;
import com.github.gregwhitaker.catnap.core.query.model.SimpleQuery;
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
        QueryProcessor processor = QueryProcessorFactory.createQueryProcessor(new SimpleQuery(), Object.class);
        assertTrue(processor instanceof SimpleQueryProcessor);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void unknownQueryTypeThrowsException() {
        QueryProcessorFactory.createQueryProcessor(new MockQuery(), Object.class);
    }
}
