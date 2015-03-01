package com.catnap.core.context;

import com.catnap.core.query.builder.QueryBuilder;
import com.catnap.core.query.model.Query;
import com.catnap.core.util.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public class CatnapContext
{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final HttpStatus httpStatus;
    private final QueryBuilder queryBuilder;
    private final Query query;
    private final boolean catnapDisabled;

    public CatnapContext(final HttpServletRequest request, final HttpServletResponse response, final QueryBuilder queryBuilder)
    {
        this(request, response, queryBuilder, HttpStatus.OK);
    }

    public CatnapContext(final HttpServletRequest request, final HttpServletResponse response, final QueryBuilder queryBuilder, HttpStatus httpStatus)
    {
        this.request = request;
        this.response = response;
        this.queryBuilder = queryBuilder;
        this.query = queryBuilder.build(request);
        this.httpStatus = httpStatus;
        this.catnapDisabled = RequestUtil.isCatnapDisabled(request);
    }

    public HttpServletRequest getRequest()
    {
        return request;
    }

    public HttpServletResponse getResponse()
    {
        return response;
    }

    public Query getQuery()
    {
        return query;
    }

    public HttpStatus getHttpStatus()
    {
        return httpStatus;
    }

    public boolean isCatnapDisabled()
    {
        return catnapDisabled;
    }
}
