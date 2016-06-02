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

package com.github.gregwhitaker.catnap.core.context;

import com.github.gregwhitaker.catnap.core.query.builder.QueryBuilder;
import com.github.gregwhitaker.catnap.core.query.model.Query;
import com.github.gregwhitaker.catnap.core.util.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class CatnapContext {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final HttpStatus httpStatus;
    private final QueryBuilder queryBuilder;
    private final Query query;
    private final boolean catnapDisabled;

    /**
     * @param request
     * @param response
     * @param queryBuilder
     */
    public CatnapContext(final HttpServletRequest request, final HttpServletResponse response, final QueryBuilder queryBuilder) {
        this(request, response, queryBuilder, HttpStatus.OK);
    }

    /**
     * @param request
     * @param response
     * @param queryBuilder
     * @param httpStatus
     */
    public CatnapContext(final HttpServletRequest request, final HttpServletResponse response, final QueryBuilder queryBuilder, HttpStatus httpStatus) {
        this.request = request;
        this.response = response;
        this.queryBuilder = queryBuilder;
        this.query = queryBuilder.build(request);
        this.httpStatus = httpStatus;
        this.catnapDisabled = RequestUtil.isCatnapDisabled(request);
    }

    /**
     * @return
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * @return
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * @return
     */
    public Query getQuery() {
        return query;
    }

    /**
     * @return
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * @return
     */
    public boolean isCatnapDisabled() {
        return catnapDisabled;
    }
}
