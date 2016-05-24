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

package com.github.gregwhitaker.catnap.springboot.config;

import com.github.gregwhitaker.catnap.core.view.JsonCatnapView;
import com.github.gregwhitaker.catnap.core.view.JsonpCatnapView;
import com.github.gregwhitaker.catnap.core.view.XmlCatnapView;
import com.github.gregwhitaker.catnap.springboot.interceptor.CatnapDisabledHandlerInterceptor;
import com.github.gregwhitaker.catnap.springboot.interceptor.CatnapResponseBodyHandlerInterceptor;
import com.github.gregwhitaker.catnap.springboot.view.CatnapViewResolver;
import com.github.gregwhitaker.catnap.springboot.view.CatnapWrappingView;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import java.util.ArrayList;
import java.util.List;

@Configuration
//@EnableConfigurationProperties({CatnapProperties.class})
public class CatnapConfiguration extends DelegatingWebMvcConfiguration {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CatnapDisabledHandlerInterceptor());
        registry.addInterceptor(new CatnapResponseBodyHandlerInterceptor());

        super.addInterceptors(registry);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
        configurer.favorPathExtension(true);
        configurer.ignoreAcceptHeader(false);
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
        configurer.mediaType("jsonp", new MediaType("application", "x-javascript"));
        configurer.mediaType("xml", MediaType.APPLICATION_XML);

        super.configureContentNegotiation(configurer);
    }

    @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {
        List<View> defaultViews = new ArrayList<>(2);
        defaultViews.add(jsonCatnapSpringView());
        defaultViews.add(jsonpCatnapSpringView());
        defaultViews.add(xmlCatnapSpringView());

        List<CatnapWrappingView> catnapViews = new ArrayList<>(2);
        catnapViews.add(jsonCatnapSpringView());
        catnapViews.add(jsonpCatnapSpringView());
        catnapViews.add(xmlCatnapSpringView());

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
        return new CatnapWrappingView(new JsonCatnapView.Builder().build());
    }

    @Bean
    public CatnapWrappingView jsonpCatnapSpringView() {
        return new CatnapWrappingView(new JsonpCatnapView.Builder().build());
    }

    @Bean
    public CatnapWrappingView xmlCatnapSpringView() {
        return new CatnapWrappingView(new XmlCatnapView.Builder().build());
    }
}
