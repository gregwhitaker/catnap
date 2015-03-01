package com.catnap.core.query.parser;

import com.catnap.core.query.QuerySyntax;
import com.catnap.core.query.model.Expression;

/**
 * Created by Woody on 1/11/14.
 */
public interface ExpressionParser<T extends Expression>
{
    public T parse(String expression);

    public QuerySyntax getSyntax();
}
