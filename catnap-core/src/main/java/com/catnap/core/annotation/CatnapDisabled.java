package com.catnap.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that causes responses returned from the annotated method to not be
 * evaluated by Catnap.
 * @author gwhit7
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@CatnapAnnotation
public @interface CatnapDisabled
{

}
