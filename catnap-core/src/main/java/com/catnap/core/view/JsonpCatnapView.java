package com.catnap.core.view;

import com.catnap.core.context.CatnapContext;
import com.catnap.core.context.HttpStatus;
import com.catnap.core.exception.CatnapException;
import com.catnap.core.model.DefaultMapBackedModel;
import com.catnap.core.model.Model;
import com.catnap.core.model.builder.DefaultModelBuilder;
import com.catnap.core.model.builder.ModelBuilder;
import com.catnap.core.query.builder.QueryBuilder;
import com.catnap.core.query.builder.SimpleQueryBuilder;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;

import java.io.OutputStream;
import java.util.regex.Pattern;

/**
 * Catnap view responsible for creating JSONP responses.
 * @author Woody
 */
public class JsonpCatnapView extends CatnapView
{
    private static final String CALLBACK_PARAMETER = "callback";
    private static final String DEFAULT_CALLBACK = "callback";
    private static final Pattern SAFE_CALLBACK_PATTERN = Pattern.compile("[a-zA-Z0-9_\\.]+");

    private ObjectMapper objectMapper;

    public JsonpCatnapView(QueryBuilder queryBuilder, ModelBuilder modelBuilder, ObjectMapper objectMapper)
    {
        super(queryBuilder, modelBuilder);
        this.objectMapper = objectMapper;
    }

    @Override
    protected void render(Object value, OutputStream outputStream, CatnapContext context) throws Exception
    {
        //JSONP responses always return a 200 with the real status set in the body of the message.
        context.getResponse().setStatus(200);

        JsonGenerator generator = objectMapper.getFactory().createGenerator(outputStream, JsonEncoding.UTF8);

        if(!context.isCatnapDisabled())
        {
            Model<?> model = modelBuilder.build(value, context);

            //If the request was successful, but the query results in no fields being returned, we need to set the
            //wrapped http status to show that we succeeded, but are not returning content.
            if(!model.isEmpty())
            {
                generator.writeRaw(callback(context) + "(");
                generator.writeObject(new JsonpResponse(context.getHttpStatus().value(), model.getResult()));
                generator.writeRaw(");");
            }
            else
            {
                generator.writeRaw(callback(context) + "(");
                generator.writeObject(new JsonpResponse(HttpStatus.NO_CONTENT.value(), model.getResult()));
                generator.writeRaw(");");
            }
        }
        else
        {
            //Catnap has been disabled for this request so go ahead and just render the original
            //model without evaluating the Catnap query
            if(value != null)
            {
                generator.writeRaw(callback(context) + "(");
                generator.writeObject(new JsonpResponse(context.getHttpStatus().value(), value));
                generator.writeRaw(");");
            }
            else
            {
                generator.writeRaw(callback(context) + "(");
                generator.writeObject(new JsonpResponse(HttpStatus.NO_CONTENT.value(), new DefaultMapBackedModel()));
                generator.writeRaw(");");
            }
        }


        generator.flush();
    }

    @Override
    protected void renderError(Object value, OutputStream outputStream, CatnapContext context) throws Exception
    {
        //JSONP responses always return a 200 with the real status set in the body of the message.
        context.getResponse().setStatus(200);

        JsonGenerator generator = objectMapper.getFactory().createGenerator(outputStream, JsonEncoding.UTF8);
        generator.writeRaw(callback(context) + "(");
        generator.writeObject(new JsonpResponse(context.getHttpStatus().value(), value));
        generator.writeRaw(");");

        generator.flush();
    }

    /**
     * Retrieves the JSONP callback method name from the context.  If the callback name was supplied as a query
     * parameter this method prevents the name from containing characters that could be used to inject scripts as
     * part of a XSS attack.
     * @param context render context
     * @return JSONP callback name supplied in the callback query parameter or the default
     * if no callback query parameter is defined.
     */
    private String callback(CatnapContext context)
    {
        String callback = DEFAULT_CALLBACK;

        if(StringUtils.isNotBlank(context.getRequest().getParameter(CALLBACK_PARAMETER)))
        {
            String paramValue = context.getRequest().getParameter(CALLBACK_PARAMETER);

            //Prevent XSS attacks
            if(SAFE_CALLBACK_PATTERN.matcher(paramValue).matches())
            {
                callback = paramValue;
            }
            else
            {
                throw new CatnapException("Invalid characters in jsonp callback");
            }
        }

        return callback;
    }

    @Override
    public String getContentType()
    {
        return "application/x-javascript";
    }

    @Override
    public String getHrefSuffix()
    {
        return ".jsonp";
    }

    /**
     * Response wrapper that holds the real HTTP status and the response.
     */
    private class JsonpResponse
    {
        private final int httpStatus;
        private final Object response;

        public JsonpResponse(int httpStatus, Object model)
        {
            this.httpStatus = httpStatus;
            this.response = model;
        }

        public int getHttpStatus()
        {
            return httpStatus;
        }

        public Object getResponse()
        {
            return response;
        }
    }

    /**
     * Static builder used to create an instance of {@link com.catnap.core.view.JsonpCatnapView}
     */
    public static class Builder implements CatnapViewBuilder<JsonpCatnapView>
    {
        private QueryBuilder queryBuilder = new SimpleQueryBuilder();
        private ModelBuilder modelBuilder = new DefaultModelBuilder();
        private ObjectMapper objectMapper = new ObjectMapper();

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

        @Override
        public JsonpCatnapView build()
        {
            return new JsonpCatnapView(queryBuilder, modelBuilder, objectMapper);
        }
    }
}
