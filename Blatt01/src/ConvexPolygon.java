import java.util.Arrays;


public class ConvexPolygon extends Polygon {
    // TODO
    private Vector2D[] vertices;

    public ConvexPolygon(Vector2D[] vertices){
        this.vertices = vertices;
    }

    @Override
    public String toString() {
        String outputTxt;
        outputTxt = "ConvexPolygon([";
        for (Vector2D vector2d : vertices) {
            outputTxt = outputTxt + vector2d.toString() + ", ";
        }
        outputTxt = outputTxt.substring(0, outputTxt.length() - 2) + "])";
        return outputTxt;
    }

    @Override
    public double perimeter() {
        double perimeter = 0;
        Vector2D lastVector2d = null;
        for (Vector2D vector2d : vertices) {
            if (lastVector2d != null) {
                Vector2D tmp = new Vector2D(vector2d.getX() - lastVector2d.getX(), vector2d.getY() - lastVector2d.getY());
                perimeter = perimeter + tmp.length();
            }
            lastVector2d = vector2d;
        }
        Vector2D ringClose = new Vector2D(lastVector2d.getX() - vertices[0].getX(), lastVector2d.getY() - vertices[0].getY());
        perimeter = perimeter + ringClose.length();
        return perimeter;
    }
}


