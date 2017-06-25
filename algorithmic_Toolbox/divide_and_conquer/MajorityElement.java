import java.util.*;
import java.io.*;

public class MajorityElement {
    
    /**
     * 
     * @param a a sequence of natural numbers
     * @preconstraint 1 <= a.length <= 10^5
     * @preconstraint for any i in {0, 1, ..., (10^5 - 1)}: 0 <= a[i] <= 10^9
     * @return 1 if the sequence a contains an element that appears strictly more
     *           that n/2 times, and 0 otherwise.
     */
    public static int hasMajorityElement(int[] a)
    {
        Arrays.sort(a);
        int index = 0;
        int n = a.length;
        
        while(index < (n-1))
        {
            int count = countOccurrences(a, index);
            if (count > n/2)
            {
                return 1;                
            }else index += count;
        }
        return 0;
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
        System.out.println(hasMajorityElement(a));
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

