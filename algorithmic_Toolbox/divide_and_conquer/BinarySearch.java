import java.io.*;
import java.util.*;

public class BinarySearch {

    private static final int KEY_NOT_FOUND_INDEX = -1;
    /**
     * @param a A sequence of natural numbers
     * @param key A natural number 
     * @preconstraint 1 <= a.length <= 10^5
     * @preconstraint for any i in {0, 1, ..., 10^5}: 1 <= a[i] <= 10^9
     * @preconstraint key  1 <= a[i] <= 10^9
     * return an index i in {0, 1 , ..., a.length} such that a[i] = key 
     *        or -1 if there is no such index. 
     */
    static int binarySearch(int[] a, int key) {
        int left = 0;
        int right = a.length - 1;
        return binarySearch(a, key, left, right);
    }
    
    static int binarySearch(int[] a, int key, int lo, int hi)
    {
        if(lo > hi) return KEY_NOT_FOUND_INDEX;
        int mid = lo + (hi - lo) / 2;
        if (key < a[mid])
            return binarySearch(a, key, lo, mid - 1);
        else if(key > a[mid])
            return binarySearch(a, key, mid + 1, hi);
        else return mid;
    }


    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int m = scanner.nextInt();
        int[] b = new int[m];
        for (int i = 0; i < m; i++) {
          b[i] = scanner.nextInt();
        }
        for (int i = 0; i < m; i++) {
            System.out.print(binarySearch(a, b[i]) + " ");
        }
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
