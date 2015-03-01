package com.catnap.demo.core.exception;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public class ProductNotFoundException extends RuntimeException
{
    public ProductNotFoundException(String id)
    {
        super("The product [" + id + "] cannot be found.");
    }
}
