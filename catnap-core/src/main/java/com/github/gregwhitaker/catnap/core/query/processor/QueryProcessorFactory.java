package com.github.gregwhitaker.catnap.core.query.processor;

import com.github.gregwhitaker.catnap.core.query.model.Query;
import com.github.gregwhitaker.catnap.core.query.model.SimpleQuery;

/**
 *
 */
public class QueryProcessorFactory {
    /**
     *
     * @param query
     * @param instanceClazz
     * @return
     */
    public static QueryProcessor createQueryProcessor(Query query, Class<?> instanceClazz) {
        if(query == null) {
            return new NullQueryProcessor();
        }

        if(query instanceof SimpleQuery) {
            return new SimpleQueryProcessor();
        }

        throw new UnsupportedOperationException(String.format("Query type [%s] is not supported", query.getClass().getName()));
    }
}
