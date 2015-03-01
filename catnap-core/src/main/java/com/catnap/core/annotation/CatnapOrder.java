package com.catnap.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@CatnapAnnotation
public @interface CatnapOrder
{
    /**
     * Order in which properties of annotated object are to be serialized in.
     */
    public String[] value() default { };

    /**
     * Property that defines what to do regarding ordering of properties
     * not explicitly included in annotation instance. If set to true,
     * they will be alphabetically ordered; if false, order is
     * undefined (default setting)
     */
    public boolean alphabetic() default false;
}
