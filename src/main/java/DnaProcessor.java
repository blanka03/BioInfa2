import org.biojava.bio.BioException;
import org.biojava.bio.alignment.AlignmentPair;
import org.biojava.bio.alignment.NeedlemanWunsch;
import org.biojava.bio.alignment.SmithWaterman;
import org.biojava.bio.alignment.SubstitutionMatrix;
import org.biojava.bio.seq.DNATools;
import org.biojava.bio.seq.Sequence;
import org.biojava.bio.symbol.AlphabetManager;
import org.biojava.bio.symbol.FiniteAlphabet;
import org.biojava.bio.symbol.IllegalSymbolException;

import java.io.IOException;

public class DnaProcessor {
    private short matchPenalty;
    private short replacePenalty;
    private short insertPenalty;
    private short deletePenalty;
    private short gapExtendPenalty;
    //required
    private String sequenceQuery;
    private String sequenceTarget;


    DnaProcessor(DnaProcessorBuilder dnaProcessorBuilder) {
        this.sequenceQuery = dnaProcessorBuilder.sequenceQuery;
        this.sequenceTarget = dnaProcessorBuilder.sequenceTarget;
        this.matchPenalty = dnaProcessorBuilder.matchPenalty;
        this.replacePenalty = dnaProcessorBuilder.replacePenalty;
        this.insertPenalty = dnaProcessorBuilder.insertPenalty;
        this.deletePenalty = dnaProcessorBuilder.deletePenalty;
        this.gapExtendPenalty = dnaProcessorBuilder.gapExtendPenalty;
    }

    String process(boolean local, boolean global, boolean customMatrix, String costMatrix) {
        String response = "";
        try {
            Sequence query = DNATools.createDNASequence(sequenceQuery, "query");
            Sequence target = DNATools.createDNASequence(sequenceTarget, "target");
            SubstitutionMatrix matrix;
            if (!customMatrix) {
                matrix = SubstitutionMatrix.getNuc4_4();
            } else {
                matrix = new SubstitutionMatrix((FiniteAlphabet) AlphabetManager.alphabetForName("DNA"), costMatrix, "macierz kosztu");
            }

            if (global) {
                NeedlemanWunsch aligner = new NeedlemanWunsch(matchPenalty, replacePenalty,
                        insertPenalty, deletePenalty, gapExtendPenalty, matrix);
                AlignmentPair ap = aligner.pairwiseAlignment(query, target);

                response += "Global alignment with Needleman-Wunsch:\n" + ap.formatOutput() + "\n Edit distance " + aligner.getEditDistance();
            }
            if (local) {
                SmithWaterman aligner2 = new SmithWaterman(matchPenalty, replacePenalty,
                        insertPenalty, deletePenalty, gapExtendPenalty, matrix);

                AlignmentPair ap2 = aligner2.pairwiseAlignment(query, target);

                response += "\n\n Local alignment with SmithWaterman:\n" + ap2.formatOutput();
            }
            return response;
        } catch (IllegalSymbolException e) {
            e.printStackTrace();
            return "";
        } catch (BioException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static class DnaProcessorBuilder {

        private short matchPenalty;
        private short replacePenalty;
        private short insertPenalty;
        private short deletePenalty;
        private short gapExtendPenalty;
        //required
        private String sequenceQuery;
        private String sequenceTarget;

        public DnaProcessorBuilder(String seq1, String seq2) {
            this.sequenceQuery = seq1;
            this.sequenceTarget = seq2;
        }

        public DnaProcessorBuilder buildMatchPenalty(String match) {
            this.matchPenalty = (short) Integer.parseInt(match);
            return this;
        }

        public DnaProcessorBuilder buildReplacePenalty(String replace) {
            this.replacePenalty = (short) Integer.parseInt(replace);
            return this;
        }

        public DnaProcessorBuilder buildInsertPenalty(String insert) {
            this.insertPenalty = (short) Integer.parseInt(insert);
            return this;
        }

        public DnaProcessorBuilder buildDeletePenalty(String delete) {
            this.deletePenalty = (short) Integer.parseInt(delete);
            return this;
        }

        public DnaProcessorBuilder buildGapExtendPenalty(String gap) {
            this.gapExtendPenalty = (short) Integer.parseInt(gap);
            return this;
        }

        public DnaProcessor build() {
            return new DnaProcessor(this);
        }
    }
}
