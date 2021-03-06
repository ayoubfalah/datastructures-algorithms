import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BurrowsWheelerTransform {
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
     * @param text a string
     * @precondition text is defined over the alphabet {A, C, G, T}
     *               text must end with $
     *               1 <= |text| - |$| <= 1000
     * @return The Burrows-Wheeler Transform of text
     */
    String BWT(String text) 
    {
        int n = text.length();
        String[] bwm = computeBwm(text);
        StringBuilder result = new StringBuilder();
        
        for (String cyclicRotation : bwm)
        {
            result.append(cyclicRotation.charAt(n - 1));
        }
        
        return result.toString();
    }
    
    /**
     * 
     * @param text a string
     * @return The cyclic rotations of text
     */
    private String[] listAllCyclicRotations(String text)
    {
        int n = text.length();
        String[] cyclicRotations = new String[n];
        cyclicRotations[0] = text;
        
        for (int i = 1; i < n; i++)
        {
            cyclicRotations[i] =  cyclicRotations[i-1].charAt(n - 1) 
                    + cyclicRotations[i-1].substring(0, n - 1);          
        }
        
        return cyclicRotations;
    }
    
    /**
     * 
     * @param text a String
     * @return The Burrows-Wheeler Matrix of text
     */
    private String[] computeBwm(String text)
    {
        String[] cyclicRotations = listAllCyclicRotations(text);
        Arrays.sort(cyclicRotations);
        return cyclicRotations;
    }

    static public void main(String[] args) throws IOException {
        new BurrowsWheelerTransform().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        System.out.println(BWT(text));
    }
}