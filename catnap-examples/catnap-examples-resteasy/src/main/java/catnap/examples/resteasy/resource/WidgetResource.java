package catnap.examples.resteasy.resource;

import catnap.examples.resteasy.model.Widget;
import catnap.examples.resteasy.service.WidgetService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("widgets")
@Produces({"application/json", "application/x-javascript"})
public class WidgetResource {

    private WidgetService service = new WidgetService();

    @GET
    public List<Widget> getWidgets() {
        return service.getWidgets();
    }

    @GET
    @Path("/{id}")
    public Widget getWidget(@PathParam("id") String id) {
        return service.getWidget(id);
    }
}
