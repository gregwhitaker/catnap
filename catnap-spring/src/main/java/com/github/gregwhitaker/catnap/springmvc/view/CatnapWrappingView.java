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

package com.github.gregwhitaker.catnap.springmvc.view;

import com.github.gregwhitaker.catnap.core.view.CatnapView;
import com.github.gregwhitaker.catnap.springmvc.interceptor.CatnapResponseBodyHandlerInterceptor;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class CatnapWrappingView implements View, WrappingView<CatnapView> {
    public static final String DEFAULT_MODELNAME = "result";

    private final CatnapView catnapView;
    private final String modelName;

    public CatnapWrappingView(final CatnapView view) {
        this(view, DEFAULT_MODELNAME);
    }

    public CatnapWrappingView(final CatnapView view, final String modelName) {
        this.catnapView = view;
        this.modelName = modelName;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        setContentTypeHeader(response);

        catnapView.render(request, response, extractResult(model), httpStatus(request));
    }

    public void setContentTypeHeader(HttpServletResponse response) {
        response.setContentType(getContentType());
        response.setCharacterEncoding(getCharacterEncoding());
    }

    public com.github.gregwhitaker.catnap.core.context.HttpStatus httpStatus(HttpServletRequest request) {
        Object attr = request.getAttribute(RESPONSE_STATUS_ATTRIBUTE);

        if (attr != null) {
            int value = ((HttpStatus) attr).value();
            return com.github.gregwhitaker.catnap.core.context.HttpStatus.valueOf(value);
        }

        return com.github.gregwhitaker.catnap.core.context.HttpStatus.OK;
    }

    public Object extractResult(Map<String, ?> model) {
        if (model != null && !model.isEmpty()) {
            if (model.size() == 1) {
                return model.values().iterator().next();
            } else {
                if (model.containsKey(CatnapResponseBodyHandlerInterceptor.MODEL_NAME)) {
                    return model.get(CatnapResponseBodyHandlerInterceptor.MODEL_NAME);
                } else {
                    return model.get(getModelName());
                }
            }
        }

        return null;
    }

    @Override
    public String getContentType() {
        return catnapView.getContentType();
    }

    @Override
    public String getCharacterEncoding() {
        return catnapView.getEncoding();
    }

    @Override
    public CatnapView getWrappedView() {
        return catnapView;
    }

    @Override
    public String getModelName() {
        return modelName;
    }
}
