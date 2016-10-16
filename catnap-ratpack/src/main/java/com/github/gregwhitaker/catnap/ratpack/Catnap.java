package com.github.gregwhitaker.catnap.ratpack;

import com.github.gregwhitaker.catnap.ratpack.render.CatnapRender;
import com.github.gregwhitaker.catnap.ratpack.render.DefaultCatnapRender;

public abstract class Catnap {

    private Catnap() {

    }

    public static CatnapRender catnap(Object obj) {
        return new DefaultCatnapRender(obj);
    }
}
