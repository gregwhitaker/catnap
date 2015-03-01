package com.catnap.core.query.model;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public class SimpleQuery implements Query<SimpleQuery>
{
    protected Map<String, SimpleQuery> parameters = new LinkedHashMap<String, SimpleQuery>();
    protected Map<String, Expression> expressions = new HashMap<String, Expression>();

    @Override
    public void addParameter(String name)
    {
        parameters.put(name, null);
    }

    @Override
    public void addParameter(String name, Query subquery)
    {
        parameters.put(name, (SimpleQuery) subquery);
    }

    @Override
    public void addParameter(String name, Expression expression)
    {
        parameters.put(name, null);
        expressions.put(name, expression);
    }

    @Override
    public void addParameter(String name, Query subquery, Expression expression)
    {
        parameters.put(name, (SimpleQuery) subquery);
        expressions.put(name, expression);
    }

    @Override
    public Map<String, SimpleQuery> getParameters()
    {
        return parameters;
    }

    @Override
    public List<String> getParameterNames()
    {
        List<String> paramNames = new ArrayList<String>(parameters.keySet().size());

        Iterator<String> keys = parameters.keySet().iterator();

        while(keys.hasNext())
        {
            paramNames.add(keys.next());
        }

        return paramNames;
    }

    @Override
    public int getParameterCount()
    {
        return parameters.size();
    }

    @Override
    public boolean containsParameter(String name)
    {
        return parameters.containsKey(name);
    }

    @Override
    public boolean containsExpression(String name)
    {
        return expressions.containsKey(name);
    }

    @Override
    public boolean containsSubquery(String name)
    {
        return (getSubquery(name) != null) ? true : false;
    }

    @Override
    public Query getSubquery(String name)
    {
        return parameters.get(name);
    }

    @Override
    public Expression getExpression(String name)
    {
        return expressions.get(name);
    }
}
