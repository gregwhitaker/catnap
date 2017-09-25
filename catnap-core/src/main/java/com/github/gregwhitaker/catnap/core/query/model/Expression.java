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

import com.github.gregwhitaker.catnap.core.query.processor.Property;

/**
 * Defines a generic expression to be evaluated by Catnap. (eg. field!=value, field=value, etc.)
 *
 * @param <T> type of the value of the property associated with this expression
 */
public interface Expression<T> {

    /**
     * Check to see if the supplied {@link Property} matches this expression.
     *
     * @param propertyToCompare {@link Property} to evaluate for match
     * @return <code>true</code> if the property matches the expression; otherwise <code>false</code>
     */
    boolean evaluate(Property propertyToCompare);

    /**
     * Name of the field associated with this expression.
     *
     * @return
     */
    String getField();

    /**
     * The type of operator used by this expression (eg. =, !=, etc.)
     *
     * @return the operator used by this expression
     */
    Operator getOperator();

    /**
     * The value of the expression.
     *
     * @return value of the expression
     */
    T getValue();
}
