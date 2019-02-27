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

package com.github.gregwhitaker.catnap.core.query.processor;

import com.github.gregwhitaker.catnap.core.query.model.CatnapCachedQuery;
import com.github.gregwhitaker.catnap.core.query.model.CatnapQuery;
import com.github.gregwhitaker.catnap.core.query.model.Query;

/**
 *
 */
public class QueryProcessorFactory {

    /**
     * @param query
     * @param instanceClazz
     * @return
     */
    public static QueryProcessor createQueryProcessor(Query query, Class<?> instanceClazz) {
        if (query == null) {
            return new NullQueryProcessor();
        }

        if (query instanceof CatnapQuery) {
            return new CatnapQueryProcessor();
        }

        if (query instanceof CatnapCachedQuery) {
            return new CachingQueryProcessor();
        }

        throw new UnsupportedOperationException(String.format("Query type [%s] is not supported", query.getClass().getName()));
    }
}
