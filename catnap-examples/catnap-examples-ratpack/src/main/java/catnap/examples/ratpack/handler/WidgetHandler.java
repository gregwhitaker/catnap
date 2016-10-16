package catnap.examples.ratpack.handler;

import catnap.examples.ratpack.service.WidgetService;
import com.google.inject.Inject;
import ratpack.handling.Context;
import ratpack.handling.Handler;

public class WidgetHandler implements Handler {

    @Inject
    private WidgetService service;

    @Override
    public void handle(Context ctx) throws Exception {

    }
}
