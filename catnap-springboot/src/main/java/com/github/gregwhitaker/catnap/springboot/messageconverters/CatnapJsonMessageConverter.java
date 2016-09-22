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

package com.github.gregwhitaker.catnap.springboot.messageconverters;

import com.github.gregwhitaker.catnap.core.view.JsonCatnapView;

/**
 * An {@link org.springframework.http.converter.AbstractHttpMessageConverter} implementation that renders JSON
 * responses with Catnap.
 */
public class CatnapJsonMessageConverter extends CatnapMessageConverter<JsonCatnapView> {

    /**
     * Initializes this instance of {@link CatnapJsonMessageConverter} with the default {@link JsonCatnapView}
     * configured for rendering responses.
     */
    public CatnapJsonMessageConverter() {
        super(new JsonCatnapView.Builder().build());
    }

    /**
     * Initializes this instance of {@link CatnapJsonMessageConverter}.
     *
     * @param view the {@link JsonCatnapView} to use when rendering responses within this message converter
     */
    public CatnapJsonMessageConverter(JsonCatnapView view) {
        super(view);
    }
}
