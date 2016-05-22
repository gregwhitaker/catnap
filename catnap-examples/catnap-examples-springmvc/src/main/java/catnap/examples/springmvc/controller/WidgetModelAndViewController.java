package catnap.examples.springmvc.controller;

import catnap.examples.springmvc.core.exception.WidgetNotFoundException;
import catnap.examples.springmvc.model.Widget;
import catnap.examples.springmvc.service.WidgetService;
import com.github.gregwhitaker.catnap.core.annotation.CatnapDisabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * This controller demonstrates using Catnap with controllers that return ModelAndViews.
 */
@Controller
@RequestMapping(value = "widgetsmvc")
public class WidgetModelAndViewController {

    @Autowired
    private WidgetService service;

    /**
     * This method demonstrates returning a list of widgets and rendering the response with Catnap.
     *
     * @return a {@link ModelAndView} containing a list of {@link catnap.examples.springmvc.model.Widget}
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllWidgets(HttpServletRequest request) {
        return new ModelAndView(request.getPathInfo(), "result", service.getWidgets());
    }

    /**
     * This method demonstrates return a single widget and rendering the response with Catnap.
     *
     * @param id widget id
     * @return a {@link ModelAndView} containing a {@link catnap.examples.springmvc.model.Widget}
     */
    @RequestMapping(method = RequestMethod.GET,
                    value = "/{id}")
    public ModelAndView getWidget(@PathVariable("id") String id, HttpServletRequest request) {
        Widget widget = service.getWidget(id);

        if (widget == null) {
            throw new WidgetNotFoundException();
        }

        return new ModelAndView(request.getPathInfo(), "result", service.getWidget(id));
    }

    /**
     * This method demonstrates returning a single widget with Catnap functionality disabled.  If you supply
     * a Catnap query to this method it will be ignored.
     *
     * @param id widget id
     * @return a {@link ModelAndView} containing a {@link catnap.examples.springmvc.model.Widget} without support
     * for partial response rendering
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/{id}/nocatnap")
    @CatnapDisabled
    public ModelAndView getWidgetsWithCatnapDisabled(@PathVariable("id") String id, HttpServletRequest request) {
        Widget widget = service.getWidget(id);

        if (widget == null) {
            throw new WidgetNotFoundException();
        }

        return new ModelAndView(request.getPathInfo(), "result", service.getWidget(id));
    }
}
