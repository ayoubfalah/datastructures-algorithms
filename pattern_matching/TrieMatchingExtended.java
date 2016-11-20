package pattern_matching;

import java.io.*;
import java.util.*;

class TrieMatchingExtended implements Runnable
{
    private static final int  INVALID_SHIFT       = -1;
    private static final int  SUBTEXT_BEGIN_INDEX = 1;
    private static final char PATTERN_END_INDICATOR  = '$';
    
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

    public void run () 
    {
        try {
                BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
                String text = in.readLine ();
                int n = Integer.parseInt (in.readLine ());
                List <String> patterns = new ArrayList();
                for (int i = 0; i < n; i++) {
                    String pattern = in.readLine () + PATTERN_END_INDICATOR;
                    patterns.add (pattern);
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
            new Thread (new TrieMatchingExtended()).start ();
    }

    private List<Integer> trieMatching(String text, List<Map<Character, Integer>> trie)
    {
        int wholeTextlength = text.length();
        List <Integer> result = new ArrayList();
        while (!(text.isEmpty()))
        {
            int subResult = prefixTrieMatching(text, trie);
            boolean isValidShift = subResult >= 0; 
            if (isValidShift) 
            {
                int validShift = wholeTextlength - subResult;                
                result.add(validShift);                
            }
            text = removeFirstSymbol(text);
        }
        return result;
    }

    private String removeFirstSymbol(String text) 
    {
        text = text.substring(SUBTEXT_BEGIN_INDEX);
        return text;
    }

    private int prefixTrieMatching(String text, List<Map<Character, Integer>> trie)
    {
        int currentTextSymbolIndex = 0;
        char currentSymbol = text.charAt(currentTextSymbolIndex);
        int rootIndex = 0;
        Map<Character, Integer> currentNode = trie.get(rootIndex);

        while (true)
        {
            // The currentNode is a leaf in trie
            boolean isCurrentNodeALeaf = currentNode.isEmpty();
            boolean patternEnd = currentNode.containsKey(PATTERN_END_INDICATOR);
            // Is there an edge in trie labeled by current symbol
            boolean edgeWithCurrentSymbol = currentNode.containsKey(currentSymbol);
            if (isCurrentNodeALeaf || patternEnd)
            {
                return text.length();
            }
            else if (edgeWithCurrentSymbol) 
            {
                Integer currentNodeIndex = currentNode.get(currentSymbol);
                currentNode = trie.get(currentNodeIndex);
                isCurrentNodeALeaf = currentNode.isEmpty();
                patternEnd = currentNode.containsKey(PATTERN_END_INDICATOR);
                if (isCurrentNodeALeaf || patternEnd)
                {
                    return text.length();
                }
                ++currentTextSymbolIndex;
                boolean hasNextLetter = currentTextSymbolIndex < text.length();
                if (hasNextLetter)
                    currentSymbol = text.charAt(currentTextSymbolIndex);
                else
                {
                    // The length of pattern is larger than the length of
                    // the text
                    return INVALID_SHIFT;
                }                    
            }
            else return INVALID_SHIFT;            
        }
    }
}
