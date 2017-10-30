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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import uk.ac.ebi.eva.commons.mongodb.entities.SampleMongo;
import uk.ac.ebi.eva.dbsnpimporter.models.Sample;

public class ImportSamplesStepConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ImportSamplesStepConfiguration.class);

    private static final String LOAD_SAMPLES_STEP = "LOAD_SAMPLES_STEP";

    @Autowired
    private ItemStreamReader<Sample> reader;

    @Autowired
    private ItemProcessor<Sample, SampleMongo> processor;

    @Autowired
    private ItemStreamWriter<SampleMongo> writer;

//    // TODO: is this necessary? If so, implement DbsnpImporterBatchConfiguration
//    @Bean
//    public SimpleCompletionPolicy chunkSizecompletionPolicy(DbsnpImporterBatchConfiguration configuration) {
//        return new SimpleCompletionPolicy(configuration.getChunkSize());
//    }

    @Bean(LOAD_SAMPLES_STEP)
    public Step loadSamplesStep(StepBuilderFactory stepBuilderFactory,
                                SimpleCompletionPolicy chunkSizeSimpleCompletionPolicy) {

        logger.debug("Building '" + LOAD_SAMPLES_STEP + "'");

        return stepBuilderFactory.get(LOAD_SAMPLES_STEP)
                                 .<Sample, SampleMongo>chunk(chunkSizeSimpleCompletionPolicy)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
