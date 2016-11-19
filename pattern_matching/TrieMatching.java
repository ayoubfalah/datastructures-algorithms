import java.io.*;
import java.util.*;
import pattern_matching.HashTableTrie;

public class TrieMatching implements Runnable 
{
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
	List <Integer> solve (String text, int n, List <String> patterns) 
        {
            
            List<Map<Character, Integer>> trie = (new HashTableTrie()).
                    buildTrie(patterns.toArray(new String[patterns.size()]));
            
            List <Integer> result = trieMatching(text, trie);

            return result;
	}

	public void run () {
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
			String text = in.readLine ();
		 	int n = Integer.parseInt (in.readLine ());
		 	List <String> patterns = new ArrayList();
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

    private List<Integer> trieMatching(String text, List<Map<Character, Integer>> trie)
    {
        int txtlength = text.length();
        List <Integer> result = new ArrayList();
        while (!(text.isEmpty()))
        {
            int subResult = prefixTrieMatching(text, trie);
            if (subResult >= 0) 
            {
                result.add(txtlength - subResult);                
            }
            text = text.substring(1);
        }
        return result;
    }

    private int prefixTrieMatching(String text, List<Map<Character, Integer>> trie)
    {
            int currentTextSymbolIndex = 0;
            char currentSymbol = text.charAt(currentTextSymbolIndex);
            Map<Character, Integer> currentNode = trie.get(0);
            while (true)
            {
                // The currentNode is a leaf in trie
                if (currentNode.size() == 0)
                {
                    return text.length();
                }
                else if (currentNode.containsKey(currentSymbol)) 
                {
                    Integer currentNodeIndex = currentNode.get(currentSymbol);
                    currentNode = trie.get(currentNodeIndex);
                    if (currentNode.size() == 0)
                    {
                        return text.length();
                    }
                    if (++currentTextSymbolIndex < text.length())
                        currentSymbol = text.charAt(currentTextSymbolIndex);
                    else
                    {
                        // The length of pattern is larger than the length of
                        // the text
                        return -1;
                    }
                    
                }
                else return -1;
            
            }
    }
}
