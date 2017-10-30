/*
 * Copyright 2017 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ebi.eva.dbsnpimporter.configurations;

import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import uk.ac.ebi.eva.dbsnpimporter.models.Sample;

public class ImportSamplesStepConfiguration {

    private static final String LOAD_SAMPLES_STEP = "LOAD_SAMPLES_STEP";

    @Autowired
    private ItemStreamReader<Sample> reader;

    @Autowired
    private ItemStreamWriter<Sample> writer;
    // TODO: the mongo writer is a Writer of SampleMongo. It should be modified to receive Sample objects to work

    // TODO: check if this is necesary
//    @Bean
//    public SimpleCompletionPolicy chunkSizecompletionPolicy(Parameters parameters) {
//        return new SimpleCompletionPolicy(parameters.getChunkSize());
//    }

    @Bean(LOAD_SAMPLES_STEP)
    public Step
}
