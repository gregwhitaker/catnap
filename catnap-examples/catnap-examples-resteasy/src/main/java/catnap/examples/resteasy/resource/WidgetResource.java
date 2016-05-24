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
