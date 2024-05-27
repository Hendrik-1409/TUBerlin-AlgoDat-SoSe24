public class BoardLoader {

    private static int[][] toIntState(String state, int n) {
        int[][] ar = new int[n][n];
        String[] lines = state.split("\n");
        for (int y = 0; y < n; y++) {
            ar[y] = new int[n];
            int x = 0;
            if (lines.length > y) {
                for (char c : lines[y].toCharArray()) {
                    ar[y][x++] = c == ' ' ? 0 : (c == 'X' ? 1 : -1);
                }
            }
        }

        return ar;
    }

    public static void load(Board b, int[][] state) {
        for (int y = 0; y < state.length; y++) {
            for (int x = 0; x < state[y].length; x++) {
                b.setField(new Position(x, y), state[y][x]);
            }
        }
    }

    public static void load(Board b, String state) {
        load(b, toIntState(state, b.getN()));
    }

    static Position pos = new Position(0, 0);
    static int[] map = new int[]{0,1,-1};
    public static void load(Board b, int seed) {
        // we interpret seed as a number base3
        int x = 0;
        int y = 0;
        int n = b.getN();

        while (seed != 0) {
            pos.x = x;
            pos.y = y;

            b.setField(pos, map[seed % 3]);


            seed /= 3;

            x++;
            if (x == n) {
                x = 0;
                y++; // this could overrun but checking would cost us.... So lets just say that seed is <= 3^(n*n)
            }
        }

        // fill the rest of the line with 0
        if(y < n) {
            for (; x < n; x++) {
                pos.x = x;
                pos.y = y;
                b.setField(pos, 0);
            }
        }
    }
}
