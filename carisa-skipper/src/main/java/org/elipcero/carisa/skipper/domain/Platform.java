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

package org.elipcero.carisa.skipper.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * This contain general platform information
 *
 * @author David Suárez
 */
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class Platform {

    public Platform(String name) {
        this.name = name;
    }

    @Id
    @Getter(AccessLevel.NONE)
    private String id;

    @Setter(AccessLevel.NONE)
    private String name;

    @Version
    @Setter(AccessLevel.NONE)
    private Long version;
}
