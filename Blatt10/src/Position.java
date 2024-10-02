public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // copy constructor
    public Position(Position that) {
        this(that.x, that.y);
    }

    /**
     * Displace the position by the specified values.
     *
     * @param xd Displacement in x-direction
     * @param yd Displacement in y-direction
     */
    public void displace(int xd, int yd) {
        x += xd;
        y += yd;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        /* TODO */
    }

    @Override
    public int hashCode() {
        /* TODO */
    }
}

