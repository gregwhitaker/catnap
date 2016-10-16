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

package catnap.examples.ratpack.handler;

import catnap.examples.ratpack.service.WidgetService;
import com.github.gregwhitaker.catnap.ratpack.Catnap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import ratpack.handling.Context;
import ratpack.handling.Handler;

@Singleton
public class WidgetsHandler implements Handler {

    private final WidgetService service;

    @Inject
    public WidgetsHandler(WidgetService service) {
        this.service = service;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        ctx.render(Catnap.catnap(service.getWidgets()));
    }
}
