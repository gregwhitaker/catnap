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

package com.github.gregwhitaker.catnap.springmvc.view;

import com.github.gregwhitaker.catnap.core.view.CatnapView;

/**
 * Interface that links a {@link CatnapView} and a Spring {@link org.springframework.web.servlet.View}
 * implementation.
 */
public interface WrappingView<T extends CatnapView>
{
    /**
     * @return the underlying {@link CatnapView} implementation wrapped by this view.
     */
    T getWrappedView();

    /**
     * @return the character encoding returned by the underlying {@link CatnapView}
     * implementation wrapped by this view. (Ex. "UTF-8" or "UTF-16")
     */
    String getCharacterEncoding();

    /**
     * @return name of the object in the {@link org.springframework.ui.Model} to render with Catnap.
     */
    String getModelName();
}
