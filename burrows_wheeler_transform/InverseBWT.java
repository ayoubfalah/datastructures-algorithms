import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class InverseBWT {

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

    /**
     * 
     * @param bwt A Burrows-Wheeler Transfrom
     * @precondition bwt is defined over the alphabet {A, C, G, T}
     *               bwt contains exaclty one $ sign
     *               1 <= |bwt| - |$| <= 10^6
     * @return The invert of bwt
     */
    String inverseBWT(String bwt) 
    {        
        HashMap<Character, Integer> mem = occs(bwt);        
        int[] ranks = count(bwt);
        
        StringBuilder text = new StringBuilder("$");
        int i = 0;
        char currentSymbol = bwt.charAt(i);        
        while (currentSymbol != '$')
        {
            text = text.insert(0, currentSymbol);
            i = Math.addExact(mem.get(currentSymbol), ranks[i]);
            currentSymbol = bwt.charAt(i);
        }        
        return text.toString();
    }

    /**
     * 
     * @param bwt A Burrows-Wheeler Transform
     * @return For any s in {A, C, G, T}: occs(bwt) computes the number of symbols
     *         in bwt that are lexically smaller than s.
     */
    private HashMap<Character, Integer> occs(String bwt)
    {
        String firstColumn = sort(bwt);
        HashMap<Character, Integer> mem = new HashMap();
        mem.put('A', firstColumn.indexOf('A'));
        mem.put('C', firstColumn.indexOf('C'));
        mem.put('G', firstColumn.indexOf('G'));
        mem.put('T', firstColumn.indexOf('T'));
        return mem;
    }
    
    private String sort(String bwt) 
    {
        char[] symbols = bwt.toCharArray();
        Arrays.sort(symbols);        
        return new String(symbols);
    }
    
    /**
     * 
     * @param bwt
     * @return The rank of symbol - 1. That is, let c := bwt[i], 
     *         where i in {0, 1, ..., (|bwt| - 1)}, then count(bwt) computes
     *         the number of occurrences of c in [0, i[
     */
    private int[] count(String bwt)
    {
        int aCounter = 0,
            cCounter = 0,
            gCounter = 0,
            tCounter = 0;
        int n = bwt.length();
        int[] ranks = new int[n];        
        for (int i = 0; i < n; i++)
        {
            char currentSymbol = bwt.charAt(i);
            switch(currentSymbol)
            {
                case 'A':
                    ranks[i] = aCounter++;
                    break;
                case 'C':
                    ranks[i] = cCounter++;
                    break;
                case 'G':
                    ranks[i] = gCounter++;
                    break;
                case 'T':
                    ranks[i] = tCounter++;
                    break;
                default: break;
            }            
        }
        return ranks;
    }

    static public void main(String[] args) throws IOException {
        new InverseBWT().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        System.out.println(inverseBWT(bwt));
    }
}
