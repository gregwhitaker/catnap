package com.catnap.springmvc.annotation;

import com.catnap.core.annotation.CatnapAnnotation;
import com.catnap.springmvc.config.DelegatingCatnapWebMvcConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@CatnapAnnotation
@Import({DelegatingCatnapWebMvcConfiguration.class})
public @interface EnableCatnapWebMvc
{

}
