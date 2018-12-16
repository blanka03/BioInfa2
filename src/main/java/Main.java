import org.biojava.bio.BioException;
import org.biojava.bio.alignment.AlignmentPair;
import org.biojava.bio.alignment.NeedlemanWunsch;
import org.biojava.bio.alignment.SubstitutionMatrix;
import org.biojava.bio.seq.DNATools;
import org.biojava.bio.seq.Sequence;
import org.biojava.bio.symbol.AlphabetManager;
import org.biojava.bio.symbol.FiniteAlphabet;
import org.biojava.bio.symbol.IllegalSymbolException;

public class Main {

    public static void main(String[] args) {
        try {
            Sequence first = DNATools.createDNASequence("CACGTTTCTTGTGGCAGCTTAAGTTTGAATGTCATTTCTTCAATGGGACGGA"+
                                    "GCGGGTGCGGTTGCTGGAAAGATGCATCTATAACCAAGAGGAGTCCGTGCGCTTCGACAGC"+
                                "GACGTGGGGGAGTACCGGGCGGTGACGGAGCTGGGGCGGCCTGATGCCGAGTACTGGAACA"+
                                "GCCAGAAGGACCTCCTGGAGCAGAGGCGGGCCGCGGTGGACACCTACTGCAGACACAACTA"+
                                "CGGGGTTGGTGAGAGCTTCACAGTGCAGCGGCGAG", "query");
            Sequence second = DNATools.createDNASequence("ACGAGTGCGTGTTTTCCCGCCTGGTCCCCAGGCCCCCTTTCCGTCCTCAGGAA"+
                                 "GACAGAGGAGGAGCCCCTCGGGCTGCAGGTGGTGGGCGTTGCGGCGGCGGCCGGTTAAGGT"+
                                 "TCCCAGTGCCCGCACCCGGCCCACGGGAGCCCCGGACTGGCGGCGTCACTGTCAGTGTCTT"+
                                 "CTCAGGAGGCCGCCTGTGTGACTGGATCGTTCGTGTCCCCACAGCACGTTTCTTGGAGTAC"+
                                 "TCTACGTCTGAGTGTCATTTCTTCAATGGGACGGAGCGGGTGCGGTTCCTGGACAGATACT"+
                                 "TCCATAACCAGGAGGAGAACGTGCGCTTCGACAGCGACGTGGGGGAGTTCCGGGCGGTGAC"+
                                 "GGAGCTGGGGCGGCCTGATGCCGAGTACTGGAACAGCCAGAAGGACATCCTGGAAGACGAG"+
                                 "CGGGCCGCGGTGGACACCTACTGCAGACACAACTACGGGGTTGTGAGAGCTTCACCGTGCA"+
                                 "GCGGCGAGACGCACTCGT", "target");
            //System.out.println(first);
            FiniteAlphabet alphabet = (FiniteAlphabet) AlphabetManager.alphabetForName("DNA");

            // local alligment
            SubstitutionMatrix matrix = SubstitutionMatrix.getNuc4_2();

            NeedlemanWunsch aligner = new NeedlemanWunsch(
                    (short) 0,  // match
                    (short) 3,  // replace
                    (short) 2,      // insert
                    (short) 2,  // delete
                    (short) 1,      // gapExtend
                    matrix  // SubstitutionMatrix
            );
            AlignmentPair ap = aligner.pairwiseAlignment(
                    first, // first sequence
                    second // second one
            );

            System.out.println("Global alignment with Needleman-Wunsch:\n" + ap.formatOutput()  + "\n Edit distance " + aligner.getEditDistance());

            /*SequencePair<DNASequence, NucleotideCompound> psaLocal =
                    Alignments.getPairwiseAlignment(first, second,
                            Alignments.PairwiseSequenceAlignerType.LOCAL, gapP,  matrix);
            System.out.println("Local alignment with SmithWaterman:\n" + psaLocal);

            // global alligment
            SequencePair<DNASequence, NucleotideCompound> psaGlobal =
                    Alignments.getPairwiseAlignment(first, second,
                            Alignments.PairwiseSequenceAlignerType.GLOBAL, gapP,  matrix);

            NeedlemanWunsch

            System.out.println("");
            System.out.println("");
            System.out.println("Global alignment with Needleman-Wunsch:\n" + psaGlobal);*/
        } catch (IllegalSymbolException e) {
            e.printStackTrace();
        } catch (BioException e) {
            e.printStackTrace();
        }
    }
}
