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

import org.elipcero.carisa.administration.domain.Ente;
import org.elipcero.carisa.administration.domain.Space;
import org.elipcero.carisa.administration.projection.ParentChildName;
import org.elipcero.carisa.core.data.EntityDataState;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Operations for space
 *
 * @author David Suárez
 */
public interface SpaceService {

    /**
     * Get space by id
     * @param id
     * @return space found
     */
    Mono<Space> getById(UUID id);

    /**
     * Create the space and insert the space into the instance. It's not done in the same transaction.
     * @param space space for creating
     * @return space created
     */
    Mono<Space> create(Space space);

    /**
     * Update or create the space. If the id exits is updated
     * otherwise is created. The instanceId can not be updated. To create @see create
     * @param id
     * @param space space for updating or creating
     * @return space created or updated
     */
    Mono<EntityDataState<Space>> updateOrCreate(final UUID id, final Space space);

    /**
     * Get entes by space id.
     * @param spaceId the spaceId to find
     * @return the ente view
     */
    Flux<ParentChildName> getEntesBySpace(final UUID spaceId);

    /**
     * Get ente categories by space id. If the ente category doesn't exist the relation between space and ente
     * category is removed automatically (purge)
     * @param spaceId the spaceId to find
     * @return the ente category view
     */
    Flux<ParentChildName> getEnteCategoriesBySpace(final UUID spaceId);

    /**
     * Adding Ente into space
     * @param ente the Ente
     * @return The Ente added
     */
    Mono<Ente> AddEnteIntoSpace(Ente ente);
}
