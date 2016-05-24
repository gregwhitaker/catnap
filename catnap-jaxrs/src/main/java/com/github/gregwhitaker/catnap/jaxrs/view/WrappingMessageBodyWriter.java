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

package com.github.gregwhitaker.catnap.jaxrs.view;

import com.github.gregwhitaker.catnap.core.view.CatnapView;

/**
 * Interface that all Catnap JAX-RS MessageBodyWriters must implement.
 */
public interface WrappingMessageBodyWriter<T extends CatnapView> {

    /**
     * @return the underlying {@link CatnapView} implementation wrapped by this MessageBodyWriter.
     */
    public T getWrappedView();

    /**
     * @return the content-type returned by the underlying {@link CatnapView} implementation
     * wrapped by this MessageBodyWriter. (Ex. "application/json" or "application/xml")
     */
    public String getContentType();

    /**
     * @return the character encoding returned by the underlying {@link CatnapView}
     * implementation wrapped by this MessageBodyWriter. (Ex. "UTF-8" or "UTF-16")
     */
    public String getCharacterEncoding();
}
