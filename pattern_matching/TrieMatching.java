import java.io.*;
import java.util.*;

class Node
{
	public static final int Letters =  4;
	public static final int NA      = -1;
	public int next [];

	Node ()
	{
		next = new int [Letters];
		Arrays.fill (next, NA);
	}
}

public class TrieMatching implements Runnable 
{
	int letterToIndex (char letter)
	{
		switch (letter)
		{
			case 'A': return 0;
			case 'C': return 1;
			case 'G': return 2;
			case 'T': return 3;
			default: assert (false); return Node.NA;
		}
	}

        /**
         * 
         * @param text a reference Text
         * @param n The number of elements in patterns
         * @param patterns a List of patterns that will be matched against the
         *                 reference Text text
         * @precondition 1 <= |text| <= 10000
         *               1 <= n <= 5000
         *               for each pattern in patterns 1<= |pattern| <= 100
         *               for each c_1 in text, c_2 in pattern:
         *                        c_1, c_2 in {A, C, G, T}
         *               for each i, j in {1, 2, ..., n}: i != j 
         *                       => patterns[i] is not prefix of patterns[j]
         *                         and patterns[j] is not prefix of patterns[i]
         * @return All valid shifts with which a pattern in patterns occurs in 
         *         text
         */
	List <Integer> solve (String text, int n, List <String> patterns) {
		List <Integer> result = new ArrayList();

		// write your code here

		return result;
	}

	public void run () {
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
			String text = in.readLine ();
		 	int n = Integer.parseInt (in.readLine ());
		 	List <String> patterns = new ArrayList <String> ();
			for (int i = 0; i < n; i++) {
				patterns.add (in.readLine ());
			}

			List <Integer> ans = solve (text, n, patterns);

			for (int j = 0; j < ans.size (); j++) {
				System.out.print ("" + ans.get (j));
				System.out.print (j + 1 < ans.size () ? " " : "\n");
			}
		}
		catch (Throwable e) {
			e.printStackTrace ();
			System.exit (1);
		}
	}

	public static void main (String [] args) {
		new Thread (new TrieMatching ()).start ();
	}
}
