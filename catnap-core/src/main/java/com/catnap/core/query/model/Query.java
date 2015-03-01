package com.catnap.core.query.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public interface Query<T extends Query>
{
    /**
     *
     * @param name
     */
    public void addParameter(String name);

    /**
     *
     * @param name
     * @param subquery
     */
    public void addParameter(String name, Query subquery);

    /**
     *
     * @param name
     * @param expression
     */
    public void addParameter(String name, Expression expression);

    /**
     *
     * @param name
     * @param subquery
     * @param expression
     */
    public void addParameter(String name, Query subquery, Expression expression);

    /**
     *
     * @return
     */
    public Map<String, T> getParameters();

    /**
     *
     * @return
     */
    public List<String> getParameterNames();

    /**
     *
     * @return
     */
    public int getParameterCount();

    /**
     *
     * @param name
     * @return
     */
    public boolean containsParameter(String name);

    /**
     *
     * @param name
     * @return
     */
    public boolean containsExpression(String name);

    /**
     *
     * @param name
     * @return
     */
    public boolean containsSubquery(String name);

    /**
     *
     * @param name
     * @return
     */
    public Query getSubquery(String name);

    /**
     *
     * @param name
     * @return
     */
    public Expression getExpression(String name);
}
