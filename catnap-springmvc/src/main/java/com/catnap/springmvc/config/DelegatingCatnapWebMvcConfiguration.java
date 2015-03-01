package com.catnap.springmvc.config;

import com.catnap.core.view.JsonCatnapView;
import com.catnap.core.view.JsonpCatnapView;
import com.catnap.core.view.XmlCatnapView;
import com.catnap.springmvc.interceptor.CatnapDisabledHandlerInterceptor;
import com.catnap.springmvc.interceptor.CatnapResponseBodyHandlerInterceptor;
import com.catnap.springmvc.view.CatnapViewResolver;
import com.catnap.springmvc.view.CatnapWrappingView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
@Configuration
public class DelegatingCatnapWebMvcConfiguration extends DelegatingWebMvcConfiguration
{
    @Override
    protected void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new CatnapDisabledHandlerInterceptor());
        registry.addInterceptor(new CatnapResponseBodyHandlerInterceptor());

        super.addInterceptors(registry);
    }

    @Override
    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer)
    {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
        configurer.favorPathExtension(true);
        configurer.ignoreAcceptHeader(false);
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
        configurer.mediaType("jsonp", new MediaType("application", "x-javascript"));
        configurer.mediaType("xml", MediaType.APPLICATION_XML);

        super.configureContentNegotiation(configurer);
    }

    @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver()
    {
        List<View> defaultViews = new ArrayList<View>(2);
        defaultViews.add(jsonCatnapSpringView());
        defaultViews.add(jsonpCatnapSpringView());
        defaultViews.add(xmlCatnapSpringView());

        List<CatnapWrappingView> catnapViews = new ArrayList<CatnapWrappingView>(2);
        catnapViews.add(jsonCatnapSpringView());
        catnapViews.add(jsonpCatnapSpringView());
        catnapViews.add(xmlCatnapSpringView());

        CatnapViewResolver catnapViewResolver = new CatnapViewResolver();
        catnapViewResolver.setViews(catnapViews);

        List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>(1);
        viewResolvers.add(catnapViewResolver);

        ContentNegotiatingViewResolver cnvr = new ContentNegotiatingViewResolver();
        cnvr.setContentNegotiationManager(mvcContentNegotiationManager());
        cnvr.setOrder(1);
        cnvr.setDefaultViews(defaultViews);
        cnvr.setViewResolvers(viewResolvers);

        return cnvr;
    }

    @Bean
    public CatnapWrappingView jsonCatnapSpringView()
    {
        return new CatnapWrappingView(new JsonCatnapView.Builder().build());
    }

    @Bean
    public CatnapWrappingView jsonpCatnapSpringView()
    {
        return new CatnapWrappingView(new JsonpCatnapView.Builder().build());
    }

    @Bean
    public CatnapWrappingView xmlCatnapSpringView()
    {
        return new CatnapWrappingView(new XmlCatnapView.Builder().build());
    }
}
