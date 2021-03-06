/*
 * Copyright 2019-2022 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.elipcero.carisa.administration.convert.type;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * Dynamic object instance converter factory
 * @see DataEngineValueConverter
 *
 * @author David Suárez
 */
@RequiredArgsConstructor
public class ValueConverterFactory<TConvert> {

    @NonNull
    private Map<Integer, TConvert> converter;

    /**
     * Get specific converter by type
     * @param type the value type of the dynamic object prototype property
     * @return
     */
    public TConvert get(int type) {
        return this.converter.get(type);
    }
}
