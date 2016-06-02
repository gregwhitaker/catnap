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

import com.github.gregwhitaker.catnap.core.context.CatnapContext;
import com.github.gregwhitaker.catnap.core.context.HttpStatus;
import com.github.gregwhitaker.catnap.core.model.builder.DefaultModelBuilder;
import com.github.gregwhitaker.catnap.core.query.builder.CatnapQueryBuilder;
import com.github.gregwhitaker.catnap.core.view.CatnapView;
import com.github.gregwhitaker.catnap.springmvc.interceptor.CatnapResponseBodyHandlerInterceptor;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.View;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CatnapWrappingViewTest {

    static class JsonView extends CatnapView {
        private boolean renderExecuted = false;

        public JsonView() {
            super(new CatnapQueryBuilder(), new DefaultModelBuilder());
        }

        @Override
        protected void render(Object value, OutputStream outputStream, CatnapContext context) throws Exception {
            renderExecuted = true;
        }

        @Override
        protected void renderError(Object value, OutputStream outputStream, CatnapContext context) throws Exception {}

        @Override
        public String getContentType() {
            return "application/json";
        }

        @Override
        public String getHrefSuffix() {
            return ".json";
        }

        public boolean isRenderExecuted() {
            return renderExecuted;
        }
    }

    static class BeanModel {}

    @Test
    public void renderOnWrappingViewRendersWrappedView() throws Exception {
        JsonView wrappedView = new JsonView();
        CatnapWrappingView view = new CatnapWrappingView(wrappedView);
        view.render(new HashMap<>(), new MockHttpServletRequest(), new MockHttpServletResponse());

        assertTrue(wrappedView.isRenderExecuted());
    }

    @Test
    public void setContentTypeHeaderSetsBothContentTypeAndEncoding() {
        MockHttpServletResponse response = new MockHttpServletResponse();

        CatnapWrappingView view = new CatnapWrappingView(new JsonView());
        view.setContentTypeHeader(response);

        assertEquals("application/json", response.getContentType());
        assertEquals("UTF-8", response.getCharacterEncoding());
    }

    @Test
    public void httpStatusReturnsRequestStatus() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, org.springframework.http.HttpStatus.ACCEPTED);

        assertEquals(HttpStatus.ACCEPTED, new CatnapWrappingView(new JsonView()).httpStatus(request));
    }

    @Test
    public void httpStatusReturns200ByDefault() {
        assertEquals(HttpStatus.OK, new CatnapWrappingView(new JsonView()).httpStatus(new MockHttpServletRequest()));
    }

    @Test
    public void extractModelFromSingleResultModel() {
        Map<String, Object> modelMap = new HashMap<String, Object>(1);
        modelMap.put("model", new BeanModel());

        CatnapWrappingView view = new CatnapWrappingView(new JsonView());
        assertTrue(view.extractResult(modelMap) instanceof BeanModel);
    }

    @Test
    public void extractModelWithoutSpecifiedModelName() {
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("params", new Object());
        modelMap.put(CatnapWrappingView.DEFAULT_MODELNAME, new BeanModel());

        CatnapWrappingView view = new CatnapWrappingView(new JsonView());
        assertTrue(view.extractResult(modelMap) instanceof BeanModel);
    }

    @Test
    public void extractModelWithModelName() {
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("params", new Object());
        modelMap.put("customModelName", new BeanModel());

        CatnapWrappingView view = new CatnapWrappingView(new JsonView(), "customModelName");
        assertTrue(view.extractResult(modelMap) instanceof BeanModel);
    }

    @Test
    public void extractModelWithHandlerInterceptorDefaultName() {
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("params", new Object());
        modelMap.put(CatnapResponseBodyHandlerInterceptor.MODEL_NAME, new BeanModel());

        CatnapWrappingView view = new CatnapWrappingView(new JsonView());
        assertTrue(view.extractResult(modelMap) instanceof BeanModel);
    }

    @Test
    public void extractEmptyModelReturnsNull() {
        assertNull(new CatnapWrappingView(new JsonView()).extractResult(new HashMap<>()));
    }

    @Test
    public void extractNullModelReturnsNull() {
        assertNull(new CatnapWrappingView(new JsonView()).extractResult(null));
    }

    @Test
    public void getModelNameWithoutSpecifiedNameReturnsDefault() {
        assertEquals(CatnapWrappingView.DEFAULT_MODELNAME, new CatnapWrappingView(new JsonView()).getModelName());
    }

    @Test
    public void getModelNameReturnsSpecifiedName() {
        assertEquals("modelName", new CatnapWrappingView(new JsonView(), "modelName").getModelName());
    }

    @Test
    public void getContentTypeReturnsContentTypeOfWrappedView() {
        assertEquals("application/json", new CatnapWrappingView(new JsonView()).getContentType());
    }

    @Test
    public void getEncodingReturnsCharacterEncodingOfWrappedView() {
        assertEquals("UTF-8", new CatnapWrappingView(new JsonView()).getCharacterEncoding());
    }

    @Test
    public void getWrappedViewReturnsWrappedView() {
        assertTrue(new CatnapWrappingView(new JsonView()).getWrappedView() instanceof JsonView);
    }
}
