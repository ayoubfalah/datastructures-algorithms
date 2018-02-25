package capstone_project;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class Pair {

    private final String a;
    private final String b;

    public Pair(String a, String b) {
        this.a = a;
        this.b = b;
    }

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    @Override
    public String toString() {
        return "(" + a + ", " + b + ")";
    }
}

/**
 *
 * @author ayoubfalah
 */
public class StringsOverlap 
{
    public static void main(String[] args) 
    {
        int threshold = 4;
        String[] reads = new String[]{"CGTACG", "TACGTA", "GTACGT", "ACGTAC",
            "GTACGA", "TACGAT"};
        Map<Pair, Integer> overlapGraph = constructOverlapGraph(reads, threshold);
        Set<Pair> keys = overlapGraph.keySet();
        
        for (Pair key : keys)
        {
            String edge = key.getA() + ", " + key.getB() + ", " 
                    + overlapGraph.get(key);
            System.out.println(edge);            
        }        
    }
    
    /**
     * 
     * @param reads a set of strings
     * @param threshold a natural number that will be used as a threshold value
     * @return The overlap graph for the set of reads
     */
    private static Map<Pair, Integer> constructOverlapGraph(String[] reads, int threshold)
    {
        Map<Pair, Integer> overlapGraph = new HashMap();
        int n = reads.length; 
        for (int i = 0; i < n; i++) 
        {
            for (int j = 0; j < n; j++) 
            {
                if (i != j)
                {
                    int l = longestOverlapLength(reads[i], reads[j]);
                    if (l >= threshold)
                        overlapGraph.put(new Pair(reads[i], reads[j]), l);
                }                
            }            
        }
        return overlapGraph;
    }
    
    /**
     * 
     * @param a a string
     * @param b a string
     * @return the length of the longest overlap between a and b
     */
    private static int longestOverlapLength(String a, String b)
    {
        int result = 0;
        int n = a.length();
        for (int i = 0; i < n; i++)
        {
            if (b.startsWith(a.substring(i))) 
            {
                result = n - i;
                break;
            }            
        }
        return result;
    }
    
    /**
     * 
     * @param longestOverlapLength a naural number that represents the length of
     *                           the longest overlap of two strings.
     * @param threshold a natural number that will be used as a threshold value
     * @return longestOverlapLength if (longestOverlapLength >= threshold)
     *          and zero otherwise.
     */
    private static int effectiveOverlapLength(int longestOverlapLength, int threshold)
    {
        return longestOverlapLength >= threshold? longestOverlapLength : 0;
    }
}