package com.github.gregwhitaker.catnap.core.exception;

/**
 *
 */
public class CatnapException extends RuntimeException {

    /**
     *
     * @param message
     */
    public CatnapException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param throwable
     */
    public CatnapException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
