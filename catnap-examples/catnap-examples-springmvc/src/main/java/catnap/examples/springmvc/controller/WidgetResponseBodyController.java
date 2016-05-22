package catnap.examples.springmvc.controller;

import catnap.examples.springmvc.core.exception.WidgetNotFoundException;
import catnap.examples.springmvc.model.Widget;
import catnap.examples.springmvc.service.WidgetService;
import com.github.gregwhitaker.catnap.core.annotation.CatnapDisabled;
import com.github.gregwhitaker.catnap.springmvc.annotation.CatnapResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "widgetsrb")
public class WidgetResponseBodyController {

    @Autowired
    private WidgetService service;

    /**
     * This method demonstrates returning a list of widgets and rendering the response with Catnap.
     *
     * @return a list of {@link catnap.examples.springmvc.model.Widget}
     */
    @RequestMapping(method = RequestMethod.GET)
    @CatnapResponseBody
    public List<Widget> getAllWidgets() {
        return service.getWidgets();
    }

    /**
     * This method demonstrates return a single widget and rendering the response with Catnap.
     *
     * @param id widget id
     * @return a {@link catnap.examples.springmvc.model.Widget}
     */
    @RequestMapping(method = RequestMethod.GET,
                    value = "/{id}")
    @CatnapResponseBody
    public Widget getWidget(@PathVariable("id") String id) {
        Widget widget = service.getWidget(id);

        if (widget == null) {
            throw new WidgetNotFoundException();
        }

        return widget;
    }

    /**
     * This method demonstrates returning a single widget with Catnap functionality disabled.  If you supply
     * a Catnap query to this method it will be ignored.
     *
     * @param id widget id
     * @return a {@link catnap.examples.springmvc.model.Widget}
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/{id}/nocatnap")
    @CatnapDisabled
    @CatnapResponseBody
    public Widget getWidgetsWithCatnapDisabled(@PathVariable("id") String id) {
        Widget widget = service.getWidget(id);

        if (widget == null) {
            throw new WidgetNotFoundException();
        }

        return widget;
    }
}
