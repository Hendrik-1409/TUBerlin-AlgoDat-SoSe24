import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class EdgeWeightedGraphTests {
    @Test
    void testGetCoordinates(){
        EdgeWeightedGraph g = new EdgeWeightedGraph(2);
        g.getCoordinates();
    }

    @Test
    void testSetCoordinates(){
        EdgeWeightedGraph g = new EdgeWeightedGraph(2);
        g.setCoordinates(new double[][]{});
    }

/*    @Test
    void testGetAndSetCoordinates(){
        double[][] cords = new double[][]{{1,2},{3,4}};
        EdgeWeightedGraph g = new EdgeWeightedGraph(2);
        g.setCoordinates(cords);
        assertEquals(cords, g.getCoordinates());
    }*/
}
