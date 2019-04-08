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

package org.elipcero.carisa.skipper.service;

import org.springframework.cloud.skipper.domain.Deployer;

/**
 * Skipper service. Build space for skipper
 *
 * @author David Suárez
 */
public interface SkipperSpaceService {

    /**
     * Save deployer into skipper spaces. Skipper works with this information
     * @param deployer deployer
     * @return deployer saved
     */
    Deployer save(Deployer deployer);
}
