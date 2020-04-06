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
 */

package org.elipcero.carisa.administration.service;

import lombok.NonNull;
import org.elipcero.carisa.administration.domain.DynamicObjectPrototype;
import org.elipcero.carisa.administration.domain.DynamicObjectPrototypeProperty;
import org.elipcero.carisa.administration.domain.Plugin;
import org.elipcero.carisa.administration.domain.PluginType;
import org.elipcero.carisa.administration.repository.DynamicObjectPrototypeRepository;
import org.elipcero.carisa.administration.service.support.DynamicObjectPrototypeService;
import org.elipcero.carisa.core.reactive.data.EmbeddedDependencyRelation;
import org.elipcero.carisa.core.reactive.data.MultiplyDependencyRelation;

/**
 * Operations for plugin instance
 * @see DynamicObjectPrototypeService
 *
 * @author David Suárez
 */

public class PluginDynamicPrototypeService extends DynamicObjectPrototypeService<Plugin> {

    public PluginDynamicPrototypeService(
         @NonNull DynamicObjectPrototypeRepository dynamicObjectRepository,
         @NonNull MultiplyDependencyRelation<PluginType, DynamicObjectPrototype, Plugin> relation,
         @NonNull EmbeddedDependencyRelation<DynamicObjectPrototypeProperty> propertyRelation) {

        super(dynamicObjectRepository, relation, propertyRelation);
    }
}
