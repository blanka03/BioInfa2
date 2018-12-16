import org.biojava.nbio.alignment.Alignments;
import org.biojava.nbio.alignment.SimpleGapPenalty;
import org.biojava.nbio.alignment.template.GapPenalty;
import org.biojava.nbio.core.alignment.matrices.SubstitutionMatrixHelper;
import org.biojava.nbio.core.alignment.template.SequencePair;
import org.biojava.nbio.core.alignment.template.SubstitutionMatrix;
import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.compound.AmbiguityDNACompoundSet;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;

public class Main {

    public static void main(String[] args) {
        try {
            DNASequence first = new DNASequence("CACGTTTCTTGTGGCAGCTTAAGTTTGAATGTCATTTCTTCAATGGGACGGA"+
                                    "GCGGGTGCGGTTGCTGGAAAGATGCATCTATAACCAAGAGGAGTCCGTGCGCTTCGACAGC"+
                                "GACGTGGGGGAGTACCGGGCGGTGACGGAGCTGGGGCGGCCTGATGCCGAGTACTGGAACA"+
                                "GCCAGAAGGACCTCCTGGAGCAGAGGCGGGCCGCGGTGGACACCTACTGCAGACACAACTA"+
                                "CGGGGTTGGTGAGAGCTTCACAGTGCAGCGGCGAG", AmbiguityDNACompoundSet.getDNACompoundSet());
            DNASequence second = new DNASequence("ACGAGTGCGTGTTTTCCCGCCTGGTCCCCAGGCCCCCTTTCCGTCCTCAGGAA"+
                                 "GACAGAGGAGGAGCCCCTCGGGCTGCAGGTGGTGGGCGTTGCGGCGGCGGCCGGTTAAGGT"+
                                 "TCCCAGTGCCCGCACCCGGCCCACGGGAGCCCCGGACTGGCGGCGTCACTGTCAGTGTCTT"+
                                 "CTCAGGAGGCCGCCTGTGTGACTGGATCGTTCGTGTCCCCACAGCACGTTTCTTGGAGTAC"+
                                 "TCTACGTCTGAGTGTCATTTCTTCAATGGGACGGAGCGGGTGCGGTTCCTGGACAGATACT"+
                                 "TCCATAACCAGGAGGAGAACGTGCGCTTCGACAGCGACGTGGGGGAGTTCCGGGCGGTGAC"+
                                 "GGAGCTGGGGCGGCCTGATGCCGAGTACTGGAACAGCCAGAAGGACATCCTGGAAGACGAG"+
                                 "CGGGCCGCGGTGGACACCTACTGCAGACACAACTACGGGGTTGTGAGAGCTTCACCGTGCA"+
                                 "GCGGCGAGACGCACTCGT", AmbiguityDNACompoundSet.getDNACompoundSet());
            //System.out.println(first);

            // local alligment
            SubstitutionMatrix<NucleotideCompound> matrix = SubstitutionMatrixHelper.getNuc4_4();

            GapPenalty gapP = new SimpleGapPenalty(1,0);  // z tym coś bez sensu :/

            SequencePair<DNASequence, NucleotideCompound> psaLocal =
                    Alignments.getPairwiseAlignment(first, second,
                            Alignments.PairwiseSequenceAlignerType.LOCAL, gapP,  matrix);
            System.out.println("Local alignment with SmithWaterman:\n" + psaLocal);

            // global alligment
            SequencePair<DNASequence, NucleotideCompound> psaGlobal =
                    Alignments.getPairwiseAlignment(first, second,
                            Alignments.PairwiseSequenceAlignerType.GLOBAL, gapP,  matrix);
            System.out.println("");
            System.out.println("");
            System.out.println("Global alignment with Needleman-Wunsch:\n" + psaGlobal);
        } catch (CompoundNotFoundException e) {
            e.printStackTrace();
        }
    }
}
