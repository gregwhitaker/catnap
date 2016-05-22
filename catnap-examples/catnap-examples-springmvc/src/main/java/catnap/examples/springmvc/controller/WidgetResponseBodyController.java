package catnap.examples.springmvc.controller;

import com.github.gregwhitaker.catnap.springmvc.annotation.CatnapResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "widgetsrb")
public class WidgetResponseBodyController {

    @RequestMapping(method = RequestMethod.GET)
    @CatnapResponseBody
    public List getAllWidgets() {
        return null;
    }
}
