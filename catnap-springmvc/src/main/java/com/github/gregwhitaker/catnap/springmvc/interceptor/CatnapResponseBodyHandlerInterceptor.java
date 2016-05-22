package com.github.gregwhitaker.catnap.springmvc.interceptor;

import com.github.gregwhitaker.catnap.springmvc.annotation.CatnapResponseBody;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.Introspector;

/**
 *
 */
public class CatnapResponseBodyHandlerInterceptor extends HandlerInterceptorAdapter {
    public static final String MODEL_NAME = "catnapModel";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        HandlerMethod method = (HandlerMethod) handler;

        if(method.getMethodAnnotation(ResponseBody.class) == null) {
            CatnapResponseBody annotation = method.getMethodAnnotation(CatnapResponseBody.class);

            if(annotation != null) {
                String modelName = modelName(annotation, method);

                if(modelAndView != null) {
                    //Transfer the model to a well known key so that we can retrieve it in the view.
                    Object model = modelAndView.getModel().get(modelName);
                    modelAndView.getModel().put(MODEL_NAME, model);
                }
            }
        }
    }

    private String modelName(CatnapResponseBody annotation, HandlerMethod method) {
        if(!StringUtils.isEmpty(annotation.value())) {
            return annotation.value();
        } else {
            return Introspector.decapitalize(method.getReturnType().getParameterType().getSimpleName());
        }
    }
}
