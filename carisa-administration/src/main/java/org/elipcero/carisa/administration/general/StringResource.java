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

package org.elipcero.carisa.administration.general;

import org.springframework.hateoas.MediaTypes;

/**
 * String resources
 *
 * @author David Suárez
 */
public class StringResource {

    public static final String METADATA_INFORMATION
            = String.format("Use '%s' mediatype for more information. View links section",
                MediaTypes.HAL_FORMS_JSON_VALUE);
}
