package com.catnap.core.query.builder;

import com.catnap.core.query.model.Query;
import com.catnap.core.query.model.SimpleQuery;
import com.catnap.core.query.parser.Parser;
import com.catnap.core.query.parser.SimpleParser;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public class SimpleQueryBuilder implements QueryBuilder
{
    public static final String DEFAULT_QUERY_PARAMETER = "fields";

    private Parser<SimpleQuery> parser = new SimpleParser();
    private String queryParameter = DEFAULT_QUERY_PARAMETER;

    @Override
    public Query build(HttpServletRequest request)
    {
        String expression = request.getParameter(getQueryParameter());

        return parser.parse(expression);
    }

    @Override
    public String getQueryParameter()
    {
        return queryParameter;
    }

    @Override
    public void setQueryParameter(String name)
    {
        this.queryParameter = name;
    }
}
