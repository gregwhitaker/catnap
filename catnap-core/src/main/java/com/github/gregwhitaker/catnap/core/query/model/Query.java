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
