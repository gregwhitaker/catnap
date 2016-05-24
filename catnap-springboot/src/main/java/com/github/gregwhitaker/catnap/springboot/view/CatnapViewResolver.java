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

package com.github.gregwhitaker.catnap.springboot.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CatnapViewResolver implements ViewResolver
{
    private static final String ACCEPT_HEADER = "Accept";
    private static final Logger logger = LoggerFactory.getLogger(CatnapViewResolver.class);

    private Map<String, CatnapWrappingView> views = new LinkedHashMap<String, CatnapWrappingView>();

    public CatnapViewResolver() {
        //No views have been configured for resolution
    }

    public CatnapViewResolver(List<CatnapWrappingView> views) {
        setViews(views);
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        if(views.isEmpty()) {
            logger.warn("No views configured for view resolver [{}]", getClass().getName());
        }

        View view = null;

        if(view == null) {
            view = resolveViewWithHrefSuffix(viewName, locale);
        }

        if(view == null) {
            view = resolveViewWithAcceptHeader(viewName, locale);
        }

        return view;
    }

    private View resolveViewWithHrefSuffix(String viewName, Locale locale) {
        for(Map.Entry<String, CatnapWrappingView> entry : views.entrySet()) {
            if(viewName.endsWith(entry.getKey())) {
                logger.debug("Resolved view [{}] with href suffix [{}]", viewName, entry.getKey());
                return entry.getValue();
            }
        }

        return null;
    }

    private View resolveViewWithAcceptHeader(String viewName, Locale locale) {
        HttpServletRequest request = (HttpServletRequest) RequestContextHolder.currentRequestAttributes()
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        String accept = request.getHeader(ACCEPT_HEADER);

        for(Map.Entry<String, CatnapWrappingView> entry : views.entrySet()) {
            if(entry.getValue().getContentType().equals(accept)) {
                logger.debug("Resolved view [{}] with Accept header [{}]", viewName, entry.getValue().getContentType());
                return entry.getValue();
            }
        }

        return null;
    }

    public void setViews(List<CatnapWrappingView> views) {
        if(!CollectionUtils.isEmpty(views)) {
            for(CatnapWrappingView view : views) {
                this.views.put(view.getWrappedView().getHrefSuffix(), view);
            }
        }
    }
}
