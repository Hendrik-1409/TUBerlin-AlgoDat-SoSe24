// Diese Klasse implementiert nur *zentrierte* reguläre Polygone, also mit midpoint = (0, 0).

public class RegularPolygon extends ConvexPolygon {

    private double radius;

    public RegularPolygon(int N, double radius) {
        super(new Vector2D[N]);
        double angle = 2 * Math.PI / N;
        for (int i = 0; i < N; i++) {
            this.vertices[i] = new Vector2D(radius * Math.cos(angle * i), radius * Math.sin(angle * i));
        }
        this.radius = radius;
    }

    public RegularPolygon(RegularPolygon polygon) {
        super(polygon.vertices.clone());
        this.radius = polygon.getRadius();
    }

    public double getRadius() {
        return radius;
    }

    public void resize(double newradius) {
        double angle = 2 * Math.PI / this.vertices.length;
        for (int i = 0; i < this.vertices.length; i++) {
            this.vertices[i] = new Vector2D(newradius * Math.cos(angle * i), newradius * Math.sin(angle * i));
        }
        this.radius = newradius;
    }

    @Override
    public String toString() {
        return "RegularPolygon{N=" + this.vertices.length + ", radius=" + radius + "}";
    }

    public static void main(String[] args) {
        RegularPolygon pentagon = new RegularPolygon(5, 1);
        System.out.println("Der Flächeninhalt des " + pentagon + " beträgt " + pentagon.area() + " LE^2.");
//        RegularPolygon otherpentagon = pentagon;      // Dies funktioniert nicht!
        RegularPolygon otherpentagon = new RegularPolygon(pentagon);
        pentagon.resize(10);
        System.out.println("Nach Vergrößerung: " + pentagon + " mit Fläche " + pentagon.area() + " LE^2.");
        System.out.println("Die Kopie: " + otherpentagon + " mit Fläche " + otherpentagon.area() + " LE^2.");
        /*
        Die erwartete Ausgabe ist:
Der Flächeninhalt des RegularPolygon{N=5, radius=1.0} beträgt 2.377641290737883 LE^2.
Nach Vergrößerung: RegularPolygon{N=5, radius=10.0} mit Fläche 237.7641290737884 LE^2.
Die Kopie: RegularPolygon{N=5, radius=1.0} mit Fläche 2.377641290737883 LE^2.
         */
    }
}

