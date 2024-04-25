import java.util.Arrays;


public class ConvexPolygon extends Polygon {
    
    private Vector2D[] vertices;

    public ConvexPolygon(Vector2D[] vertices){
        this.vertices = vertices;
    }

    ConvexPolygon(ConvexPolygon polygon) {
        this.vertices = polygon.getVertices();
    }

    public void setVertices(Vector2D[] vertices) {
        this.vertices = vertices;
    }

    public Vector2D[] getVertices() {
        return vertices;
    }

    @Override
    public String toString() {
        String outputTxt;
        outputTxt = "ConvexPolygon([";
        for (Vector2D vector2d : this.vertices) {
            outputTxt = outputTxt + vector2d.toString() + ", ";
        }
        outputTxt = outputTxt.substring(0, outputTxt.length() - 2) + "])";
        return outputTxt;
    }

    @Override
    public double perimeter() {
        double perimeter = 0;
        Vector2D lastVector2d = null;
        for (Vector2D vector2d : this.vertices) {
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

    @Override
    public double area() {
        //https://math.stackexchange.com/questions/4626242/how-do-you-find-the-area-of-a-triangle-given-two-2d-vectors
        double area = 0;
        Vector2D oneVector2d = null;
        Vector2D startVector2d = vertices[0];
        for (Vector2D vector2d : this.vertices) {
            if (oneVector2d == null || oneVector2d == startVector2d) {
                oneVector2d = vector2d;
                continue;
            }
            else{
                Vector2D a = new Vector2D(oneVector2d.getX() - startVector2d.getX(), oneVector2d.getY() - startVector2d.getY());
                Vector2D b = new Vector2D(vector2d.getX() - startVector2d.getX(), vector2d.getY() - startVector2d.getY());
                area += (a.getX() * b.getY() - a.getY() * b.getX()) / 2;
                oneVector2d = vector2d;
            }
        }
        return area;
    }

    public static Polygon[] somePolygons() {
        Polygon[] output = new Polygon[4];
        output[0] = new ConvexPolygon(new Vector2D[]{new Vector2D(0, 0), new Vector2D(10, 0), new Vector2D(5, 5)});
        output[1] = new ConvexPolygon(new Vector2D[]{new Vector2D(0, 0), new Vector2D(10, -5), new Vector2D(12, 2), new Vector2D(3, 17)});
        return output;
    }
}


