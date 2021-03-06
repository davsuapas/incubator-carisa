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

package org.elipcero.carisa.core.reactive.data;

import org.elipcero.carisa.core.data.EntityDataState;
import org.elipcero.carisa.core.data.Relation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface DependencyRelation<TRelation extends Relation> {

    /**
     * Get relation by identifier
     * @param id identifier to found
     * @return relations
     */
    Mono<TRelation> getById(final Map<String, Object> id);

    /**
     * Exists the relation by identifier
     * @param id identifier to found
     * @return if exists return true
     */
    Mono<Boolean> existsById(final Map<String, Object> id);

    /**
     * It update or create the relation depending if it exists
     * @param relation relation to update
     * @param onUpdateChange If exists it update changes
     * @param onCreatedEntity If not exist relation is created
     * @return relation updated or created
     */
    Mono<EntityDataState<TRelation>> updateOrCreate(
            final TRelation relation,
            final Consumer<TRelation> onUpdateChange, final Supplier<Mono<TRelation>> onCreatedEntity);

    /**
     * Get Children by parent identifier
     * @param parentId the parent identifier
     * @return the children
     */
    Flux<TRelation> getRelationsByParent(UUID parentId);
}
