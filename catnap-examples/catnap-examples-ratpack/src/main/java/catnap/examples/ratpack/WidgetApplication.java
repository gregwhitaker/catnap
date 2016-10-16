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

package catnap.examples.ratpack;

import catnap.examples.ratpack.handler.WidgetHandler;
import catnap.examples.ratpack.handler.WidgetsHandler;
import com.github.gregwhitaker.catnap.ratpack.CatnapRenderer;
import ratpack.guice.Guice;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

public class WidgetApplication {

    public static void main(String... args) throws Exception {
        RatpackServer.start(c -> c
                .serverConfig(ServerConfig.builder().port(8080).build())
                .registry(Guice.registry(b -> b
                        .module(WidgetModule.class)
                        .bind(CatnapRenderer.class))
                )
                .handlers(chain -> chain
                        .get("widgets", WidgetsHandler.class)
                        .get("widgets/:id", WidgetHandler.class)));
    }
}
