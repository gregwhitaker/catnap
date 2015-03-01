package com.catnap.core.query.builder;

import com.catnap.core.query.model.Query;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public interface QueryBuilder
{
    public Query build(HttpServletRequest request);

    public String getQueryParameter();

    public void setQueryParameter(String name);
}
