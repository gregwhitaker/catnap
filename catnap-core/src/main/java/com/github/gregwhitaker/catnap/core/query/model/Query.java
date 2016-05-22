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

package com.github.gregwhitaker.catnap.core.query.model;

import java.util.List;
import java.util.Map;

/**
 *
 * @param <T>
 */
public interface Query<T extends Query> {

    /**
     *
     * @param name
     */
    void addParameter(String name);

    /**
     *
     * @param name
     * @param subquery
     */
    void addParameter(String name, Query subquery);

    /**
     *
     * @param name
     * @param expression
     */
    void addParameter(String name, Expression expression);

    /**
     *
     * @param name
     * @param subquery
     * @param expression
     */
    void addParameter(String name, Query subquery, Expression expression);

    /**
     *
     * @return
     */
    Map<String, T> getParameters();

    /**
     *
     * @return
     */
    List<String> getParameterNames();

    /**
     *
     * @return
     */
    int getParameterCount();

    /**
     *
     * @param name
     * @return
     */
    boolean containsParameter(String name);

    /**
     *
     * @param name
     * @return
     */
    boolean containsExpression(String name);

    /**
     *
     * @param name
     * @return
     */
    boolean containsSubquery(String name);

    /**
     *
     * @param name
     * @return
     */
    Query getSubquery(String name);

    /**
     *
     * @param name
     * @return
     */
    Expression getExpression(String name);
}
