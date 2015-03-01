package com.catnap.core.view;

import com.catnap.core.context.CatnapContext;
import com.catnap.core.exception.ViewRenderException;
import com.catnap.core.model.builder.DefaultModelBuilder;
import com.catnap.core.model.builder.ModelBuilder;
import com.catnap.core.query.builder.QueryBuilder;
import com.catnap.core.query.builder.SimpleQueryBuilder;
import com.catnap.core.util.ClassUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.ClassUtils;
import org.omg.DynamicAny._DynStructStub;

import javax.xml.bind.*;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Catnap view responsible for creating XML responses.
 * @author gwhit7
 */
public class XmlCatnapView extends CatnapView
{
    private final ConcurrentHashMap<Class<?>, JAXBContext> jaxbContexts = new ConcurrentHashMap<Class<?>, JAXBContext>(64);
    private final Map<String, Object> marshallerProperties;

    private XmlCatnapView(QueryBuilder queryBuilder, ModelBuilder modelBuilder, Map<String, Object> marshallerProperties)
    {
        super(queryBuilder, modelBuilder);
        this.marshallerProperties = marshallerProperties;
    }

    @Override
    protected void render(Object value, OutputStream outputStream, CatnapContext context) throws Exception
    {
        try
        {
            Class clazz = ClassUtil.loadClass(value);
            Marshaller marshaller = createMarshaller(clazz);
            marshaller.marshal(value, outputStream);
        }
        catch (MarshalException e)
        {
            throw new ViewRenderException("Could not marshal [" + value + "]: ", e);
        }
    }

    @Override
    protected void renderError(Object value, OutputStream outputStream, CatnapContext context) throws Exception
    {
        render(value, outputStream, context);
    }

    /**
     * Creates a {@link javax.xml.bind.Marshaller} instance for the specified class.
     * @param clazz class for which to create a Marshaller
     * @return a {@link javax.xml.bind.Marshaller} that supports serializing the specified class
     */
    private Marshaller createMarshaller(Class clazz)
    {
        try
        {
            JAXBContext context = getJaxbContext(clazz);
            Marshaller marshaller = context.createMarshaller();
            setMarshallerProperties(marshaller);
            return marshaller;
        }
        catch (JAXBException e)
        {
            throw new ViewRenderException("Could not create Marshaller for class [" + clazz + "]: ", e);
        }
    }

    /**
     * Sets customized marshaller properties, defined when this view was built, on the supplied
     * {@link javax.xml.bind.Marshaller} instance.
     * @param marshaller {@link javax.xml.bind.Marshaller} to customize
     * @throws PropertyException
     */
    private void setMarshallerProperties(Marshaller marshaller) throws PropertyException
    {
        Iterator<String> keyIter = marshallerProperties.keySet().iterator();

        while(keyIter.hasNext())
        {
            String key = keyIter.next();
            marshaller.setProperty(key, marshallerProperties.get(key));
        }
    }

    /**
     * Retrieves the cached {@link javax.xml.bind.JAXBContext} for a class.  If a context does not exist for the class
     * a new context will be created.
     * @param clazz class for which to retrieve a context
     * @return JAXBContext that supports serialization of the supplied class
     */
    private JAXBContext getJaxbContext(Class clazz)
    {
        if(clazz == null)
        {
            throw new ViewRenderException("Cannot create JAXBContext for null type");
        }

        JAXBContext jaxbContext = jaxbContexts.get(clazz);

        if(jaxbContext == null)
        {
            try
            {
                jaxbContext = JAXBContext.newInstance(clazz);
                jaxbContexts.putIfAbsent(clazz, jaxbContext);
            }
            catch (JAXBException e)
            {
                throw new ViewRenderException("Could not create JAXBContext for type [" + clazz + "]", e);
            }
        }

        return jaxbContext;
    }

    @Override
    public String getContentType()
    {
        return "application/xml";
    }

