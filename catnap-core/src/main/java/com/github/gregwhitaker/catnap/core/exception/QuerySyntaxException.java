package com.github.gregwhitaker.catnap.core.exception;

/**
 *
 */
public class QuerySyntaxException extends CatnapException {

    /**
     *
     * @param message
     */
    public QuerySyntaxException(String message) {
        super(message);
    }

    /**
     *
     * @param expression
     * @param message
     */
    public QuerySyntaxException(String expression, String message) {
        super(String.format("Unable to parse expression [%s] - %s", expression, message));
    }
}
