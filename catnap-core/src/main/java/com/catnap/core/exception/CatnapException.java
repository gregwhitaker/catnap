package com.catnap.core.exception;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public class CatnapException extends RuntimeException
{
    public CatnapException(String message)
    {
        super(message);
    }

    public CatnapException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
