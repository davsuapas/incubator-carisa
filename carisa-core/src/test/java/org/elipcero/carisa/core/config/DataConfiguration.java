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

package org.elipcero.carisa.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;

/**
 * @author David Suárez
 */
@EnableConfigurationProperties(CassandraProperties.class)
@Configuration
public class DataConfiguration extends AbstractReactiveCassandraConfiguration {

    @Autowired
    private CassandraProperties cassandraProperties;

    public static final String CONST_KEY_SPACE_NAME = "carisa_test";

    @Override
    protected String getKeyspaceName() {
        return CONST_KEY_SPACE_NAME;
    }

    @Override
    protected boolean getMetricsEnabled() {
        return false;
    }

    @Override
    protected int getPort() {
        return this.cassandraProperties.getPort();
    }

}
