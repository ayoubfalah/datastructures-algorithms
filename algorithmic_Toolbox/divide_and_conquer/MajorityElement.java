package algorithmic_Toolbox.divide_and_conquer;

import java.util.*;
import java.io.*;

public class MajorityElement 
{
    private static final int HASNOT_MAJORITY_ELEMENT = 0;
    private static final int HAS_MAJORITY_ELEMENT = 1;
    
    /**
     * 
     * @param sequence a sequence of natural numbers
     * @preconstraint 1 <= a.length <= 10^5
     * @preconstraint for any i in {0, 1, ..., (10^5 - 1)}: 0 <= a[i] <= 10^9
     * @return 1 if the sequence a contains an element that appears strictly more
     *           that n/2 times, and 0 otherwise.
     */
    public static int hasMajorityElement(int[] sequence)
    {
        Arrays.sort(sequence);
        int index = 0;
        int n = sequence.length;
        
        while(index < (n-1))
        {
            int count = countOccurrences(sequence, index);
            if (count > n/2)
                return HAS_MAJORITY_ELEMENT;
            else index += count;
        }
        return HASNOT_MAJORITY_ELEMENT;
    }
    
    /**
     * 
     * @param sequence a sequence of natural numbers
     * @preconstraint 1 <= a.length <= 10^5
     * @preconstraint for any i in {0, 1, ..., (10^5 - 1)}: 0 <= a[i] <= 10^9
     * @return 1 if the sequence a contains an element that appears strictly more
     *           that n/2 times, and 0 otherwise.
     */
    public static int hasMajorityElementFaster(int[] sequence)
    {
        int n = sequence.length;
        if (n == 1) return HASNOT_MAJORITY_ELEMENT;
        Map<Integer, Integer> itemOccurrences = new HashMap();
        // Setting the occurrences to 0s
        for (int item : sequence) itemOccurrences.put(item, 0);
        
        for (int item : sequence) 
        {
            Integer occurrence = itemOccurrences.get(item);
            itemOccurrences.put(item, ++occurrence);            
        }        
        Collection<Integer> occurrences = itemOccurrences.values();
        Integer maxOccurrence = Collections.max(occurrences);
        
        return (maxOccurrence > n/2)? 
                HAS_MAJORITY_ELEMENT : HASNOT_MAJORITY_ELEMENT;
    }
    
    /**
     * 
     * @param a a sequence of natural numbers
     * @preconstraint 1 <= a.length <= 10^5
     * @preconstraint for any i in {0, 1, ..., (10^5 - 1)}: 0 <= a[i] <= 10^9
     * @param keyIndex an index of an element in a.
     * @return the number of occurences of a[keyIndex] in the sequence a
     */
    private static int countOccurrences(int[] a, int keyIndex)
    {
        int itemToCount = a[keyIndex];
        int count = 1;
        int i = keyIndex + 1;
        while ((i < a.length) && (a[i] == itemToCount)) {            
            count++;
            i++;
        }        
        return count;
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        System.out.println(hasMajorityElementFaster(a));
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}

