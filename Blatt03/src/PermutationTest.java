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
	
	/**
	* A method to test permutations. The constructor of the Permutation classes is tested. 
	* The variable `original` must be initialized in the constructor with a sequence of the given length 
	* where no number occurs twice. Furthermore, `allDerangements` must be initialized with an empty list.
	*
	*/
	@Test
	void testPermutation() {
		initialize();
		PermutationVariation[] cases = new PermutationVariation[] {p1, p2};
		int[] allN = new int[] {n1, n2};
		testPermutationGeneral(cases, allN);
	}

	/**
	 * A method to test permutations. The function checks if the original sequence of the permutation has the correct length, 
	 * if allDerangements is not null and if it is an empty list. Additionally, it ensures that each element in the original 
	 * sequence appears only once, satisfying the condition of a derangement.
	 * Helps {@link #PermutationTest()}
	 *
	 * @param  cases  an array of PermutationVariation objects representing different permutation cases
	 * @param  allN   an array of integers representing the lengths of the permutations
	 */
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
	
	/**
	 * A method to test the derangements of the permutation classes. This method generates all
	 * fixed-point-free permutations and stores them in `allDerangements`. In this test, it is checked
	 * that the number of generated derangements is correct, and that each derangement fulfills the property of being fixed-point-free.
	 * (Whether they are actually permutations is checked separately in the next subtask.)
	 * 
	 * https://de.wikipedia.org/wiki/Fixpunktfreie_Permutation
	 */
	@Test
	void testDerangements() {
		initialize();
		//in case there is something wrong with the constructor
		fixConstructor();
		PermutationVariation[] cases = new PermutationVariation[] {p1, p2};
		int[] allN = new int[] {n2, n1};
		testDerangementsGeneral(cases, allN);
	}

	void testDerangementsGeneral(PermutationVariation[] cases, int[] allN) {
		for (int i = 0; i < allN.length; i++) {
			int n = allN[i];
			PermutationVariation p = cases[i];
			assert p.allDerangements.size() == subfakultät(n): "Error - Anzahl der Permutationen stimmt nicht";
			for (int[] thisDerangement : p.allDerangements) {
				for (int j = 0; j < thisDerangement.length; j++) {
					assert thisDerangement[j] != p.original[j] : "Error - ein Element darf nicht an der selben stelle sein - fixpunktkriterium nicht erfuellt";
				}
			}
		}
	}

	/**
	 * A method to calculate the subfactorial of a given integer.
	 * https://de.wikipedia.org/wiki/Subfakult%C3%A4t
	 *
	 * @param  n  the integer for which subfactorial is calculated
	 * @return    the subfactorial value of the input integer
	 */
	int  subfakultät(int n) {
		double result = 0;
		for (int i = 0; i <= n; i++) {
			result += Math.pow(-1, i) / fakultät(i);
		}
		result *= fakultät(n);
		return (int) Math.round(result);
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
		PermutationVariation[] cases = new PermutationVariation[] {p1, p2};
		int[] allN = new int[] {n1, n2};
		testDerangementsGeneral(cases, allN);
		testsameElementsGeneral(cases);
	}

	void testsameElementsGeneral(PermutationVariation[] cases) {
		for (int i = 0; i < cases.length; i++) {
			PermutationVariation p = cases[i];
			assert !(p.allDerangements.isEmpty()) : "Error - allDerangements sollte nicht leer sein";
			for (int[] thisDerangement : p.allDerangements) {
				for (int j = 0; j < thisDerangement.length; j++) {
					boolean searchHelper = false;
					for (int k = 0; k < p.original.length; k++) {
						if (thisDerangement[j] == p.original[k]) {
							searchHelper = true;
							break;
						}
					}
					assert searchHelper : "Error - Element nicht in Originalliste gefunden";
					for (int k = 0; k < thisDerangement.length; k++) {
						if (j == k) {
							continue;
						}
						assert thisDerangement[j] != thisDerangement[k] : "Error - Ein Element darf nicht zeimal vorkommen";
					}
				}
			}
		}
	}
	
	/**
	 * Setter method to set the value of cases.
	 *
	 * @param  c  the value to set for cases
	 */
	void setCases(int c) {
		this.cases=c;
	}
	
	/**
	 * A method to fix the constructor in case there is something wrong with it.
	 */
	public void fixConstructor() {
		//in case there is something wrong with the constructor
		p1.allDerangements=new LinkedList<int[]>();
		for(int i=0;i<n1;i++)
			p1.original[i]=2*i+1;
		
		p2.allDerangements=new LinkedList<int[]>();
		for(int i=0;i<n2;i++)
			p2.original[i]=i+1;
	}
	/**
	 * Some parts of this Javadoc may be generated by Codeium AI https://codeium.com/
	 */
}


