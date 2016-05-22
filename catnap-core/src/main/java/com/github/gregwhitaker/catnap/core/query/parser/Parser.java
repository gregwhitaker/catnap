package com.github.gregwhitaker.catnap.core.query.parser;

import com.github.gregwhitaker.catnap.core.exception.QuerySyntaxException;
import com.github.gregwhitaker.catnap.core.query.QuerySyntax;
import com.github.gregwhitaker.catnap.core.query.model.Query;

/**
 *
 * @param <T>
 */
public interface Parser<T extends Query>
{
    /**
     *
     * @param expression
     * @return
     * @throws QuerySyntaxException
     */
    T parse(String expression) throws QuerySyntaxException;

    /**
     *
     * @return
     */
    QuerySyntax getSyntax();
}
