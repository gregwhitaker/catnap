package com.catnap.core.query.parser;

import com.catnap.core.exception.QuerySyntaxException;
import com.catnap.core.query.QuerySyntax;
import com.catnap.core.query.model.Query;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public interface Parser<T extends Query>
{
    /**
     *
     * @param expression
     * @return
     * @throws QuerySyntaxException
     */
    public T parse(String expression) throws QuerySyntaxException;

    /**
     *
     * @return
     */
    public QuerySyntax getSyntax();
}
