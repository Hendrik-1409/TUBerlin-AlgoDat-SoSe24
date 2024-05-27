import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IsGameWonTests {
    @ParameterizedTest
    @MethodSource("gameSource")
    void TestAllSpecificOutput(Result r) {
        int size = r.size;
        int len = (int) Math.pow(3, size * size);
        byte[] expectedOutput = Base64.getDecoder().decode(r.expected);
        Board b = new Board(size);

        for (int i = 0; i < len; i++) {
            BoardLoader.load(b, i);
            boolean expected = (expectedOutput[i / 8] & ((byte) (0x1 << i % 8))) != 0;
            assertEquals(expected, b.isGameWon());
        }

    }

    // Use this if you just want to recheck your code
    //@ParameterizedTest
    //@MethodSource("gameSource")
    void TestAllQuick(Result r) throws IOException {
        int size = r.size;
        int len = (int) Math.pow(3, size * size);
        byte[] output = new byte[len / 8 + 1];
        Board b = new Board(size);

        for (int i = 0; i < len; i++) {
            BoardLoader.load(b, i);
            output[i / 8] = (byte) (output[i / 8] | (((byte) (b.isGameWon() ? 0x1 : 0x0) << i % 8)));
        }
        String b64 = Base64.getEncoder().encodeToString(output);
        //Files.write(Paths.get("src/games" + r.size + "x" + r.size + ".txt"), StandardCharsets.US_ASCII.encode(b64).array()); // used to generate expected output files
        assertEquals(r.expected, b64);

    }

    static Stream<Result> gameSource() throws IOException {
        int len = 4;
        Result[] results = new Result[len];
        for (int i = 1; i <= len; i++) {
            results[i - 1] = new Result(i, new String(Files.readAllBytes(Paths.get("src/games"+i+"x"+ i +".txt")), StandardCharsets.US_ASCII));
        }


        return Stream.of(results);
    }

    static class Result {
        private String expected;
        private int size;

        public Result(int _size, String _expected) {
            this.expected = _expected;
            this.size = _size;
        }
    }
}
