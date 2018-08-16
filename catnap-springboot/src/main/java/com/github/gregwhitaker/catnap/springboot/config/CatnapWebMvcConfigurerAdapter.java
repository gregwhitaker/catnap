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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gregwhitaker.catnap.core.view.JsonCatnapView;
import com.github.gregwhitaker.catnap.core.view.JsonpCatnapView;
import com.github.gregwhitaker.catnap.springboot.messageconverters.CatnapJsonMessageConverter;
import com.github.gregwhitaker.catnap.springboot.messageconverters.CatnapJsonpMessageConverter;

@Configuration
public class CatnapWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    /**
     * @see See "Customize the Jackson ObjectMapper" section in Spring Boot documentation
     */
    @Autowired
    private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
        configurer.mediaType("jsonp", new MediaType("application", "x-javascript"));
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        final ObjectMapper objectMapper = this.jackson2ObjectMapperBuilder.build();
        converters.add(0, new CatnapJsonMessageConverter(new JsonCatnapView.Builder()
                .withObjectMapper(objectMapper).build()));
        converters.add(1, new CatnapJsonpMessageConverter(new JsonpCatnapView.Builder()
                .withObjectMapper(objectMapper).build()));
    }
}