    @Override
    public String getHrefSuffix()
    {
        return ".xml";
    }

    /**
     * Static builder used to create an instance of {@link com.catnap.core.view.XmlCatnapView}
     */
    public static class Builder implements CatnapViewBuilder<XmlCatnapView>
    {
        private QueryBuilder queryBuilder = new SimpleQueryBuilder();
        private ModelBuilder modelBuilder = new DefaultModelBuilder();
        private Map<String, Object> marshallerProperties = new HashMap<String, Object>(5);

        /**
         * Sets the {@link com.catnap.core.query.builder.QueryBuilder} to be used by this view.
         * @param queryBuilder {@link com.catnap.core.query.builder.QueryBuilder} used by this view to create Catnap queries.
         * @return this {@link com.catnap.core.view.JsonCatnapView.Builder} instance
         */
        public Builder withQueryBuilder(QueryBuilder queryBuilder)
        {
            this.queryBuilder = queryBuilder;
            return this;
        }

        /**
         * Sets the {@link com.catnap.core.model.builder.ModelBuilder} to be used by this view.
         * @param modelBuilder {@link com.catnap.core.model.builder.ModelBuilder} used by this view to create Catnap models.
         * @return this {@link com.catnap.core.view.JsonCatnapView.Builder} instance
         */
        public Builder withModelBuilder(ModelBuilder modelBuilder)
        {
            this.modelBuilder = modelBuilder;
            return this;
        }

        /**
         * Sets the <code>jaxb.encoding</code> property for all {@link javax.xml.bind.Marshaller} instances used by this view.
         * @param value value to set <code>jaxb.encoding</code> property
         * @return this {@link com.catnap.core.view.XmlCatnapView.Builder} instance
         */
        public Builder withMarshallerJaxbEncoding(String value)
        {
            this.marshallerProperties.put(Marshaller.JAXB_ENCODING, value);
            return this;
        }

        /**
         * Sets the <code>jaxb.schemaLocation</code> property for all {@link javax.xml.bind.Marshaller} instances used
         * by this view.
         * @param value value to set <code>jaxb.schemaLocation</code> property
         * @return this {@link com.catnap.core.view.XmlCatnapView.Builder} instance
         */
        public Builder withMarshallerSchemaLocation(String value)
        {
            this.marshallerProperties.put(Marshaller.JAXB_SCHEMA_LOCATION, value);
            return this;
        }

        /**
         * Sets the <code>jaxb.noNamespaceSchemaLocation</code> property for all {@link javax.xml.bind.Marshaller}
         * instances used by this view.
         * @param value value to set <code>jaxb.noNamespaceSchemaLocation</code> property
         * @return this {@link com.catnap.core.view.XmlCatnapView.Builder} instance
         */
        public Builder withMarshallerNoNamespaceSchemaLocation(String value)
        {
            this.marshallerProperties.put(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, value);
            return this;
        }

        /**
         * Sets the <code>jaxb.formatted.output</code> property for all {@link javax.xml.bind.Marshaller} instances
         * used by this view.
         * @param value value to set <code>jaxb.formatted.output</code> property
         * @return this {@link com.catnap.core.view.XmlCatnapView.Builder} instance
         */
        public Builder withMarshallerFormattedOutput(Boolean value)
        {
            this.marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, value);
            return this;
        }

        /**
         * Sets the <code>jaxb.fragment</code> property for all {@link javax.xml.bind.Marshaller} instances used by this view.
         * @param value value to set <code>jaxb.fragment</code> property
         * @return this {@link com.catnap.core.view.XmlCatnapView.Builder} instance
         */
        public Builder withMarshallerFragment(Boolean value)
        {
            this.marshallerProperties.put(Marshaller.JAXB_FRAGMENT, value);
            return this;
        }

        @Override
        public XmlCatnapView build()
        {
            return new XmlCatnapView(queryBuilder, modelBuilder, marshallerProperties);
        }
    }
}
