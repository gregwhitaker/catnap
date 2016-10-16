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

package catnap.examples.ratpack.service;

import catnap.examples.ratpack.model.Widget;
import catnap.examples.ratpack.model.WidgetImage;
import catnap.examples.ratpack.model.WidgetPrices;
import com.google.inject.Singleton;

import java.util.*;

@Singleton
public class WidgetService {
    private final Map<String, Widget> widgets = new HashMap<>(3);

    /**
     * Initializes this instance of the {@link WidgetService}.
     */
    public WidgetService() {
        widgets.put("1", createWidget1());
        widgets.put("2", createWidget2());
        widgets.put("3", createWidget3());
    }

    /**
     * Gets all widgets in the system.
     *
     * @return a list of all {@link Widget}s in the system.
     */
    public List<Widget> getWidgets() {
        List<Widget> widgetList = new ArrayList<>();
        widgetList.addAll(widgets.values());
        return widgetList;
    }

    /**
     * Gets a widget by id.
     *
     * @param id widget id
     * @return a {@link Widget} if one exists with the specified id; otherwise <code>null</code>
     */
    public Widget getWidget(String id) {
        return widgets.get(id);
    }

    /**
     * Creates a widget that is on sale.
     *
     * @return a {@link Widget}
     */
    private Widget createWidget1() {
        WidgetPrices prices = new WidgetPrices();
        prices.setList(35.99);
        prices.setSale(30.00);
        prices.setMsrp(39.99);
        prices.setFormattedList("$35.99");
        prices.setFormattedSale("$30.00");
        prices.setFormattedMSRP("$39.99");

        WidgetImage image1 = new WidgetImage();
        image1.setSortOrder(1);
        image1.setUrl("http://gregwhitaker.github.com/catnap/widgets/1/images/large.png");
        image1.setAlt("Widget 1 Large");
        image1.setType("large");

        WidgetImage image2 = new WidgetImage();
        image2.setSortOrder(2);
        image2.setUrl("http://gregwhitaker.github.com/catnap/widgets/1/images/medium.png");
        image2.setAlt("Widget 1 Medium");
        image2.setType("medium");

        WidgetImage image3 = new WidgetImage();
        image3.setSortOrder(3);
        image3.setUrl("http://gregwhitaker.github.com/catnap/widgets/1/images/thumbnail.png");
        image3.setAlt("Widget 1 Thumbnail");
        image3.setType("thumbnail");

        ArrayList<WidgetImage> images = new ArrayList<>();
        images.add(image1);
        images.add(image2);
        images.add(image3);

        Widget widget = new Widget();
        widget.setId("1");
        widget.setName("Widget 1");
        widget.setLongName("Catnap Example Widget 1");
        widget.setActive(true);
        widget.setPrices(prices);
        widget.setImages(images);

        return widget;
    }

    /**
     * Creates a widget that has two images.
     *
     * @return a {@link Widget}
     */
    private Widget createWidget2() {
        WidgetPrices prices = new WidgetPrices();
        prices.setList(110.00);
        prices.setSale(110.00);
        prices.setMsrp(150.00);
        prices.setFormattedList("$110.00");
        prices.setFormattedSale("$110.00");
        prices.setFormattedMSRP("$150.00");

        WidgetImage image1 = new WidgetImage();
        image1.setSortOrder(1);
        image1.setUrl("http://gregwhitaker.github.com/catnap/widgets/2/images/large.png");
        image1.setAlt("Widget 2 Large");
        image1.setType("large");

        WidgetImage image2 = new WidgetImage();
        image2.setSortOrder(2);
        image2.setUrl("http://gregwhitaker.github.com/catnap/widgets/2/images/thumbnail.png");
        image2.setAlt("Widget 2 Thumbnail");
        image2.setType("thumbnail");

        Widget widget = new Widget();
        widget.setId("2");
        widget.setName("Widget 2");
        widget.setLongName("Catnap Example Widget 2");
        widget.setActive(true);
        widget.setPrices(prices);
        widget.setImages(Arrays.asList(image1, image2));

        return widget;
    }

    /**
     * Creates a widget that is inactive.
     *
     * @return a {@link Widget}
     */
    private Widget createWidget3() {
        WidgetPrices prices = new WidgetPrices();
        prices.setList(9.99);
        prices.setSale(9.99);
        prices.setMsrp(10.00);
        prices.setFormattedList("$9.99");
        prices.setFormattedSale("$9.99");
        prices.setFormattedMSRP("$10.00");

        WidgetImage image1 = new WidgetImage();
        image1.setSortOrder(1);
        image1.setUrl("http://gregwhitaker.github.com/catnap/widgets/3/images/large.png");
        image1.setAlt("Widget 3 Large");
        image1.setType("large");

        WidgetImage image2 = new WidgetImage();
        image2.setSortOrder(2);
        image2.setUrl("http://gregwhitaker.github.com/catnap/widgets/3/images/medium.png");
        image2.setAlt("Widget 3 Medium");
        image2.setType("medium");

        WidgetImage image3 = new WidgetImage();
        image3.setSortOrder(3);
        image3.setUrl("http://gregwhitaker.github.com/catnap/widgets/3/images/thumbnail.png");
        image3.setAlt("Widget 3 Thumbnail");
        image3.setType("thumbnail");

        Widget widget = new Widget();
        widget.setId("3");
        widget.setName("Widget 3");
        widget.setLongName("Catnap Example Widget 3");
        widget.setActive(false);
        widget.setPrices(prices);
        widget.setImages(Arrays.asList(image1, image2, image3));

        return widget;
    }
}
