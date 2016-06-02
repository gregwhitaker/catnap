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

package com.github.gregwhitaker.catnap.springmvc.interceptor;

import com.github.gregwhitaker.catnap.core.exception.ViewRenderException;
import com.github.gregwhitaker.catnap.springmvc.annotation.CatnapResponseBody;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.Introspector;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class CatnapResponseBodyHandlerInterceptor extends HandlerInterceptorAdapter {
    public static final String MODEL_NAME = "catnapModel";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        HandlerMethod method = (HandlerMethod) handler;

        if (method.getMethodAnnotation(ResponseBody.class) == null) {
            CatnapResponseBody annotation = method.getMethodAnnotation(CatnapResponseBody.class);

            if (annotation != null) {
                String modelName = modelName(annotation, method);

                if (modelAndView != null) {
                    //Transfer the model to a well known key so that we can retrieve it in the view.
                    Object model = modelAndView.getModel().get(modelName);
                    modelAndView.getModel().put(MODEL_NAME, model);
                }
            }
        }
    }

    private String modelName(CatnapResponseBody annotation, HandlerMethod method) {
        if (!StringUtils.isEmpty(annotation.value())) {
            return annotation.value();
        } else {
            if (List.class.isAssignableFrom(method.getReturnType().getParameterType())) {
                String paramType = method.getReturnType().getGenericParameterType().getTypeName();
                paramType = paramType.substring(paramType.lastIndexOf(".") + 1, paramType.length() - 1);
                return Introspector.decapitalize(paramType) + "List";
            }

            if (Map.class.isAssignableFrom(method.getReturnType().getParameterType())) {
                throw new ViewRenderException("Map is not a supported return type for methods annotated with " +
                        "@CatnapResponseBody.  Please return an object or a list of objects.  If you wish to return " +
                        "a map then you must use Catnap's standard model and view handling by removing the " +
                        "@CatnapResponseBody annotation.");
            }

            return Introspector.decapitalize(method.getReturnType().getParameterType().getSimpleName());
        }
    }
}
