import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.awt.Color;

/**
 * This class solves a clustering problem with the Prim algorithm.
 */
public class Clustering {
	EdgeWeightedGraph G;
	List <List<Integer>>clusters; 
	List <List<Integer>>labeled; 
	
	/**
	 * Constructor for the Clustering class, for a given EdgeWeightedGraph and no labels.
	 * @param G a given graph representing a clustering problem
	 */
	public Clustering(EdgeWeightedGraph G) {
            this.G=G;
	    clusters= new LinkedList <List<Integer>>();
	}
	
    /**
	 * Constructor for the Clustering class, for a given data set with labels
	 * @param in input file for a clustering data set with labels
	 */
	public Clustering(In in) {
            int V = in.readInt();
            int dim= in.readInt();
            G= new EdgeWeightedGraph(V);
            labeled=new LinkedList <List<Integer>>();
            LinkedList labels= new LinkedList();
            double[][] coord = new double [V][dim];
            for (int v = 0;v<V; v++ ) {
                for(int j=0; j<dim; j++) {
                	coord[v][j]=in.readDouble();
                }
                String label= in.readString();
                    if(labels.contains(label)) {
                    	labeled.get(labels.indexOf(label)).add(v);
                    }
                    else {
                    	labels.add(label);
                    	List <Integer> l= new LinkedList <Integer>();
                    	labeled.add(l);
                    	labeled.get(labels.indexOf(label)).add(v);
                    	System.out.println(label);
                    }                
            }
             
            G.setCoordinates(coord);
            for (int w = 0; w < V; w++) {
                for (int v = 0;v<V; v++ ) {
                	if(v!=w) {
                	double weight=0;
                    for(int j=0; j<dim; j++) {
                    	weight= weight+Math.pow(G.getCoordinates()[v][j]-G.getCoordinates()[w][j],2);
                    }
                    weight=Math.sqrt(weight);
                    Edge e = new Edge(v, w, weight);
                    G.addEdge(e);
                	}
                }
            }
	    clusters= new LinkedList <List<Integer>>();
	}
	
    /**
	 * This method finds a specified number of clusters based on a MST.
	 *
	 * It is based in the idea that removing edges from a MST will create a
	 * partition into several connected components, which are the clusters.
	 * @param numberOfClusters number of expected clusters
	 */
	public void findClusters(int numberOfClusters){
		PriorityQueue <Edge> pq= new PriorityQueue <Edge>();
		boolean[] marked = new boolean [G.V()];
		int[] parent = new int [G.V()];

		marked[0]=true;
		for (Edge e : G.adj(0)) {
			int w = e.other(0);
			if(!marked[w]) {
				pq.add(e);
			}
		}

		int connectedComponents=0;
		while(!pq.isEmpty() && connectedComponents<numberOfClusters) {
			Edge e = pq.poll();
			int v = e.either();
			int w = e.other(v);
			if(!marked[w]) {
				parent[w]=v;
				marked[w]=true;
				connectedComponents++;
				for (Edge e2 : G.adj(w)) {
					int w2 = e2.other(w);
					if(!marked[w2]) {
						pq.add(e2);
					}
				}
			} else if(!marked[v]) {
				parent[v]=w;
				marked[v]=true;
				for (Edge e2 : G.adj(v)) {
					int w2 = e2.other(v);
					if(!marked[w2]) {
						pq.add(e2);
					}
				}
			}
		}

		clusters=new LinkedList <List<Integer>>();
		for (int v=0; v<G.V(); v++) {
			if(marked[v]) {
				int w = v;
				List <Integer> cluster = new LinkedList <Integer>();
				while(w!=parent[w]) {
					cluster.add(w);
					w=parent[w];
				}
				cluster.add(w);
				Collections.sort(cluster);
				clusters.add(cluster);
			}
		}
	}
	
