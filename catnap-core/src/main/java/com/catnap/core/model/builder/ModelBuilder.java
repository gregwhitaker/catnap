package com.catnap.core.model.builder;

import com.catnap.core.context.CatnapContext;
import com.catnap.core.model.Model;
import com.catnap.core.query.model.Query;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public interface ModelBuilder
{
    public Model<?> build(Object instance, CatnapContext context);
}
