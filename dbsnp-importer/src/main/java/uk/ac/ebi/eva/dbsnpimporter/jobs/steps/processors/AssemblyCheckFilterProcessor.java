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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import uk.ac.ebi.eva.commons.core.models.Region;
import uk.ac.ebi.eva.dbsnpimporter.models.SubSnpCoreFields;
import uk.ac.ebi.eva.dbsnpimporter.sequence.FastaSequenceReader;
import uk.ac.ebi.eva.dbsnpimporter.sequence.ReadSequenceException;

/**
 * Spring batch processor that filter out variants whose reference allele does not match a fasta file containing a
 * sequence assembly for that species
 */
public class AssemblyCheckFilterProcessor implements ItemProcessor<SubSnpCoreFields, SubSnpCoreFields> {

    private static final Logger logger = LoggerFactory.getLogger(AssemblyCheckFilterProcessor.class);

    private FastaSequenceReader fastaReader;

    public AssemblyCheckFilterProcessor(FastaSequenceReader fastaSequenceReader) {
        this.fastaReader = fastaSequenceReader;
    }

    /**
     * Process a variant, filtering it out if the reference allele is not correct
     * @param subSnpCoreFields Variant
     * @return The variant not changed if the reference allele is correct, null otherwise
     * @throws Exception
     */
    @Override
    public SubSnpCoreFields process(SubSnpCoreFields subSnpCoreFields) throws Exception {
        String referenceAllele = subSnpCoreFields.getReferenceInForwardStrand();
        if (isEmpty(referenceAllele) || referenceAlleleIsCorrect(referenceAllele, subSnpCoreFields)) {
            return subSnpCoreFields;
        } else {
            return null;

        }
    }

    private boolean isEmpty(String referenceAllele) {
        return referenceAllele == null || referenceAllele.trim().length() == 0 || referenceAllele.equals("-");
    }

    private boolean referenceAlleleIsCorrect(String referenceAllele,
                                             SubSnpCoreFields subSnpCoreFields) throws ReadSequenceException {

        Region region = subSnpCoreFields.getVariantCoordinates();
        try {
            String sequenceInAssembly = fastaReader.getSequence(region.getChromosome(), region.getStart(),
                                                                region.getEnd());
            if (referenceAllele.equals(sequenceInAssembly)) {
                return true;
            } else {
                logger.warn(
                        "Variant filtered out because it the reference allele does not match the reference sequence: " +
                                "{}.\n{} expected in {}, {} found",
                        subSnpCoreFields, sequenceInAssembly, region, referenceAllele);
                return false;
            }
        } catch (IllegalArgumentException e) {
            logger.warn(
                    "Variant filtered out because the region {} cannot be retrieved from the reference sequence " +
                            "file: {}\n{}",
                    region, subSnpCoreFields, e.getMessage());
            return false;
        }
    }
}
