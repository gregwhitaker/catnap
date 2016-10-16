package com.github.gregwhitaker.catnap.ratpack.render;

import com.github.gregwhitaker.catnap.core.view.JsonCatnapView;
import ratpack.handling.Context;
import ratpack.render.RendererSupport;

public class CatnapRenderer extends RendererSupport<CatnapRender> {

    @Override
    public void render(Context ctx, CatnapRender catnapRender) throws Exception {
        JsonCatnapView jsonCatnapView = new JsonCatnapView.Builder().build();
        ctx.getResponse().send("This is doing something");
    }
}
