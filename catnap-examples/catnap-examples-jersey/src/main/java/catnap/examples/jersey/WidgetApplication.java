package catnap.examples.jersey;

import catnap.examples.jersey.resource.WidgetResource;
import com.github.gregwhitaker.catnap.jaxrs.view.JsonCatnapMessageBodyWriter;
import com.github.gregwhitaker.catnap.jaxrs.view.JsonpCatnapMessageBodyWriter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class WidgetApplication extends Application {
    private final Set<Object> singletons = new HashSet<>();

    public WidgetApplication() {
        singletons.add(new WidgetResource());

        singletons.add(new JsonCatnapMessageBodyWriter());
        singletons.add(new JsonpCatnapMessageBodyWriter());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
