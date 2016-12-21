package string_algo_datastructures.suffix_array;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import knuth_morris_pratt.KnuthMorrisPratt;

/**
 *
 * @author ayoubfalah
 */
public class StressTester 
{
    public static void main(String[] args) 
    {
        label: while(true)
        {
            int n = Math.abs((new Random()).nextInt()) % 10 + 2;
            System.out.println(n);
            char[] alphabet = { 'A', 'C', 'G', 'T'};
            
            String pattern = generateRandomString(alphabet, 4);
            String text = generateRandomString(alphabet, 6);
            System.out.println("Pattern: " + pattern);
            System.out.println("Text: " + text);
            System.out.println("");
            
            KnuthMorrisPratt kMP = new KnuthMorrisPratt();
            List<Integer> res1 = kMP.findPattern(pattern, text);
            Collections.sort(res1);
            
            SuffixArrayLong suffixArrayLong = new SuffixArrayLong();
            int[] suffixArray = suffixArrayLong.computeSuffixArray(text+"$");
            SuffixArrayMatching suffixArrayMatching = new SuffixArrayMatching();
            List<Integer> res2 = suffixArrayMatching.
                    findOccurrences(pattern, text+"$", suffixArray);
            Collections.sort(res2);
            for (int i = 0; i < res1.size(); i++)
            {
                if ((res1.isEmpty() && !res2.isEmpty()) || 
                    (res2.isEmpty() && !res1.isEmpty())) 
                {
                    System.out.println("Wrong answer: KMP size:= " + res1.size() 
                            + ", SA size:= " + res2.size());
                    break label;                    
                }
                if (!res1.get(i).equals(res2.get(i)))
                {
                    System.out.println("Wrong answer: " + res1.get(i) + " " + res2.get(i));
                    break label;
                }
            }
            System.out.println("OK");            
        }       
    }

    private static String generateRandomString(char[] alphabet, int n) 
    {
        String pattern = "";
        for (int j = 0; j < n; j++)
        {
            char symbol = alphabet[Math.abs((new Random()).nextInt()) % alphabet.length];
            pattern += symbol;
        }
        return pattern;
    }    
}
