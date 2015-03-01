package com.catnap.demo.springmvc.controller;

import com.catnap.demo.core.exception.ProductNotFoundException;
import com.catnap.demo.core.model.Error;
import com.catnap.demo.core.model.*;
import com.catnap.springmvc.annotation.CatnapResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Woody on 12/29/13.
 */
@ControllerAdvice
public class ErrorHandler
{
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @CatnapResponseBody
    public Error handleProductNotFoundException(ProductNotFoundException e)
    {
        return new Error(e.getMessage());
    }
}
