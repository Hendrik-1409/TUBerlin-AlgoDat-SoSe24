import java.util.LinkedList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PermutationTest {
	PermutationVariation p1;
	PermutationVariation p2;
	public int n1;
	public int n2;
	int cases=1;
	
	void initialize() {
		n1=4;
		n2=6;
		Cases c= new Cases();
		p1= c.switchforTesting(cases, n1);
		p2= c.switchforTesting(cases, n2);
	}
	

	@Test
	void testPermutation() {
		initialize();
		PermutationVariation[] cases = new PermutationVariation[] {p1, p2};
		int[] allN = new int[] {n1, n2};
		testPermutationGeneral(cases, allN);
	}

	void testPermutationGeneral(PermutationVariation[] cases, int[] allN) {
		for (int i = 0; i < allN.length; i++) {
			int n = allN[i];
			PermutationVariation p = cases[i];
			assert p.original.length == n : "Error - Die Folge der Permutation hat nicht die richtige Länge";
			assert p.allDerangements.isEmpty() : "Error - allDerangements sollte leer sein";
			for (int j = 0; j < p.original.length; j++) {
				for (int j2 = 0; j2 < p.original.length; j2++) {
					if (j == j2) {
						continue;
					}
					assert !(p.original[j] == p.original[j2]) : "Error - Ein Element sollte nur einmal vorkommen";
				}
			}
		}
	}

	@Test
	void testDerangements() {
		initialize();
		//in case there is something wrong with the constructor
		fixConstructor();
		// TODO
	}
	
	@Test
	void testsameElements() {
		initialize();
		//in case there is something wrong with the constructor
		fixConstructor();
		// TODO
	}
	
	void setCases(int c) {
		this.cases=c;
	}
	
	public void fixConstructor() {
		//in case there is something wrong with the constructor
		p1.allDerangements=new LinkedList<int[]>();
		for(int i=0;i<n1;i++)
			p1.original[i]=2*i+1;
		
		p2.allDerangements=new LinkedList<int[]>();
		for(int i=0;i<n2;i++)
			p2.original[i]=i+1;
	}
}


