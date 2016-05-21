package com.github.gregwhitaker.catnap.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that is added to "getter" methods that causes the property to be
 * omitted when serializing the response.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@CatnapAnnotation
public @interface CatnapIgnore {

    /**
     * Optional argument that defines whether this annotation is active
     * or not. The only use for value 'false' if for overriding purposes
     * (which is not needed often); most likely it is needed for use
     * with "mix-in annotations" (aka "annotation overrides").
     * For most cases, however, default value of "true" is just fine
     * and should be omitted.
     */
    boolean value() default true;
}
