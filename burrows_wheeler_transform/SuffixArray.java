
import java.util.*;
import java.io.*;
import java.util.zip.CheckedInputStream;

public class SuffixArray {
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

    public class Suffix implements Comparable<Suffix> 
    {
        String suffix;
        int start;

        Suffix(String suffix, int start) {
            this.suffix = suffix;
            this.start = start;
        }

        @Override
        public int compareTo(Suffix that)
        {
            return this.suffix.compareTo(that.suffix);
        }
    }

    // Build suffix array of the string text and
    // return an int[] result of the same length as the text
    // in text where the i-th lexicographically smallest
    // suffix of text starts.
    public int[] computeSuffixArray(String text)
    {
        TreeSet<Suffix> suffixes = new TreeSet();
        int n = text.length();
        
        for (int i = 0; i < n; i++)
        {
            text =  text.charAt(n - 1) + text.substring(0, n - 1);
            int start = (n - 1) - i;
            Suffix suffix = new Suffix(text, start);
            suffixes.add(suffix);
        }
        
        int[] suffixArray = computeSuffixArray(suffixes);
        return suffixArray;
    }

    private int[] computeSuffixArray(TreeSet<Suffix> suffixes) {
        Iterator<Suffix> sfxItr = suffixes.iterator();
        int[] sfxArray = new int[suffixes.size()];
        int index = 0;
        while (sfxItr.hasNext())
        {
            Suffix suffix = sfxItr.next();
            sfxArray[index] = suffix.start;
            index++;
        }
        return sfxArray;
    }


    static public void main(String[] args) throws IOException {
        new SuffixArray().run();
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
        int[] SuffixArray = computeSuffixArray(text);
        print(SuffixArray);
    }
}
