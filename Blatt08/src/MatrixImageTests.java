import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.InputMismatchException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixImageTests {
    @ParameterizedTest
    @MethodSource("contrasTestDataSource")
    void testContrast(ContrasTestData data) {
        if (data instanceof ShouldFail) {
            assertThrows(((ShouldFail) data).getExpectedExceptionClass(), () -> {
                MatrixImage img = data.getMatrixImage();
                img.contrast(new Coordinate(data.x1, data.y1), new Coordinate(data.x2, data.y2));
            }, "Invalid coordinates should result in an exception.");
        } else {
            MatrixImage img = data.getMatrixImage();
            assertEquals(data.expected, img.contrast(new Coordinate(data.x1, data.y1), new Coordinate(data.x2, data.y2)), "Contras is incorrect.");
        }
    }

    public static Stream<ContrasTestData> contrasTestDataSource() {
        return Stream.of(new ContrasTestData[]{
                new ContrastFailData(new int[][]{{1}}, 5, 0, 0, 0),
                new ContrastFailData(new int[][]{{1}}, 0, 5, 0, 0),
                new ContrastFailData(new int[][]{{1}}, 0, 0, 5, 0),
                new ContrastFailData(new int[][]{{1}}, 0, 0, 0, 5),
                new ContrastFailData(new int[][]{{1}}, -5, 0, 0, 0),
                new ContrastFailData(new int[][]{{1}}, 0, -5, 0, 0),
                new ContrastFailData(new int[][]{{1}}, 0, 0, -5, 0),
                new ContrastFailData(new int[][]{{1}}, 0, 0, 0, -5),

                new ContrastFailData(new int[][]{{1, 2}, {3, 4}}, 3, 0, 0, 0),

                new ContrasTestData(new int[][]{{1}}, 0, 0, 0, 0, 0),
                new ContrasTestData(new int[][]{{1, 2}}, 0, 0, 0, 1, 1),
                new ContrasTestData(new int[][]{{2, 1}}, 0, 0, 0, 1, 1)}
        );
    }

    @ParameterizedTest
    @MethodSource("removePathDataSource")
    public void testRemoveVPath(RemovePathData data) {
        MatrixImage img = data.getMatrixImage();
        img.removeVPath(data.path);

        assertArrayEquals(data.newFiled, img.field);
    }

    public static Stream<RemovePathData> removePathDataSource() {
        return Stream.of(new RemovePathData[]{ // handsimuliert
                new RemovePathData(new int[][]{{1, 2}, {3, 4}}, new int[]{0, 0}, new int[][]{{3, 4}}),
                new RemovePathData(new int[][]{{1, 2}, {3, 4}}, new int[]{1, 1}, new int[][]{{1, 2}}),
                new RemovePathData(new int[][]{{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}, {11, 12, 13, 14, 15}}, new int[]{0, 1, 2, 1, 0}, new int[][]{{6, 2, 3, 4, 10}, {11, 12, 8, 14, 15}}),
                new RemovePathData(new int[][]{{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18},{19,20,21,22,23,24},{25,26,27,28,29,30},{31,32,33,34,35,36}},
                        new int[]{5,3,4,1,4,2},
                        new int[][]{{1,2,3,4,5,6},{7,8,9,16,11,12},{13,14,15,22,17,24},{19,26,21,28,23,30},{25,32,33,34,35,36}}),
        });
    }

    public interface ShouldFail {
        Class getExpectedExceptionClass();
    }

    public static class ImageData {
        private int x;
        private int y = -1;
        private int[][] field;

        public ImageData(int[][] field) {
            this.field = field;

            this.x = field.length;
            // make sure that the input is correct
            for (int[] ar : field) {
                if (this.y == -1) {
                    this.y = ar.length;
                } else {
                    assertEquals(this.y, ar.length);
                }
            }
        }

        public MatrixImage getMatrixImage() {
            MatrixImage img = new MatrixImage(x, y);
            img.field = field;

            return img;
        }
    }

    public static class ContrasTestData extends ImageData {
        public int x1;
        public int y1;
        public int x2;
        public int y2;
        public int expected;

        public ContrasTestData(int[][] field, int x1, int y1, int x2, int y2, int expected) {
            super(field);
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.expected = expected;
        }
    }

    public static class ContrastFailData extends ContrasTestData implements ShouldFail {

        public ContrastFailData(int[][] field, int x1, int y1, int x2, int y2) {
            super(field, x1, y1, x2, y2, 0);
        }

        @Override
        public Class getExpectedExceptionClass() {
            return InputMismatchException.class;
        }
    }

    public static class RemovePathData extends ImageData {
        public int[] path;
        public int[][] newFiled;

        public RemovePathData(int[][] field, int[] path, int[][] newFiled) {
            super(field);
            this.path = path;
            this.newFiled = newFiled;
        }
    }
}
