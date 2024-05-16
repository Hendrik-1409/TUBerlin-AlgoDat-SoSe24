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
			assert p.allDerangements != null : "Error - allDerangements sollte nicht null sein";
			assert p.allDerangements.equals(new LinkedList<int[]>()) : "Error - allDerangements sollte leer sein";
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
		PermutationVariation[] cases = new PermutationVariation[] {p1, p2};
		int[] allN = new int[] {n1, n2};
		testDerangementsGeneral(cases, allN);
	}

	void testDerangementsGeneral(PermutationVariation[] cases, int[] allN) {
		for (int i = 0; i < allN.length; i++) {
			int n = allN[i];
			PermutationVariation p = cases[i];
			assert p.allDerangements.size() == subfakultät(n) : "Error - Anzahl der Permutationen stimmt nicht";
			for (int[] thisDerangement : p.allDerangements) {
				for (int j = 0; j < thisDerangement.length; j++) {
					assert !(thisDerangement[j] == p.original[j]) : "Error - ein Element darf vnicht an der selben stelle sein - fixpunktkriterium nicht erfuellt";
				}
			}
		}
	}

	/**
	 * A method to calculate the subfactorial of a given integer.
	 *
	 * @param  n  the integer for which subfactorial is calculated
	 * @return    the subfactorial value of the input integer
	 */
	int  subfakultät(int n) {
		int result = 0;
		for (int i = 0; i < n; i++) {
			result += Math.pow(-1, i) / fakultät(i);
		}
		result *= fakultät(n);
		return result;
	}

	/**
	 * A method to calculate the factorial of a given integer.
	 *
	 * @param  n  the integer for which factorial is calculated
	 * @return    the factorial value of the input integer
	 */
	int fakultät(int n) {
		int result = 1;
		for (int i = 1; i <= n; i++) {
			result *= i;
		}
		return result;
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


