package com.github.gregwhitaker.catnap.core.query.builder;

import com.github.gregwhitaker.catnap.core.query.model.Query;
import com.github.gregwhitaker.catnap.core.query.model.SimpleQuery;
import com.github.gregwhitaker.catnap.core.query.parser.Parser;
import com.github.gregwhitaker.catnap.core.query.parser.SimpleParser;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public class SimpleQueryBuilder implements QueryBuilder {
    public static final String DEFAULT_QUERY_PARAMETER = "fields";

    private Parser<SimpleQuery> parser = new SimpleParser();
    private String queryParameter = DEFAULT_QUERY_PARAMETER;

    @Override
    public Query<SimpleQuery> build(HttpServletRequest request) {
        String expression = request.getParameter(getQueryParameter());

        return parser.parse(expression);
    }

    @Override
    public String getQueryParameter() {
        return queryParameter;
    }

    @Override
    public void setQueryParameter(String name) {
        this.queryParameter = name;
    }
}
