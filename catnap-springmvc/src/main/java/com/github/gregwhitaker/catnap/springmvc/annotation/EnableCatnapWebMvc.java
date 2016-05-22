package com.github.gregwhitaker.catnap.springmvc.annotation;

import com.github.gregwhitaker.catnap.core.annotation.CatnapAnnotation;
import com.github.gregwhitaker.catnap.springmvc.config.DelegatingCatnapWebMvcConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@CatnapAnnotation
@Import({DelegatingCatnapWebMvcConfiguration.class})
public @interface EnableCatnapWebMvc {

}
