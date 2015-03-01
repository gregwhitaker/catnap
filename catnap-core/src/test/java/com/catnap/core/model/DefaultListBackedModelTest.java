package com.catnap.core.model;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public class DefaultListBackedModelTest
{
    @Test
    public void addSingleValueToModel()
    {
        DefaultListBackedModel model = new DefaultListBackedModel();
        model.addValue("test");

        assertEquals(1, model.getResult().size());
        assertEquals("test", model.getResult().get(0));
    }

    @Test
    public void createChildMap()
    {
        DefaultListBackedModel model = new DefaultListBackedModel();
        model.createChildMap();

        assertEquals(1, model.getResult().size());
        assertTrue(model.getResult().get(0) instanceof LinkedHashMap);
    }

    @Test
    public void modelWithoutElementsIsEmpty()
    {
        assertTrue(new DefaultListBackedModel().isEmpty());
    }

    @Test
    public void modelWithElementsIsNotEmpty()
    {
        DefaultListBackedModel model = new DefaultListBackedModel();
        model.addValue("test");

        assertFalse(model.isEmpty());
    }
}
