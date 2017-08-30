/*
 * Copyright 2016 Greg Whitaker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.gregwhitaker.catnap.springmvc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gregwhitaker.catnap.core.view.JsonCatnapView;
import com.github.gregwhitaker.catnap.core.view.JsonpCatnapView;
import com.github.gregwhitaker.catnap.springmvc.interceptor.CatnapDisabledHandlerInterceptor;
import com.github.gregwhitaker.catnap.springmvc.interceptor.CatnapResponseBodyHandlerInterceptor;
import com.github.gregwhitaker.catnap.springmvc.messageconverters.CatnapJsonMessageConverter;
import com.github.gregwhitaker.catnap.springmvc.messageconverters.CatnapJsonpMessageConverter;
import com.github.gregwhitaker.catnap.springmvc.view.CatnapViewResolver;
import com.github.gregwhitaker.catnap.springmvc.view.CatnapWrappingView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DelegatingCatnapWebMvcConfiguration extends DelegatingWebMvcConfiguration {

    @Autowired(required = false)
    private ObjectMapper mapper;

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new CatnapJsonMessageConverter());
        converters.add(new CatnapJsonpMessageConverter());
        addDefaultHttpMessageConverters(converters);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CatnapDisabledHandlerInterceptor());
        registry.addInterceptor(new CatnapResponseBodyHandlerInterceptor());

        super.addInterceptors(registry);
    }

    @Override
    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
        configurer.favorPathExtension(true);
        configurer.ignoreAcceptHeader(false);
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
        configurer.mediaType("jsonp", new MediaType("application", "x-javascript"));

        super.configureContentNegotiation(configurer);
    }

    @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {
        List<View> defaultViews = new ArrayList<>(2);
        defaultViews.add(jsonCatnapSpringView());
        defaultViews.add(jsonpCatnapSpringView());

        List<CatnapWrappingView> catnapViews = new ArrayList<>(2);
        catnapViews.add(jsonCatnapSpringView());
        catnapViews.add(jsonpCatnapSpringView());

        CatnapViewResolver catnapViewResolver = new CatnapViewResolver();
        catnapViewResolver.setViews(catnapViews);

        List<ViewResolver> viewResolvers = new ArrayList<>(1);
        viewResolvers.add(catnapViewResolver);

        ContentNegotiatingViewResolver cnvr = new ContentNegotiatingViewResolver();
        cnvr.setContentNegotiationManager(mvcContentNegotiationManager());
        cnvr.setOrder(1);
        cnvr.setDefaultViews(defaultViews);
        cnvr.setViewResolvers(viewResolvers);

        return cnvr;
    }

    @Bean
    public CatnapWrappingView jsonCatnapSpringView() {
        if (mapper != null) {
            return new CatnapWrappingView(new JsonCatnapView.Builder().withObjectMapper(mapper).build());
        } else {
            return new CatnapWrappingView(new JsonCatnapView.Builder().build());
        }
    }

    @Bean
    public CatnapWrappingView jsonpCatnapSpringView() {
        if (mapper != null) {
            return new CatnapWrappingView(new JsonpCatnapView.Builder().withObjectMapper(mapper).build());
        } else {
            return new CatnapWrappingView(new JsonpCatnapView.Builder().build());
        }
    }
}
