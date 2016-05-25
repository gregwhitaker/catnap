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
import com.github.gregwhitaker.catnap.core.model.builder.DefaultModelBuilder;
import com.github.gregwhitaker.catnap.core.query.builder.SimpleQueryBuilder;
import com.github.gregwhitaker.catnap.core.view.CatnapView;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CatnapViewResolverTest {

    static class JsonView extends CatnapView {

        public JsonView() {
            super(new SimpleQueryBuilder(), new DefaultModelBuilder());
        }

        @Override
        protected void render(Object value, OutputStream outputStream, CatnapContext context) throws Exception {}

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
    }

    public static CatnapViewResolver viewResolver;

    @BeforeClass
    public static void setup() {
        List<CatnapWrappingView> views = new ArrayList<>(2);
        views.add(new CatnapWrappingView(new JsonView()));

        viewResolver = new CatnapViewResolver(views);
    }

    @Before
    public void init() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    public void resolveViewByHrefSuffix() throws Exception {
        CatnapWrappingView view = (CatnapWrappingView) viewResolver.resolveViewName("/view.json", Locale.US);
        assertTrue(view.getWrappedView() instanceof JsonView);
    }

    @Test
    public void resolveViewByAcceptHeader() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept", "application/json");

        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        requestAttributes.setAttribute(RequestAttributes.REFERENCE_REQUEST, request, RequestAttributes.SCOPE_REQUEST);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        CatnapWrappingView view = (CatnapWrappingView) viewResolver.resolveViewName("/view", Locale.US);
        assertTrue(view.getWrappedView() instanceof JsonView);
    }

    @Test
    public void resolveViewByInvalidHrefSuffixReturnsNull() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        requestAttributes.setAttribute(RequestAttributes.REFERENCE_REQUEST, request, RequestAttributes.SCOPE_REQUEST);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        assertNull(viewResolver.resolveViewName("/view.xml", Locale.US));
    }

    @Test
    public void resolveViewByInvalidAcceptHeaderReturnsNull() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept", "application/xml");

        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        requestAttributes.setAttribute(RequestAttributes.REFERENCE_REQUEST, request, RequestAttributes.SCOPE_REQUEST);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        assertNull(viewResolver.resolveViewName("/view", Locale.US));
    }

    @Test
    public void noConfiguredViewsReturnsNull() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        requestAttributes.setAttribute(RequestAttributes.REFERENCE_REQUEST, request, RequestAttributes.SCOPE_REQUEST);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        assertNull(new CatnapViewResolver().resolveViewName("/view.json", Locale.US));
    }
}
