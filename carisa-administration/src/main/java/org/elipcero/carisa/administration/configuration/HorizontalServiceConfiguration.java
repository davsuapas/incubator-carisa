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

package org.elipcero.carisa.administration.configuration;

import org.elipcero.carisa.administration.domain.DynamicObjectInstance;
import org.elipcero.carisa.administration.domain.DynamicObjectInstanceProperty;
import org.elipcero.carisa.administration.domain.DynamicObjectPrototype;
import org.elipcero.carisa.administration.domain.DynamicObjectPrototypeProperty;
import org.elipcero.carisa.administration.domain.Ente;
import org.elipcero.carisa.administration.domain.EnteCategory;
import org.elipcero.carisa.administration.domain.EnteCategoryLinkProperty;
import org.elipcero.carisa.administration.domain.EnteCategoryProperty;
import org.elipcero.carisa.administration.domain.EnteHierarchy;
import org.elipcero.carisa.administration.domain.EnteProperty;
import org.elipcero.carisa.administration.domain.Instance;
import org.elipcero.carisa.administration.domain.InstanceSpace;
import org.elipcero.carisa.administration.domain.Plugin;
import org.elipcero.carisa.administration.domain.PluginType;
import org.elipcero.carisa.administration.domain.Space;
import org.elipcero.carisa.administration.domain.SpaceEnte;
import org.elipcero.carisa.administration.domain.SpaceQueryInstance;
import org.elipcero.carisa.administration.repository.DynamicObjectInstanceRepository;
import org.elipcero.carisa.administration.repository.DynamicObjectPrototypeRepository;
import org.elipcero.carisa.administration.repository.EnteCategoryRepository;
import org.elipcero.carisa.administration.repository.EnteRepository;
import org.elipcero.carisa.administration.repository.InstanceRepository;
import org.elipcero.carisa.administration.repository.SpaceRepository;
import org.elipcero.carisa.administration.service.DefaultDynamicObjectInstancePropertyService;
import org.elipcero.carisa.administration.service.DefaultDynamicObjectPrototypePropertyService;
import org.elipcero.carisa.administration.service.DefaultEnteCategoryPropertyService;
import org.elipcero.carisa.administration.service.DefaultEnteCategoryService;
import org.elipcero.carisa.administration.service.DefaultEntePropertyService;
import org.elipcero.carisa.administration.service.DefaultEnteService;
import org.elipcero.carisa.administration.service.DefaultInstanceService;
import org.elipcero.carisa.administration.service.DefaultSpaceService;
import org.elipcero.carisa.administration.service.DynamicObjectInstancePropertyService;
import org.elipcero.carisa.administration.service.DynamicObjectPrototypePropertyService;
import org.elipcero.carisa.administration.service.DynamicQueryInstanceService;
import org.elipcero.carisa.administration.service.EnteCategoryPropertyService;
import org.elipcero.carisa.administration.service.EnteCategoryService;
import org.elipcero.carisa.administration.service.EntePropertyService;
import org.elipcero.carisa.administration.service.EnteService;
import org.elipcero.carisa.administration.service.InstanceService;
import org.elipcero.carisa.administration.service.PluginDynamicPrototypeService;
import org.elipcero.carisa.administration.service.SpaceService;
import org.elipcero.carisa.administration.service.support.DynamicObjectInstanceService;
import org.elipcero.carisa.administration.service.support.DynamicObjectPrototypeService;
import org.elipcero.carisa.core.application.configuration.ServiceProperties;
import org.elipcero.carisa.core.reactive.data.EmbeddedDependencyRelation;
import org.elipcero.carisa.core.reactive.data.MultiplyDependencyRelation;
import org.elipcero.carisa.core.reactive.misc.DataLockController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * General configuration
 *
 * @author David Suárez
 */
@EnableConfigurationProperties(ServiceProperties.class)
@Configuration
public class HorizontalServiceConfiguration {

    @Autowired
    private ServiceProperties serviceProperties;

    @Autowired
    private DataLockController dataLockController;

    // Relations

    @Autowired
    private MultiplyDependencyRelation<Instance, Space, InstanceSpace> instanceSpaceRelation;

    @Autowired
    private MultiplyDependencyRelation<Space, Ente, SpaceEnte> spaceEnteRelation;

