package com.github.gregwhitaker.catnap.core.query.builder;

import com.github.gregwhitaker.catnap.core.query.model.Query;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public interface QueryBuilder {

    /**
     *
     * @param request
     * @return
     */
    Query build(HttpServletRequest request);

    /**
     *
     * @return
     */
    String getQueryParameter();

    /**
     *
     * @param name
     */
    void setQueryParameter(String name);
}
