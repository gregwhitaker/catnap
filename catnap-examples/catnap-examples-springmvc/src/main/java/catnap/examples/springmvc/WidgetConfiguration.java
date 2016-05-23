package catnap.examples.springmvc;

import com.github.gregwhitaker.catnap.core.view.JsonCatnapView;
import com.github.gregwhitaker.catnap.core.view.JsonpCatnapView;
import com.github.gregwhitaker.catnap.springmvc.interceptor.CatnapDisabledHandlerInterceptor;
import com.github.gregwhitaker.catnap.springmvc.interceptor.CatnapResponseBodyHandlerInterceptor;
import com.github.gregwhitaker.catnap.springmvc.view.CatnapViewResolver;
import com.github.gregwhitaker.catnap.springmvc.view.CatnapWrappingView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan(basePackages = "catnap.examples.springmvc")
public class WidgetConfiguration extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);

        registry.addInterceptor(new CatnapDisabledHandlerInterceptor());
        registry.addInterceptor(new CatnapResponseBodyHandlerInterceptor());
    }

    @Override
    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
        configurer.favorPathExtension(true);
        configurer.ignoreAcceptHeader(false);
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
        configurer.mediaType("jsonp", new MediaType("application", "x-javascript"));

        super.configureContentNegotiation(configurer);
    }

    @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {
        List<View> defaultViews = new ArrayList<>(2);
        defaultViews.add(jsonCatnapSpringView());
        defaultViews.add(jsonpCatnapSpringView());

        List<CatnapWrappingView> catnapViews = new ArrayList<>(2);
        catnapViews.add(jsonCatnapSpringView());
        catnapViews.add(jsonpCatnapSpringView());

        CatnapViewResolver catnapViewResolver = new CatnapViewResolver();
        catnapViewResolver.setViews(catnapViews);

        List<ViewResolver> viewResolvers = new ArrayList<>(1);
        viewResolvers.add(catnapViewResolver);

        ContentNegotiatingViewResolver cnvr = new ContentNegotiatingViewResolver();
        cnvr.setContentNegotiationManager(mvcContentNegotiationManager());
        cnvr.setOrder(1);
        cnvr.setDefaultViews(defaultViews);
        cnvr.setViewResolvers(viewResolvers);

        return cnvr;
    }

    @Bean
    public CatnapWrappingView jsonCatnapSpringView() {
        return new CatnapWrappingView(new JsonCatnapView.Builder()
                .withAllowNoContentResponse(false)
                .build());
    }

    @Bean
    public CatnapWrappingView jsonpCatnapSpringView() {
        return new CatnapWrappingView(new JsonpCatnapView.Builder().build());
    }
}
