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

package uk.ac.ebi.eva.dbsnpimporter.jobs.steps.processors;

import org.junit.Test;

import uk.ac.ebi.eva.commons.core.models.pedigree.Sex;
import uk.ac.ebi.eva.commons.mongodb.entities.SampleMongo;
import uk.ac.ebi.eva.dbsnpimporter.models.Sample;

import static org.junit.Assert.assertEquals;

public class SampleProcessorTest {

    @Test
    public void convertSample() throws Exception {
        SampleProcessor processor = new SampleProcessor();
        String sampleId = "sample1";
        String father = "father1";
        String mother = "mother1";
        Sample sample = new Sample(sampleId, Sex.MALE, father, mother, null);

        SampleMongo sampleMongo = processor.process(sample);

        assertEquals(sampleId, sampleMongo.getId());
        assertEquals("MALE", sampleMongo.getSex());
        assertEquals(father, sampleMongo.getFather());
        assertEquals(mother, sampleMongo.getMother());
        assertEquals(null, sampleMongo.getPhenotypes());
    }

}