	/**
	 * This method finds clusters based on a MST and a threshold for the coefficient of variation.
	 *
	 * It is based in the idea that removing edges from a MST will create a
	 * partition into several connected components, which are the clusters.
	 * The edges are removed based on the threshold given. For further explanation see the exercise sheet.
	 *
	 * @param threshold for the coefficient of variation
	 */
	public void findClusters(double threshold){
		PriorityQueue <Edge> pq= new PriorityQueue <Edge>();
		boolean[] marked = new boolean [G.V()];
		int[] parent = new int [G.V()];

		marked[0]=true;
		for (Edge e : G.adj(0)) {
			int w = e.other(0);
			if(!marked[w]) {
				pq.add(e);
			}
		}

		int connectedComponents=0;
		while(!pq.isEmpty() && connectedComponents<G.V()) {
			Edge e = pq.poll();
			int v = e.either();
			int w = e.other(v);
			if(!marked[w]) {
				parent[w]=v;
				marked[w]=true;
				connectedComponents++;
				List <Edge> part = new LinkedList <Edge>();
				part.add(e);
				for (Edge e2 : G.adj(w)) {
					int w2 = e2.other(w);
					if(!marked[w2] && parent[w2]!=v) {
						pq.remove(e2);
						part.add(e2);
					}
				}
				double coefficientOfVariation = coefficientOfVariation(part);
				if(coefficientOfVariation>threshold) {
					for (Edge e2 : part) {
						pq.add(e2);
					}
				}
			} else if(!marked[v]) {
				parent[v]=w;
				marked[v]=true;
				for (Edge e2 : G.adj(v)) {
					int w2 = e2.other(v);
					if(!marked[w2]) {
						pq.add(e2);
					}
				}
			}
		}

		clusters=new LinkedList <List<Integer>>();
		for (int v=0; v<G.V(); v++) {
			if(marked[v]) {
				int w = v;
				List <Integer> cluster = new LinkedList <Integer>();
				while(w!=parent[w]) {
					cluster.add(w);
					w=parent[w];
				}
				cluster.add(w);
				Collections.sort(cluster);
				clusters.add(cluster);
			}
		}
	}
	
	/**
	 * Evaluates the clustering based on a fixed number of clusters.
	 * @return array of the number of the correctly classified data points per cluster
	 */
	public int[] validation() {
		int[] result = new int[clusters.size()];
		for (int i = 0; i < G.V(); i++) {
			boolean found = false;
			for (int j = 0; j < clusters.size() && !found; j++) {
				if (clusters.get(j).contains(i)) {
					result[j]++;
					found = true;
				}
			}
		}
		return result;
	}
	
	/**
	 * Calculates the coefficient of variation.
	 * For the formula see exercise sheet.
	 * @param part list of edges
	 * @return coefficient of variation
	 */
	public double coefficientOfVariation(List <Edge> part) {
		if (part.size() == 0) {
			return 0.0;
		}
		System.out.println("Part size: " + part.size());
		double sum = 0;
		double sumxSquared = 0;
		for(Edge e: part) {
			sum += (double)e.weight();
			sumxSquared += (double)Math.pow(e.weight(),2);
			System.out.println("Edge weight: " + e.weight() + ", sum: " + sum + ", sumxSquared: " + sumxSquared);
		}
		double faktor = (double)1/(double)part.size();
		double zähler = Math.sqrt(faktor*sumxSquared - Math.pow(faktor*sum,2));
		double nenner = faktor*sum;
		System.out.println("Sum: " + sum + ", sumxSquared: " + sumxSquared + ", zähler: " + zähler + ", nenner: " + nenner);
		double coefficientOfVariation = zähler/nenner;
		System.out.println("Coefficient of variation: " + coefficientOfVariation);
		return coefficientOfVariation;
	}
	
	/**
	 * Plots clusters in a two-dimensional space.
	 */
	public void plotClusters() {
		int canvas=800;
	    StdDraw.setCanvasSize(canvas, canvas);
	    StdDraw.setXscale(0, 15);
	    StdDraw.setYscale(0, 15);
	    StdDraw.clear(new Color(0,0,0));
		Color[] colors= {new Color(255, 255, 255), new Color(128, 0, 0), new Color(128, 128, 128), 
				new Color(0, 108, 173), new Color(45, 139, 48), new Color(226, 126, 38), new Color(132, 67, 172)};
	    int color=0;
		for(List <Integer> cluster: clusters) {
			if(color>colors.length-1) color=0;
		    StdDraw.setPenColor(colors[color]);
		    StdDraw.setPenRadius(0.02);
		    for(int i: cluster) {
		    	StdDraw.point(G.getCoordinates()[i][0], G.getCoordinates()[i][1]);
		    }
		    color++;
	    }
	    StdDraw.show();
	}
	

    public static void main(String[] args) {
		// FOR TESTING
    }
}

