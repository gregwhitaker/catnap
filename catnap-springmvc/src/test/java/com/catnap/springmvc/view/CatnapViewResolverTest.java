package com.catnap.springmvc.view;

import static org.junit.Assert.*;
import com.catnap.core.context.CatnapContext;
import com.catnap.core.model.builder.DefaultModelBuilder;
import com.catnap.core.model.builder.ModelBuilder;
import com.catnap.core.query.builder.QueryBuilder;
import com.catnap.core.query.builder.SimpleQueryBuilder;
import com.catnap.core.view.CatnapView;
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

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public class CatnapViewResolverTest
{
    static class JsonView extends CatnapView
    {
        public JsonView()
        {
            super(new SimpleQueryBuilder(), new DefaultModelBuilder());
        }

        @Override
        protected void render(Object value, OutputStream outputStream, CatnapContext context) throws Exception {}

        @Override
        protected void renderError(Object value, OutputStream outputStream, CatnapContext context) throws Exception {}

        @Override
        public String getContentType()
        {
            return "application/json";
        }

        @Override
        public String getHrefSuffix()
        {
            return ".json";
        }
    }

    public static CatnapViewResolver viewResolver;

    @BeforeClass
    public static void setup()
    {
        List<CatnapWrappingView> views = new ArrayList<CatnapWrappingView>(2);
        views.add(new CatnapWrappingView(new JsonView()));

        viewResolver = new CatnapViewResolver(views);
    }

    @Before
    public void init()
    {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    public void resolveViewByHrefSuffix() throws Exception
    {
        CatnapWrappingView view = (CatnapWrappingView) viewResolver.resolveViewName("/view.json", Locale.US);
        assertTrue(view.getWrappedView() instanceof JsonView);
    }

    @Test
    public void resolveViewByAcceptHeader() throws Exception
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept", "application/json");

        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        requestAttributes.setAttribute(RequestAttributes.REFERENCE_REQUEST, request, RequestAttributes.SCOPE_REQUEST);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        CatnapWrappingView view = (CatnapWrappingView) viewResolver.resolveViewName("/view", Locale.US);
        assertTrue(view.getWrappedView() instanceof JsonView);
    }

    @Test
    public void resolveViewByInvalidHrefSuffixReturnsNull() throws Exception
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        requestAttributes.setAttribute(RequestAttributes.REFERENCE_REQUEST, request, RequestAttributes.SCOPE_REQUEST);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        assertNull(viewResolver.resolveViewName("/view.xml", Locale.US));
    }

    @Test
    public void resolveViewByInvalidAcceptHeaderReturnsNull() throws Exception
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept", "application/xml");

        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        requestAttributes.setAttribute(RequestAttributes.REFERENCE_REQUEST, request, RequestAttributes.SCOPE_REQUEST);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        assertNull(viewResolver.resolveViewName("/view", Locale.US));
    }

    @Test
    public void noConfiguredViewsReturnsNull() throws Exception
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        requestAttributes.setAttribute(RequestAttributes.REFERENCE_REQUEST, request, RequestAttributes.SCOPE_REQUEST);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        assertNull(new CatnapViewResolver().resolveViewName("/view.json", Locale.US));
    }
}
