package com.github.gregwhitaker.catnap.ratpack;

public abstract class Catnap {

    private Catnap() {

    }

    public static CatnapRender catnap(Object obj) {
        return new DefaultCatnapRender(obj);
    }
}
