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
import lombok.RequiredArgsConstructor;
import org.elipcero.carisa.administration.domain.EnteCategory;
import org.elipcero.carisa.administration.domain.EnteHierarchy;
import org.elipcero.carisa.administration.domain.Space;
import org.elipcero.carisa.administration.domain.support.Named;
import org.elipcero.carisa.administration.projection.EnteHierachyName;
import org.elipcero.carisa.administration.repository.EnteCategoryRepository;
import org.elipcero.carisa.administration.repository.EnteRepository;
import org.elipcero.carisa.core.data.EntityDataState;
import org.elipcero.carisa.core.reactive.data.DependencyRelationCreateCommand;
import org.elipcero.carisa.core.reactive.data.MultiplyDependencyConnectionInfo;
import org.elipcero.carisa.core.reactive.data.MultiplyDependencyRelation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @see EnteCategoryService
 *
 * @author David Suárez
 */
@RequiredArgsConstructor
public class DefaultEnteCategoryService implements EnteCategoryService {

    @NonNull
    private final EnteCategoryRepository enteCategoryRepository;

    @NonNull
    private final MultiplyDependencyRelation<EnteCategory, EnteCategory, EnteHierarchy> enteCategoryHierarchyRelation;

    @NonNull
    private final MultiplyDependencyRelation<Space, EnteCategory, EnteHierarchy> spaceHierarchyRelation;

    @NonNull
    private final EnteRepository enteRepository;

    /**
     * @see EnteCategoryService
     */
    @Override
    public Mono<EnteCategory> getById(final UUID id) {
        return this.enteCategoryRepository.findById(id);
    }

    /**
     * @see EnteCategoryService
     */
    @Override
    public Mono<EnteCategory> create(final EnteCategory enteCategory) {
        DependencyRelationCreateCommand<EnteCategory, EnteHierarchy> commandCreate =
                new DependencyRelationCreateCommand<EnteCategory, EnteHierarchy>() {
                    @Override
                    public EnteCategory getChild() {
                        return enteCategory;
                    }

                    @Override
                    public EnteHierarchy getRelation() {
                        return EnteHierarchy.builder()
                                .parentId(enteCategory.getParentId())
                                .id(enteCategory.getId())
                                .category(true).build();
                    }
                };

        if (enteCategory.isRoot()) {
            return this.spaceHierarchyRelation.create(commandCreate);
        }
        else {
            return this.enteCategoryHierarchyRelation.create(commandCreate);
        }
    }

    /**
     * @see EnteCategoryService
     */
    @Override
    public Mono<EntityDataState<EnteCategory>> updateOrCreate(final UUID id, final EnteCategory enteCategory) {
        enteCategory.setId(id);
        return this.enteCategoryRepository
                .updateCreate(id,
                        enteCategoryForUpdating -> enteCategoryForUpdating.setName(enteCategory.getName()),
                        () -> this.create(enteCategory));
    }

    /**
     * @see EnteCategoryService
     */
    @Override
    public Flux<EnteHierachyName> getChildren(final UUID enteCategoryId) {
        return this.enteCategoryHierarchyRelation.getChildrenByParent(
                    enteCategoryId, (relation)
                        -> relation.isCategory() ?
                        this.enteCategoryRepository.findById(relation.getChildId()).cast(Named.class) :
                        this.enteRepository.findById(relation.getChildId()).cast(Named.class))
                .map(child -> EnteHierachyName
                        .builder()
                            .parentId(child.getRelation().getParentId())
                            .childId(child.getRelation().getChildId())
                            .childName(child.getChild().getName())
                            .category(child.getRelation().isCategory())
                        .build());
    }

    /**
     * @see EnteCategoryService
     */
    @Override
    public Mono<EnteCategory> connectToParent(UUID childId, UUID parentId) {
        return this.enteCategoryHierarchyRelation.connectTo(
                EnteHierarchy.builder()
                    .parentId(parentId)
                    .id(childId)
                    .category(true)
                .build()).map(MultiplyDependencyConnectionInfo::getChild);
    }
}
