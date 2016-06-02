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

package com.github.gregwhitaker.catnap.core.query.builder;

import com.github.gregwhitaker.catnap.core.query.model.CatnapQuery;
import com.github.gregwhitaker.catnap.core.query.model.Query;
import com.github.gregwhitaker.catnap.core.query.parser.CatnapParser;
import com.github.gregwhitaker.catnap.core.query.parser.Parser;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public class CatnapQueryBuilder implements QueryBuilder {
    public static final String DEFAULT_QUERY_PARAMETER = "fields";

    private Parser<CatnapQuery> parser = new CatnapParser();
    private String queryParameter = DEFAULT_QUERY_PARAMETER;

    @Override
    public Query<CatnapQuery> build(HttpServletRequest request) {
        String expression = request.getParameter(getQueryParameter());

        return parser.parse(expression);
    }

    @Override
    public String getQueryParameter() {
        return queryParameter;
    }

    @Override
    public void setQueryParameter(String name) {
        this.queryParameter = name;
    }
}
