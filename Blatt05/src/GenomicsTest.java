import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class GenomicsTest {
    @ParameterizedTest
    @MethodSource("simpleTestSource")
    void optBottomUpTestSimple(GenomicsTestData data) {
        assertEquals(data.expeced, Genomics.optBottomUp(data.letters, data.words));
    }

    @Test
    void optBottomUpTestBsp() {
        assertEquals(4, Genomics.optBottomUp("CAGTCCAGTCAGTC", new String[]{"AGT", "CA", "CAG", "GTC", "TC", "TCA", "TCC"}));
    }

    @Test
    void optBottomUpTestComplex() {
        assertEquals(0, Genomics.optBottomUp("AAAIGGGGTGIACTCTCCGAACATIICIIAAACICCTATGTCACCATCTAICTCICCIICGCAAIIGITCAGCGTIATIICICACA",
                new String[]{"ITI", "CC", "CTG", "CCA", "IC", "CI", "TCITI", "AT"}));
        assertEquals(80, Genomics.optBottomUp("GATCCTGCTGTCTCCTGTGCGATAGAGT", new String[]{"GAT", "GA", "TG", "AG", "TC", "TGC", "AGT", "TCC", "A", "G", "C"}));
    }

    static Stream<GenomicsTestData> simpleTestSource(){
        return Stream.of(new GenomicsTestData[]{
            new GenomicsTestData(1, "", new String[]{""}),
            new GenomicsTestData(1, "", new String[]{"A"}),
            new GenomicsTestData(0, "A", new String[]{""}),
            new GenomicsTestData(1, "A", new String[]{"A"}),
            new GenomicsTestData(2, "AA", new String[]{"A", "AA"}),
            new GenomicsTestData(4, "AABB", new String[]{"A", "AA", "B", "BB"})
        });
    }

    static class GenomicsTestData{
        public int expeced;
        public String letters;
        public String[] words;

        public GenomicsTestData(int expected, String letters, String[] words){
            this.expeced = expected;
            this.letters = letters;
            this.words = words;
        }
    }
}

