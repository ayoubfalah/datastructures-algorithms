package knuth_morris_pratt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class KnuthMorrisPratt {

    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    // Find all the occurrences of the pattern in the text and return
    // a list of all positions in the text (starting from 0) where
    // the pattern starts in the text.
    public List<Integer> findPattern(String pattern, String text) 
    {
        String ptrnTxt = pattern + '$' + text;
        int[] s = computePrefixFunction(ptrnTxt);
        ArrayList<Integer> result = new ArrayList();
        int m = pattern.length();
        int n = ptrnTxt.length();
        for (int i = m + 1; i < n ; i++) 
        {
            if (s[i] == m)
            {
                // Position, where pattern starts in text
                int shift = i - 2*m;                
                result.add(shift);                
            }            
        }        
        return result;
    }
    
    /**
     * 
     * @param text a string
     * @percondition for any symbol in text: symbol in {$, A, C, G, T}
     *               1 <= |text| <= 2*10^6
     * @return An array s of size |text|, so that for any i from 0 to (|text| - 1)
     *         s[i] is the length of the longest border of text[0..i]
     */
    private int[] computePrefixFunction(String text) 
    {
        int n = text.length();
        int[] s = new int[n];
        s[0] = 0;
        int border = 0;
        for (int i = 1; i < n; i++)
        {
            char currentChar = text.charAt(i);
            while ((border > 0) && (currentChar != text.charAt(border)))
            {                
                border = s[border - 1];
            }
            if (currentChar == text.charAt(border))
            {
                border = border + 1;                
            }else border = 0;
            
            s[i] = border;            
        }
        return s;
    }

    static public void main(String[] args) throws IOException {
        new KnuthMorrisPratt().run();
    }

    public void print(List<Integer> x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String pattern = scanner.next();
        String text = scanner.next();
        List<Integer> positions = findPattern(pattern, text);
        print(positions);
    }
}
