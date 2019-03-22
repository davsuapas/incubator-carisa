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

import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elipcero.carisa.skipper.domain.KubernetesDeployerRequest;
import org.elipcero.carisa.skipper.factory.EnvironmentServiceFactory;
import org.springframework.cloud.skipper.domain.Deployer;
import org.springframework.cloud.skipper.domain.Platform;
import org.springframework.cloud.skipper.server.repository.map.DeployerRepository;

import java.util.Arrays;
import java.util.List;

/**
 * @author David Suárez
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultDeployerService implements DeployerService {

    @NonNull
    private final DeployerRepository deployerRepository;

    @NonNull
    private final EnvironmentServiceFactory environmentServiceFactory;

    @NonNull
    private final List<Platform> platforms;

    public Deployer deploy(final KubernetesClient client, final Deployer deployer, final Object properties) {
        this.environmentServiceFactory.Get(deployer.getType()).create(properties, client);
        AddDeployerToPlatform(deployer);
        Deployer result = Save(deployer);

        return result;
    }

    private void AddDeployerToPlatform(Deployer deployer) {
        Platform platform = this.platforms.stream()
                .filter(p -> p.getName().equalsIgnoreCase(KubernetesDeployerRequest.PLATFORM_TYPE_KUBERNETES))
                .findFirst()
                .get();

        platform.setDeployers(Arrays.asList(deployer));
    }

    private Deployer Save(Deployer deployer) {
        Deployer result = this.deployerRepository.save(deployer);
        this.log.info("Deployer '{}' saved for platform: '{}'", deployer.getName(), deployer.getType());
        return result;
    }
}
