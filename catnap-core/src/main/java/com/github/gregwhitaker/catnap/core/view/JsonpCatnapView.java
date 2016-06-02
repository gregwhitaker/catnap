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

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gregwhitaker.catnap.core.context.CatnapContext;
import com.github.gregwhitaker.catnap.core.context.HttpStatus;
import com.github.gregwhitaker.catnap.core.exception.CatnapException;
import com.github.gregwhitaker.catnap.core.model.DefaultMapBackedModel;
import com.github.gregwhitaker.catnap.core.model.Model;
import com.github.gregwhitaker.catnap.core.model.builder.DefaultModelBuilder;
import com.github.gregwhitaker.catnap.core.model.builder.ModelBuilder;
import com.github.gregwhitaker.catnap.core.query.builder.QueryBuilder;
import com.github.gregwhitaker.catnap.core.query.builder.CatnapQueryBuilder;
import org.apache.commons.lang.StringUtils;

import java.io.OutputStream;
import java.util.regex.Pattern;

/**
 * Catnap view responsible for creating JSONP responses.
 */
public class JsonpCatnapView extends CatnapView {
    private static final String CALLBACK_PARAMETER = "callback";
    private static final String DEFAULT_CALLBACK = "callback";
    private static final Pattern SAFE_CALLBACK_PATTERN = Pattern.compile("[a-zA-Z0-9_\\.]+");

    private ObjectMapper objectMapper;

    public JsonpCatnapView(QueryBuilder queryBuilder, ModelBuilder modelBuilder, ObjectMapper objectMapper) {
        super(queryBuilder, modelBuilder);
        this.objectMapper = objectMapper;
    }

    @Override
    protected void render(Object value, OutputStream outputStream, CatnapContext context) throws Exception {
        //JSONP responses always return a 200 with the real status set in the body of the message.
        context.getResponse().setStatus(200);

        JsonGenerator generator = objectMapper.getFactory().createGenerator(outputStream, JsonEncoding.UTF8);

        if(!context.isCatnapDisabled()) {
            Model<?> model = modelBuilder.build(value, context);

            //If the request was successful, but the query results in no fields being returned, we need to set the
            //wrapped http status to show that we succeeded, but are not returning content.
            if(!model.isEmpty()) {
                generator.writeRaw(callback(context) + "(");
                generator.writeObject(new JsonpResponse(context.getHttpStatus().value(), model.getResult()));
                generator.writeRaw(");");
            } else {
                generator.writeRaw(callback(context) + "(");
                generator.writeObject(new JsonpResponse(HttpStatus.NO_CONTENT.value(), model.getResult()));
                generator.writeRaw(");");
            }
        } else {
            //Catnap has been disabled for this request so go ahead and just render the original
            //model without evaluating the Catnap query
            if(value != null) {
                generator.writeRaw(callback(context) + "(");
                generator.writeObject(new JsonpResponse(context.getHttpStatus().value(), value));
                generator.writeRaw(");");
            } else {
                generator.writeRaw(callback(context) + "(");
                generator.writeObject(new JsonpResponse(HttpStatus.NO_CONTENT.value(), new DefaultMapBackedModel()));
                generator.writeRaw(");");
            }
        }

        generator.flush();
    }

    @Override
    protected void renderError(Object value, OutputStream outputStream, CatnapContext context) throws Exception {
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
    private String callback(CatnapContext context) {
        String callback = DEFAULT_CALLBACK;

        if(StringUtils.isNotBlank(context.getRequest().getParameter(CALLBACK_PARAMETER))) {
            String paramValue = context.getRequest().getParameter(CALLBACK_PARAMETER);

            //Prevent XSS attacks
            if(SAFE_CALLBACK_PATTERN.matcher(paramValue).matches()) {
                callback = paramValue;
            } else {
                throw new CatnapException("Invalid characters in jsonp callback");
            }
        }

        return callback;
    }

    @Override
    public String getContentType() {
        return "application/x-javascript";
    }

    @Override
    public String getHrefSuffix() {
        return ".jsonp";
    }

    /**
     * Response wrapper that holds the real HTTP status and the response.
     */
    private static class JsonpResponse {
        private final int httpStatus;
        private final Object response;

        JsonpResponse(int httpStatus, Object model) {
            this.httpStatus = httpStatus;
            this.response = model;
        }

        public int getHttpStatus() {
            return httpStatus;
        }

        public Object getResponse() {
            return response;
        }
    }

    /**
     * Static builder used to create an instance of {@link JsonpCatnapView}
     */
    public static class Builder implements CatnapViewBuilder<JsonpCatnapView> {
        private QueryBuilder queryBuilder = new CatnapQueryBuilder();
        private ModelBuilder modelBuilder = new DefaultModelBuilder();
        private ObjectMapper objectMapper = new ObjectMapper();

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
         * Sets the {@link com.fasterxml.jackson.databind.ObjectMapper} to be used by this view.
         * @param objectMapper {@link com.fasterxml.jackson.databind.ObjectMapper} used by this view to serialize models to Json.
         * @return this {@link Builder} instance
         */
        public Builder withObjectMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
            return this;
        }

        @Override
        public JsonpCatnapView build() {
            return new JsonpCatnapView(queryBuilder, modelBuilder, objectMapper);
        }
    }
}
