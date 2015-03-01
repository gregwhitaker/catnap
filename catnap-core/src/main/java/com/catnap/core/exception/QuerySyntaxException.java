package com.catnap.core.exception;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public class QuerySyntaxException extends CatnapException
{
    public QuerySyntaxException(String message)
    {
        super(message);
    }

    public QuerySyntaxException(String expression, String message)
    {
        super(String.format("Unable to parse expression [%s] - %s", expression, message));
    }
}
