package com.catnap.core.view;

import com.catnap.core.context.CatnapContext;
import com.catnap.core.context.HttpStatus;
import com.catnap.core.model.DefaultMapBackedModel;
import com.catnap.core.model.Model;
import com.catnap.core.model.builder.DefaultModelBuilder;
import com.catnap.core.model.builder.ModelBuilder;
import com.catnap.core.query.builder.QueryBuilder;
import com.catnap.core.query.builder.SimpleQueryBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.OutputStream;

/**
 * Catnap view responsible for creating JSON responses.
 * @author Woody
 */
public class JsonCatnapView extends CatnapView
{
    private ObjectMapper objectMapper;
    private boolean allowNoContentResponse;

    private JsonCatnapView(QueryBuilder queryBuilder, ModelBuilder modelBuilder,
                           ObjectMapper objectMapper, boolean allowNoContentResponse)
    {
        super(queryBuilder, modelBuilder);
        this.objectMapper = objectMapper;
        this.allowNoContentResponse = allowNoContentResponse;
    }

    @Override
    protected void render(Object value, OutputStream outputStream, CatnapContext context) throws Exception
    {
        if(!context.isCatnapDisabled())
        {
            Model<?> model = modelBuilder.build(value, context);

            //If the request was successful, but the query results in no fields being returned, we need to set the
            //http status to show that we succeeded, but are not returning content.
            if(model.isEmpty())
            {
                if(allowNoContentResponse)
                {
                    context.getResponse().setStatus(HttpStatus.NO_CONTENT.value());
                }
                else
                {
                    context.getResponse().setStatus(HttpStatus.OK.value());
                }
            }

            objectMapper.writeValue(outputStream, model.getResult());
        }
        else
        {
            //Catnap has been disabled for this request so go ahead and just render the original
            //model without evaluating the Catnap query
            if(value != null)
            {
                objectMapper.writeValue(outputStream, value);
            }
            else
            {
                if(allowNoContentResponse)
                {
                    context.getResponse().setStatus(HttpStatus.NO_CONTENT.value());
                }
                else
                {
                    context.getResponse().setStatus(HttpStatus.OK.value());
                }

                objectMapper.writeValue(outputStream, new DefaultMapBackedModel());
            }
        }
    }

    @Override
    protected void renderError(Object value, OutputStream outputStream, CatnapContext context) throws Exception
    {
        objectMapper.writeValue(outputStream, value);
    }

    @Override
    public String getContentType()
    {
        return "application/json";
    }

    @Override
    public String getHrefSuffix()
    {
        return ".json";
    }

    /**
     * Static builder used to create an instance of {@link com.catnap.core.view.JsonCatnapView}
     */
    public static class Builder implements CatnapViewBuilder<JsonCatnapView>
    {
        private QueryBuilder queryBuilder = new SimpleQueryBuilder();
        private ModelBuilder modelBuilder = new DefaultModelBuilder();
        private ObjectMapper objectMapper;
        private boolean allowNoContentResponse = true;

        public Builder()
        {
            //Enable pretty-printing by default
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            this.objectMapper = objectMapper;
        }

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
         * Sets the {@link com.fasterxml.jackson.databind.ObjectMapper} to be used by this view.
         * @param objectMapper {@link com.fasterxml.jackson.databind.ObjectMapper} used by this view to serialize models to Json.
         * @return this {@link com.catnap.core.view.JsonCatnapView.Builder} instance
         */
        public Builder withObjectMapper(ObjectMapper objectMapper)
        {
            this.objectMapper = objectMapper;
            return this;
        }

        /**
         * Configures the view behaviour for Catnap queries that result in no data.
         * <p>
         * When this value is <code>true</code> a Catnap query that results in no data being returned will
         * return an HTTP 204 status without a response body.
         *
         * When this value is <code>false</code> a Catnap query that results in no data being returned will
         * return an HTTP 200 status with an empty response body.
         *
         * The default value is <code>true</code>.
         * </p>
         * @param value boolean value indicating whether or not to return empty response bodies for Catnap
         *              queries that result in no data
         * @return this {@link com.catnap.core.view.JsonCatnapView.Builder} instance
         */
        public Builder withAllowNoContentResponse(boolean value)
        {
            this.allowNoContentResponse = value;
            return this;
        }

        @Override
        public JsonCatnapView build()
        {
            return new JsonCatnapView(queryBuilder, modelBuilder, objectMapper, allowNoContentResponse);
        }
    }
}
