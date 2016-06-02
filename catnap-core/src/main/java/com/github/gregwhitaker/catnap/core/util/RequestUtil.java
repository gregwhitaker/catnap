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

package com.github.gregwhitaker.catnap.core.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility that provides methods for modifying http requests.
 */
public class RequestUtil {
    private static final String CATNAP_DISABLED = "catnapDisabled";

    /**
     * Disables Catnap query evaluation for the specified request.
     *
     * @param request the request for which to disable Catnap query evaluation
     */
    public static void disableCatnap(final HttpServletRequest request) {
        request.setAttribute(CATNAP_DISABLED, true);
    }

    /**
     * Returns a boolean indicating whether or not Catnap query evaluation has been disabled for the
     * specified request.
     *
     * @param request
     * @return
     */
    public static boolean isCatnapDisabled(final HttpServletRequest request) {
        return (request.getAttribute(CATNAP_DISABLED) != null) ? (Boolean) request.getAttribute(CATNAP_DISABLED) : false;
    }

    /**
     * Checks if there is a parameter in the request query string with the supplied name.
     *
     * @param parameterName name to check for
     * @return <code>true</code> if the parameter exists in the query string; otherwise <code>false</code>
     */
    public static boolean containsParameter(final HttpServletRequest request, final String parameterName) {
        return request.getParameter(parameterName) != null;
    }
}
