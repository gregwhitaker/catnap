package com.catnap.core.query.processor;

import com.catnap.core.query.model.Query;
import com.catnap.core.query.model.SimpleQuery;
import com.catnap.core.query.processor.QueryProcessor;
import com.catnap.core.query.processor.SimpleQueryProcessor;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public class QueryProcessorFactory
{
    /**
     *
     * @param query
     * @param instanceClazz
     * @return
     */
    public static QueryProcessor createQueryProcessor(Query query, Class<?> instanceClazz)
    {
        if(query == null)
        {
            return new NullQueryProcessor();
        }

        if(query instanceof SimpleQuery)
        {
            return new SimpleQueryProcessor();
        }

        throw new UnsupportedOperationException(String.format("Query type [%s] is not supported", query.getClass().getName()));
    }
}
