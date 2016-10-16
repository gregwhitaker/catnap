package com.github.gregwhitaker.catnap.ratpack;

import ratpack.handling.Context;
import ratpack.render.RendererSupport;

public class CatnapRenderer extends RendererSupport<CatnapRender> {

    @Override
    public void render(Context ctx, CatnapRender catnapRender) throws Exception {
        ctx.getResponse().send("This is doing something");
    }
}
