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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.gregwhitaker.catnap.core.context.CatnapContext;
import com.github.gregwhitaker.catnap.core.context.HttpStatus;
import com.github.gregwhitaker.catnap.core.model.DefaultMapBackedModel;
import com.github.gregwhitaker.catnap.core.model.Model;
import com.github.gregwhitaker.catnap.core.model.builder.DefaultModelBuilder;
import com.github.gregwhitaker.catnap.core.model.builder.ModelBuilder;
import com.github.gregwhitaker.catnap.core.query.builder.CatnapQueryBuilder;
import com.github.gregwhitaker.catnap.core.query.builder.QueryBuilder;

import java.io.OutputStream;

/**
 * Catnap view responsible for creating JSON responses.
 */
public class JsonCatnapView extends CatnapView {
    private ObjectMapper objectMapper;
    private boolean allowNoContentResponse;

    private JsonCatnapView(QueryBuilder queryBuilder, ModelBuilder modelBuilder,
                           ObjectMapper objectMapper, boolean allowNoContentResponse) {
        super(queryBuilder, modelBuilder);
        this.objectMapper = objectMapper;
        this.allowNoContentResponse = allowNoContentResponse;
    }

    @Override
    protected void render(Object value, OutputStream outputStream, CatnapContext context) throws Exception {
        if (!context.isCatnapDisabled()) {
            Model<?> model = modelBuilder.build(value, context);

            //If the request was successful, but the query results in no fields being returned, we need to set the
            //http status to show that we succeeded, but are not returning content.
            if (model.isEmpty()) {
                if (allowNoContentResponse) {
                    context.getResponse().setStatus(HttpStatus.NO_CONTENT.value());
                } else {
                    context.getResponse().setStatus(HttpStatus.OK.value());
                }
            }

            objectMapper.writeValue(outputStream, model.getResult());
        } else {
            //Catnap has been disabled for this request so go ahead and just render the original
            //model without evaluating the Catnap query
            if (value != null) {
                objectMapper.writeValue(outputStream, value);
            } else {
                if (allowNoContentResponse) {
                    context.getResponse().setStatus(HttpStatus.NO_CONTENT.value());
                } else {
                    context.getResponse().setStatus(HttpStatus.OK.value());
                }

                objectMapper.writeValue(outputStream, new DefaultMapBackedModel());
            }
        }
    }

    @Override
    protected void renderError(Object value, OutputStream outputStream, CatnapContext context) throws Exception {
        objectMapper.writeValue(outputStream, value);
    }

    @Override
    public String getContentType() {
        return "application/json";
    }

    @Override
    public String getHrefSuffix() {
        return ".json";
    }

    /**
     * Static builder used to create an instance of {@link JsonCatnapView}
     */
    public static class Builder implements CatnapViewBuilder<JsonCatnapView> {
        private QueryBuilder queryBuilder = new CatnapQueryBuilder();
        private ModelBuilder modelBuilder = new DefaultModelBuilder();
        private ObjectMapper objectMapper;
        private boolean allowNoContentResponse = true;

        public Builder() {
            //Enable pretty-printing by default
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            this.objectMapper = objectMapper;
        }

        /**
         * Sets the {@link QueryBuilder} to be used by this view.
         *
         * @param queryBuilder {@link QueryBuilder} used by this view to create Catnap queries.
         * @return this {@link Builder} instance
         */
        public Builder withQueryBuilder(QueryBuilder queryBuilder) {
            this.queryBuilder = queryBuilder;
            return this;
        }

        /**
         * Sets the {@link ModelBuilder} to be used by this view.
         *
         * @param modelBuilder {@link ModelBuilder} used by this view to create Catnap models.
         * @return this {@link Builder} instance
         */
        public Builder withModelBuilder(ModelBuilder modelBuilder) {
            this.modelBuilder = modelBuilder;
            return this;
        }

        /**
         * Sets the {@link com.fasterxml.jackson.databind.ObjectMapper} to be used by this view.
         *
         * @param objectMapper {@link com.fasterxml.jackson.databind.ObjectMapper} used by this view to serialize models to Json.
         * @return this {@link Builder} instance
         */
        public Builder withObjectMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
            return this;
        }

        /**
         * Configures the view behaviour for Catnap queries that result in no data.
         * <p>
         * When this value is <code>true</code> a Catnap query that results in no data being returned will
         * return an HTTP 204 status without a response body.
         * <p>
         * When this value is <code>false</code> a Catnap query that results in no data being returned will
         * return an HTTP 200 status with an empty response body.
         * <p>
         * The default value is <code>true</code>.
         * </p>
         *
         * @param value boolean value indicating whether or not to return empty response bodies for Catnap
         *              queries that result in no data
         * @return this {@link Builder} instance
         */
        public Builder withAllowNoContentResponse(boolean value) {
            this.allowNoContentResponse = value;
            return this;
        }

        @Override
        public JsonCatnapView build() {
            return new JsonCatnapView(queryBuilder, modelBuilder, objectMapper, allowNoContentResponse);
        }
    }
}
