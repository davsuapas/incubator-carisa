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

package org.elipcero.carisa.administration.controller;

import org.elipcero.carisa.administration.testutil.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author David Suárez
 */
public abstract class DataAbstractControllerTest extends AbstractControllerTest {

    @Autowired
    private CassandraTemplate cqlTemplate;

    protected void executeCommands(final String resourceName) {
        this.executeCommands(resourceName, true);
    }

    /**
     * Execute cassandra sentences.
     * @param resourceName
     * @param dropTable If dropTable == true execute drop table sentences
     */
    protected void executeCommands(final String resourceName, final Boolean dropTable) {
        try {
            Arrays.stream(Util.loadCqlFromResource(resourceName).split("\n"))
                    .forEach(sentence -> {
                        if (dropTable) {
                            this.cqlTemplate.getCqlOperations().execute(sentence);
                        }
                        else if (!(sentence.toLowerCase().contains("drop table"))) {
                            this.cqlTemplate.getCqlOperations().execute(sentence);
                        }
                    });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