    @Autowired
    private EmbeddedDependencyRelation<EnteProperty> entePropertyRelation;

    @Autowired
    private MultiplyDependencyRelation<EnteCategory, EnteCategory, EnteHierarchy> enteCategoryHirarchyRelation;

    @Autowired
    private MultiplyDependencyRelation<EnteCategory, Ente, EnteHierarchy> enteHierarchyRelation;

    @Autowired
    private MultiplyDependencyRelation<Space, EnteCategory, EnteHierarchy> spaceHirarchyRelation;

    @Autowired
    private EmbeddedDependencyRelation<EnteCategoryProperty> enteCategoryPropertyRelation;

    @Autowired
    private MultiplyDependencyRelation<EnteCategoryProperty, Ente, EnteCategoryLinkProperty> linkEnteRelation;

    @Autowired
    private MultiplyDependencyRelation<Space, DynamicObjectInstance, SpaceQueryInstance> spaceQueryRelation;

    @Autowired
    private MultiplyDependencyRelation<PluginType, DynamicObjectPrototype, Plugin> pluginTypePluginRelation;

    @Autowired
    private EmbeddedDependencyRelation<DynamicObjectPrototypeProperty> prototypePropertyRelation;

    @Autowired
    private EmbeddedDependencyRelation<DynamicObjectInstanceProperty<?>> instancePropertyRelation;

    // Dynamic object

    @Autowired
    private DynamicObjectPrototypeRepository dynamicObjectPrototypeRepository;

    @Autowired
    private DynamicObjectInstanceRepository dynamicObjectInstanceRepository;

    // Instance configuration

    @Autowired
    private InstanceRepository instanceRepository;

    @Bean
    public InstanceService instanceService() {
        return new DefaultInstanceService(
                instanceRepository, serviceProperties, dataLockController, instanceSpaceRelation);
    }

    // Space configuration

    @Autowired
    private SpaceRepository spaceRepository;

    @Bean
    public SpaceService spaceService() {
        return new DefaultSpaceService(
                spaceRepository, spaceEnteRelation, enteCategoryHirarchyRelation,
                instanceSpaceRelation, spaceQueryRelation);
    }

    // Ente configuration

    @Autowired
    private EnteRepository enteRepository;

    @Bean
    public EnteService enteService() {
        return new DefaultEnteService(
                enteRepository, enteHierarchyRelation, entePropertyRelation, spaceEnteRelation);
    }

    // Ente property configuration

    @Bean
    public EntePropertyService entePropertyService() {
        return new DefaultEntePropertyService(entePropertyRelation);
    }

    // Ente category configuration

    @Autowired
    private EnteCategoryRepository enteCategoryRepository;

    @Bean
    public EnteCategoryService enteCategoryService() {
        return new DefaultEnteCategoryService(
                enteCategoryRepository, enteCategoryHirarchyRelation,
                spaceHirarchyRelation, enteRepository);
    }

    // Ente Category property configuration

    @Bean
    public EnteCategoryPropertyService enteCategoryPropertyService() {
        return new DefaultEnteCategoryPropertyService(
                enteCategoryPropertyRelation, linkEnteRelation, enteHierarchyRelation, entePropertyRelation);
    }

    // Plugin prototype configuration

    @Bean
    public DynamicObjectPrototypeService<Plugin> dynamicObjectPrototypeService() {
        return new PluginDynamicPrototypeService(
                dynamicObjectPrototypeRepository, pluginTypePluginRelation, prototypePropertyRelation);
    }

    // Query instance configuration

    @Bean
    public DynamicObjectInstanceService<SpaceQueryInstance> dynamicObjectInstanceService() {
        return new DynamicQueryInstanceService(
                dynamicObjectInstanceRepository, spaceQueryRelation, dynamicObjectPrototypeRepository, instancePropertyRelation);
    }

    // Query prototype property configuration

    @Bean
    public DynamicObjectPrototypePropertyService dynamicObjectPrototypePropertyService() {
        return new DefaultDynamicObjectPrototypePropertyService(prototypePropertyRelation);
    }

    // Query instance property configuration

    @Bean
    public DynamicObjectInstancePropertyService dynamicObjectInstancePropertyService() {
        return new DefaultDynamicObjectInstancePropertyService(instancePropertyRelation);
    }
}
