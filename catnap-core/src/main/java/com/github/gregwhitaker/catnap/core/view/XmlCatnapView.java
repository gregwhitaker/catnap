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

package com.github.gregwhitaker.catnap.core.view;

import com.github.gregwhitaker.catnap.core.context.CatnapContext;
import com.github.gregwhitaker.catnap.core.exception.ViewRenderException;
import com.github.gregwhitaker.catnap.core.model.builder.DefaultModelBuilder;
import com.github.gregwhitaker.catnap.core.model.builder.ModelBuilder;
import com.github.gregwhitaker.catnap.core.query.builder.QueryBuilder;
import com.github.gregwhitaker.catnap.core.query.builder.SimpleQueryBuilder;
import com.github.gregwhitaker.catnap.core.util.ClassUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Catnap view responsible for creating XML responses.
 */
public class XmlCatnapView extends CatnapView {
    private final ConcurrentHashMap<Class<?>, JAXBContext> jaxbContexts = new ConcurrentHashMap<Class<?>, JAXBContext>(64);
    private final Map<String, Object> marshallerProperties;

    private XmlCatnapView(QueryBuilder queryBuilder, ModelBuilder modelBuilder, Map<String, Object> marshallerProperties) {
        super(queryBuilder, modelBuilder);
        this.marshallerProperties = marshallerProperties;
    }

    @Override
    protected void render(Object value, OutputStream outputStream, CatnapContext context) throws Exception {
        try {
            Class clazz = ClassUtil.loadClass(value);
            Marshaller marshaller = createMarshaller(clazz);
            marshaller.marshal(value, outputStream);
        } catch (MarshalException e) {
            throw new ViewRenderException("Could not marshal [" + value + "]: ", e);
        }
    }

    @Override
    protected void renderError(Object value, OutputStream outputStream, CatnapContext context) throws Exception {
        render(value, outputStream, context);
    }

    /**
     * Creates a {@link javax.xml.bind.Marshaller} instance for the specified class.
     * @param clazz class for which to create a Marshaller
     * @return a {@link javax.xml.bind.Marshaller} that supports serializing the specified class
     */
    private Marshaller createMarshaller(Class clazz) {
        try {
            JAXBContext context = getJaxbContext(clazz);
            Marshaller marshaller = context.createMarshaller();
            setMarshallerProperties(marshaller);
            return marshaller;
        } catch (JAXBException e) {
            throw new ViewRenderException("Could not create Marshaller for class [" + clazz + "]: ", e);
        }
    }

    /**
     * Sets customized marshaller properties, defined when this view was built, on the supplied
     * {@link javax.xml.bind.Marshaller} instance.
     * @param marshaller {@link javax.xml.bind.Marshaller} to customize
     * @throws PropertyException
     */
    private void setMarshallerProperties(Marshaller marshaller) throws PropertyException {
        for (Map.Entry<String, Object> entry : marshallerProperties.entrySet()) {
            marshaller.setProperty(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Retrieves the cached {@link javax.xml.bind.JAXBContext} for a class.  If a context does not exist for the class
     * a new context will be created.
     * @param clazz class for which to retrieve a context
     * @return JAXBContext that supports serialization of the supplied class
     */
    private JAXBContext getJaxbContext(Class clazz) {
        if(clazz == null) {
            throw new ViewRenderException("Cannot create JAXBContext for null type");
        }

        JAXBContext jaxbContext = jaxbContexts.get(clazz);

        if(jaxbContext == null) {
            jaxbContexts.computeIfAbsent(clazz, ctx -> {
                try {
                    return JAXBContext.newInstance(clazz);
                } catch (JAXBException e) {
                    throw new ViewRenderException("Could not create JAXBContext for type [" + clazz + "]", e);
                }
            });
        }

        return jaxbContexts.get(clazz);
    }

    @Override
    public String getContentType() {
        return "application/xml";
    }

    @Override
    public String getHrefSuffix() {
        return ".xml";
    }

    /**
     * Static builder used to create an instance of {@link XmlCatnapView}
     */
    public static class Builder implements CatnapViewBuilder<XmlCatnapView> {
        private QueryBuilder queryBuilder = new SimpleQueryBuilder();
        private ModelBuilder modelBuilder = new DefaultModelBuilder();
        private Map<String, Object> marshallerProperties = new HashMap<String, Object>(5);

        /**
         * Sets the {@link QueryBuilder} to be used by this view.
         * @param queryBuilder {@link QueryBuilder} used by this view to create Catnap queries.
         * @return this {@link Builder} instance
         */
        public Builder withQueryBuilder(QueryBuilder queryBuilder) {
            this.queryBuilder = queryBuilder;
            return this;
        }

        /**
         * Sets the {@link ModelBuilder} to be used by this view.
         * @param modelBuilder {@link ModelBuilder} used by this view to create Catnap models.
         * @return this {@link Builder} instance
         */
        public Builder withModelBuilder(ModelBuilder modelBuilder) {
            this.modelBuilder = modelBuilder;
            return this;
        }

        /**
         * Sets the <code>jaxb.encoding</code> property for all {@link javax.xml.bind.Marshaller} instances used by this view.
         * @param value value to set <code>jaxb.encoding</code> property
         * @return this {@link Builder} instance
         */
        public Builder withMarshallerJaxbEncoding(String value) {
            this.marshallerProperties.put(Marshaller.JAXB_ENCODING, value);
            return this;
        }

        /**
         * Sets the <code>jaxb.schemaLocation</code> property for all {@link javax.xml.bind.Marshaller} instances used
         * by this view.
         * @param value value to set <code>jaxb.schemaLocation</code> property
         * @return this {@link Builder} instance
         */
        public Builder withMarshallerSchemaLocation(String value) {
            this.marshallerProperties.put(Marshaller.JAXB_SCHEMA_LOCATION, value);
            return this;
        }

        /**
         * Sets the <code>jaxb.noNamespaceSchemaLocation</code> property for all {@link javax.xml.bind.Marshaller}
         * instances used by this view.
         * @param value value to set <code>jaxb.noNamespaceSchemaLocation</code> property
         * @return this {@link Builder} instance
         */
        public Builder withMarshallerNoNamespaceSchemaLocation(String value) {
            this.marshallerProperties.put(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, value);
            return this;
        }

        /**
         * Sets the <code>jaxb.formatted.output</code> property for all {@link javax.xml.bind.Marshaller} instances
         * used by this view.
         * @param value value to set <code>jaxb.formatted.output</code> property
         * @return this {@link Builder} instance
         */
        public Builder withMarshallerFormattedOutput(Boolean value) {
            this.marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, value);
            return this;
        }

        /**
         * Sets the <code>jaxb.fragment</code> property for all {@link javax.xml.bind.Marshaller} instances used by this view.
         * @param value value to set <code>jaxb.fragment</code> property
         * @return this {@link Builder} instance
         */
        public Builder withMarshallerFragment(Boolean value) {
            this.marshallerProperties.put(Marshaller.JAXB_FRAGMENT, value);
            return this;
        }

        @Override
        public XmlCatnapView build() {
            return new XmlCatnapView(queryBuilder, modelBuilder, marshallerProperties);
        }
    }
}
