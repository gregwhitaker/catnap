package com.catnap.core.exception;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public class ViewRenderException extends CatnapException
{
    public ViewRenderException(String message)
    {
        super("Unable to render view - [" + message + "]");
    }

    public ViewRenderException(String message, Throwable throwable)
    {
        super("Unable to render view - [" + message + "]", throwable);
    }
}
