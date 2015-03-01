package com.catnap.springmvc.interceptor;

import com.catnap.core.annotation.CatnapDisabled;
import com.catnap.core.util.RequestUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gwhit7
 */
public class CatnapDisabledHandlerInterceptor extends HandlerInterceptorAdapter
{
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception
    {
        HandlerMethod method = (HandlerMethod) handler;

        if(method.getMethodAnnotation(CatnapDisabled.class) != null)
        {
            RequestUtil.disableCatnap(request);
        }
    }
}
