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

package catnap.examples.springboot.controller;

import catnap.examples.springboot.core.exception.WidgetNotFoundException;
import catnap.examples.springboot.model.Widget;
import catnap.examples.springboot.service.WidgetService;
import com.github.gregwhitaker.catnap.core.annotation.CatnapDisabled;
import com.github.gregwhitaker.catnap.springmvc.annotation.CatnapResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/widgets")
public class WidgetController {

    @Autowired
    private WidgetService service;

    /**
     * This method demonstrates returning a list of widgets and rendering the response with Catnap.
     *
     * @return a list of {@link catnap.examples.springboot.model.Widget}
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
     * @return a {@link catnap.examples.springboot.model.Widget}
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
     * @return a {@link catnap.examples.springboot.model.Widget}
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
