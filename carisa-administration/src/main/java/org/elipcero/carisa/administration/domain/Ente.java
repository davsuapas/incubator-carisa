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

package org.elipcero.carisa.administration.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.elipcero.carisa.core.data.EntityInitializer;
import org.elipcero.carisa.core.data.Relation;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The thinks of space.
 * The ente are the items of spaces to trace, count, measure, etc.
 *
 * @author David Suárez
 */
@Table("carisa_ente")
@Builder
@Getter
@Setter
public class Ente implements Relation, EntityInitializer<Ente> {

    public static String SPACEID_COLUMN_NAME = "spaceId";
    public static String ID_COLUMN_NAME = "Id";

    @Column("parentId")
    @PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID spaceId;

    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private UUID id;

    private String name;

    @Override
    public UUID getParentId() {
        return this.spaceId;
    }

    @Override
    public UUID getChildId() {
        return this.id;
    }

    @Override
    public void setParentId(UUID value) {
        this.spaceId = value;
    }

    public static Map<String, Object> GetMapId(UUID spaceId, UUID id) {
        return new HashMap<String, Object>() {{
            put(SPACEID_COLUMN_NAME, spaceId);
            put(ID_COLUMN_NAME, id);
        }};
    }

    @Override
    public Ente tryInitId() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
        return this;
    }
}
