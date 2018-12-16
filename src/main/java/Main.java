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

public class Main {

    public static void main(String[] args) {
        try {
            Sequence first = DNATools.createDNASequence("ACGA", "query");
            Sequence second = DNATools.createDNASequence("ACGAGTGCGTGTTTTCCCGCCTGGTCCCCAGGCCCCCTTTCC", "target");
            //System.out.println(first);
            FiniteAlphabet alphabet = (FiniteAlphabet) AlphabetManager.alphabetForName("DNA");

            // local alligment
            SubstitutionMatrix matrix = SubstitutionMatrix.getNuc4_4();

            NeedlemanWunsch aligner = new NeedlemanWunsch(
                    (short) 0,      // match
                    (short) 3,      // replace
                    (short) 2,      // insert
                    (short) 1,      // delete
                    (short) 1,      // gapExtend
                    matrix          // SubstitutionMatrix
            );
            AlignmentPair ap = aligner.pairwiseAlignment(
                    first, // first sequence
                    second // second one
            );

            System.out.println("Global alignment with Needleman-Wunsch:\n" + ap.formatOutput() + "\n Edit distance " + aligner.getEditDistance());

            SmithWaterman aligner2 = new SmithWaterman(
                    (short) 0,      // match
                    (short) 3,      // replace
                    (short) 2,      // insert
                    (short) 2,      // delete
                    (short) 1,      // gapExtend
                    matrix          // SubstitutionMatrix
            );

            AlignmentPair ap2 = aligner2.pairwiseAlignment(
                    first, // first sequence
                    second // second one
            );


            System.out.println("\n \n Local alignment with SmithWaterman:\n" + ap2.formatOutput());


        } catch (IllegalSymbolException e) {
            e.printStackTrace();
        } catch (BioException e) {
            e.printStackTrace();
        }
    }
}
