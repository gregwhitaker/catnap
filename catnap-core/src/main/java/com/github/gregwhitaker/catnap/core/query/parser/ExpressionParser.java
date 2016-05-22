package com.github.gregwhitaker.catnap.core.query.parser;

import com.github.gregwhitaker.catnap.core.query.QuerySyntax;
import com.github.gregwhitaker.catnap.core.query.model.Expression;

/**
 *
 * @param <T>
 */
public interface ExpressionParser<T extends Expression> {

    /**
     *
     * @param expression
     * @return
     */
    T parse(String expression);

    /**
     *
     * @return
     */
    QuerySyntax getSyntax();
}
