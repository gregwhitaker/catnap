package com.github.gregwhitaker.catnap.core.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility that provides methods for modifying http requests.
 */
public class RequestUtil {
    private static final String CATNAP_DISABLED = "catnapDisabled";

    /**
     * Disables Catnap query evaluation for the specified request.
     * @param request the request for which to disable Catnap query evaluation
     */
    public static void disableCatnap(final HttpServletRequest request) {
        request.setAttribute(CATNAP_DISABLED, true);
    }

    /**
     * Returns a boolean indicating whether or not Catnap query evaluation has been disabled for the
     * specified request.
     * @param request
     * @return
     */
    public static boolean isCatnapDisabled(final HttpServletRequest request) {
        return (request.getAttribute(CATNAP_DISABLED) != null) ? (Boolean) request.getAttribute(CATNAP_DISABLED) : false;
    }

    /**
     * Checks if there is a parameter in the request query string with the supplied name.
     * @param parameterName name to check for
     * @return <code>true</code> if the parameter exists in the query string; otherwise <code>false</code>
     */
    public static boolean containsParameter(final HttpServletRequest request, final String parameterName) {
        return request.getParameter(parameterName) != null;
    }
}
