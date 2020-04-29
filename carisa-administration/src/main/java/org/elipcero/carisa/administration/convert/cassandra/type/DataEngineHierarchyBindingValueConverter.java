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
 *
 */

package org.elipcero.carisa.administration.convert.cassandra.type;

import org.elipcero.carisa.administration.convert.type.DataEngineValueConverter;
import org.elipcero.carisa.administration.domain.DynamicObjectInstanceProperty;

import java.util.UUID;

/**
 * Convert from/to cassandra the hierarchy binding value
 * @see DataEngineValueConverter
 *
 * @author David Suárez
 */
public class DataEngineHierarchyBindingValueConverter implements DataEngineValueConverter {

    /**
     * @see DataEngineValueConverter#create(String)
     */
    @Override
    public DynamicObjectInstanceProperty.Value create(String value) {
        String[] properties = value.split(",");

        return new DynamicObjectInstanceProperty.HierarchyBindingValue(
                stringToUUID(properties[0]),
                UUID.fromString(properties[1]),
                Boolean.parseBoolean(properties[2])
        );
    }

    /**
     * @see DataEngineValueConverter#writeToString(DynamicObjectInstanceProperty.Value)
     */
    @Override
    public String writeToString(DynamicObjectInstanceProperty.Value value) {
        DynamicObjectInstanceProperty.HierarchyBindingValue hierarchyBindingValue =
                (DynamicObjectInstanceProperty.HierarchyBindingValue)value.getRawValue();

        return UUIDToString(hierarchyBindingValue.getParentId()) + "," +
                hierarchyBindingValue.getChildId().toString() + "," +
                hierarchyBindingValue.getCategory();
    }

    private static String UUIDToString(UUID value) {
        return value == null ? "null" : value.toString();
    }

    private static UUID stringToUUID(String value) {
        if (value.equals("null")) {
            return null;
        }
        return UUID.fromString(value);
    }
}
