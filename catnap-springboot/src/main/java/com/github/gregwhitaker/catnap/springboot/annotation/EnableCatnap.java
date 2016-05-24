package com.github.gregwhitaker.catnap.springboot.annotation;

import com.github.gregwhitaker.catnap.core.annotation.CatnapAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@CatnapAnnotation
public @interface EnableCatnap {

}
