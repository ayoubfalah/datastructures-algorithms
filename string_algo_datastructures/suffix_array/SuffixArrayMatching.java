package string_algo_datastructures.suffix_array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SuffixArrayMatching 
{
    
    class fastscanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        fastscanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextint() throws IOException {
            return Integer.parseInt(next());
        }
    }

    /**
     * 
     * @param pattern a string
     * @param text a string
     * @precondition pattern and text are defined only over {A, C, G, T}
     *               0 <= |pattern| <= 10^5
     *               1 <= |text| <= 10^5
     * @param suffixArray the suffix array of text
     * @return all starting positions in text where pattern appears as substring
     */
    public List<Integer> findOccurrences(String pattern, String text, int[] suffixArray) 
    {
        int index = binarySearch(text, suffixArray, pattern);
        ArrayList<Integer> result = new ArrayList<>();        
        if (index >= 0)
        {
            result.add(suffixArray[index]);
            
            int l = index - 1;
            while (l >= 0 && text.startsWith(pattern, suffixArray[l]))
            {                
                result.add(suffixArray[l]);
                l--;
            }
            
            int r = index + 1;
            int n = text.length();                
            while (r < n && text.startsWith(pattern, suffixArray[r])) 
            {                
                result.add(suffixArray[r]);
                r++;
            }            
        }        
        return result;
        
    }

    private int binarySearch(String text, int[] suffixArray, String pattern) 
    {
        int minIndex = 0;
        int maxIndex = text.length() - 1;
        int m = pattern.length();
        while (minIndex <= maxIndex)
        {
            int midIndex = (minIndex + maxIndex) / 2;
            int lcp = computeLCPFunction(text, pattern, suffixArray, midIndex);    
            if (m == lcp)
                return midIndex;
            else if (pattern.charAt(lcp) > text.charAt(suffixArray[midIndex] + lcp))
                minIndex = midIndex + 1;
            else if(pattern.charAt(lcp) < text.charAt(suffixArray[midIndex] + lcp))
                maxIndex = midIndex - 1;
            }
            return -1;
    }
    
    private int computeLCPFunction(String text, String pattern, 
            int[] suffixArray, int startIndex)
    {
        int m = pattern.length();
        int lcp = 0;
        int currentTxtSymbolIndex = suffixArray[startIndex] + lcp;
        while (lcp < m && 
                text.startsWith(String.valueOf(pattern.charAt(lcp)), 
                        currentTxtSymbolIndex))
        {
            lcp = lcp + 1;
            currentTxtSymbolIndex = suffixArray[startIndex] + lcp;
        }
        return lcp;
    }

    static public void main(String[] args) throws IOException {
        new SuffixArrayMatching().run();
    }

    public void print(boolean[] x) {
        for (int i = 0; i < x.length; ++i) {
            if (x[i]) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    public void run() throws IOException {
        fastscanner scanner = new fastscanner();
        String text = scanner.next() + "$";
        SuffixArrayLong suffixArrayCalculator = new SuffixArrayLong();
        int[] suffixArray = suffixArrayCalculator.computeSuffixArray(text);
        int patternCount = scanner.nextint();
        boolean[] occurs = new boolean[text.length()];
        for (int patternIndex = 0; patternIndex < patternCount; ++patternIndex)
        {
            String pattern = scanner.next();
            List<Integer> occurrences = findOccurrences(pattern, text, suffixArray);
            for (int x : occurrences) {
                occurs[x] = true;
            }
        }
        print(occurs);
    }
}
