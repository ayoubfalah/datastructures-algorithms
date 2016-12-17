import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class InverseBWT 
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
    
    String inverseBWT(String bwt) 
    {
        char[] chars = bwt.toCharArray();
        int[] orders = sortCharacters(chars);
        
        StringBuilder text = new StringBuilder();
        int i = orders[0]; // orders[0] denotes $
        i = orders[i];        
        while (i != orders[0])
        {
            text = text.append(chars[i]);
            i = orders[i];
        }
        text.append(chars[i]);
        return text.toString();
    }
    
    private int[] sortCharacters(char[] chars)
    {
        // ∑ = {A, ..., T} U {$} => |∑| = 85
        int AlphabetLength = 85;
        int n = chars.length;
        int[] orders = new int[n];
        int[] count = new int[AlphabetLength];
        
        for (int i = 0; i < n; i++)
        {
            count[chars[i]] = count[chars[i]] + 1;            
        }
        for (int j = 1; j < AlphabetLength; j++) 
        {
            count[j] = count[j] + count[j - 1];            
        }
        for (int k = n - 1; k >= 0 ; k--)
        {
            int c = chars[k];
            count[c] = count[c] - 1;
            orders[count[c]] = k;            
        }
        return orders;
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
