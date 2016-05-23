package catnap.examples.resteasy;

import catnap.examples.resteasy.resource.WidgetResource;
import com.github.gregwhitaker.catnap.jaxrs.view.JsonCatnapMessageBodyWriter;
import com.github.gregwhitaker.catnap.jaxrs.view.JsonpCatnapMessageBodyWriter;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class WidgetApplication extends Application {

    private final Set<Object> singletons = new HashSet<>();

    public WidgetApplication() {
        singletons.add(new WidgetResource());

        //Add Catnap MessageBodyWriters (Note: These can also be auto-detected via their @Provider annotations)
        singletons.add(new JsonCatnapMessageBodyWriter());
        singletons.add(new JsonpCatnapMessageBodyWriter());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
