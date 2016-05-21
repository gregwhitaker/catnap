package com.github.gregwhitaker.catnap.core.exception;

/**
 *
 */
public class ViewRenderException extends CatnapException {

    /**
     *
     * @param message
     */
    public ViewRenderException(String message) {
        super("Unable to render view - [" + message + "]");
    }

    /**
     *
     * @param message
     * @param throwable
     */
    public ViewRenderException(String message, Throwable throwable) {
        super("Unable to render view - [" + message + "]", throwable);
    }
}
