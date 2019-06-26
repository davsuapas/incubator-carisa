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

package org.elipcero.carisa.administration.repository;

import org.elipcero.carisa.administration.domain.Instance;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Custom repository for instance
 *
 * @author David Suárez
 */
public interface CustomInstanceRepository {

    /**
     * Change the state into instance
     * @param id the instance id to change state
     * @param state @see Instance.State
     * @return true if ok
     */
    Mono<Boolean> changeState(final UUID id, final Instance.State state);
}
