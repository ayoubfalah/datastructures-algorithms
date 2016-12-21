package string_algo_datastructures.suffix_array;

import java.util.*;
import java.io.*;
import java.util.zip.CheckedInputStream;

public class SuffixArrayLong 
{
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

    // Build suffix array of the string text and
    // return an int[] result of the same length as the text
    // such that the value result[i] is the index (0-based)
    // in text where the i-th lexicographically smallest
    // suffix of text starts.
    public int[] computeSuffixArray(String text) 
    {
        char[] chars = text.toCharArray();
        int[] orders = sortCharacters(chars);
        int [] classes = computeCharClasses(chars, orders);
        int l = 1;
        while (l < chars.length)
        {     
            orders = sortDoubled(chars, l, orders, classes);
            classes = updateClasses(orders, classes, l);
            l *= 2;            
        }

        return orders;
    }
    
    private int[] sortCharacters(char[] chars)
    {
        // ∑ = {A, ..., T} U {$} => |∑| = 85
        int AlphabetLenth = 85;
        int[] orders = new int[chars.length];
        int[] count = new int[AlphabetLenth];
        
        for (int i = 0; i < chars.length; i++)
        {
            count[chars[i]] = count[chars[i]] + 1;            
        }
        for (int j = 1; j < AlphabetLenth; j++) 
        {
            count[j] = count[j] + count[j - 1];            
        }
        for (int k = chars.length - 1; k >= 0 ; k--)
        {
            int c = chars[k];
            count[c] = count[c] - 1;
            orders[count[c]] = k;            
        }
        return orders;
    }
    
    private int[] computeCharClasses(char[] chars, int[] orders)
    {
        int[] classes = new int[chars.length];
        classes[orders[0]] = 0;
        for (int i = 1; i < chars.length; i++)
        {
            if (chars[orders[i]] != chars[orders[i - 1]]) 
            {
                classes[orders[i]] = classes[orders[i - 1]] + 1;                
            }else
            {
                classes[orders[i]] = classes[orders[i - 1]];                
            }            
        }
        return classes;
    }
    /**
     * 
     * @param chars
     * @param l
     * @param orders
     * @param classes
     * @return 
     */
    private int[] sortDoubled(char[] chars, int l, int[] orders, int[] classes)
    {
        int[] count = new int[chars.length];
        int[] newOrders = new int[chars.length];
        for (int i = 0; i < chars.length; i++) 
        {
            count[classes[i]] = count[classes[i]] + 1;            
        }
        for (int j = 1; j < chars.length; j++) 
        {
            count[j] = count[j] + count[j - 1];            
        }
        for (int k = chars.length - 1; k >= 0; k--)
        {
            int start = (orders[k] - l + chars.length) % chars.length;
            int cl = classes[start];
            count[cl] = count[cl] - 1;
            newOrders[count[cl]] = start;
        }
        return newOrders;
    }
    
    /**
     * 
     * @param orders
     * @param classes
     * @param l
     * @return 
     */
    private int[] updateClasses(int[] orders, int[] classes, int l) 
    {
        int n = orders.length;
        int[] newClasses = new int[n];
        newClasses[orders[0]] = 0;
        for (int i = 1; i < n; i++)
        {
            int cur = orders[i] % n;
            int prev = orders[i - 1];
            int mid = (cur + l) % n;
            int midPrev = (prev + l) % n;
            if ((classes[cur] != classes[prev]) ||
                (classes[mid] != classes[midPrev]))
            {
                newClasses[cur] = newClasses[prev] + 1;                
            }else
            {
                newClasses[cur] = newClasses[prev]; 
            }
            
        }
        return newClasses;
    }


    static public void main(String[] args) throws IOException {
        new SuffixArrayLong().run();
    }

    public void print(int[] x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        int[] suffix_array = computeSuffixArray(text);
        print(suffix_array);
    }
}
