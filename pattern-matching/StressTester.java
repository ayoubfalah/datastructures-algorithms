import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
            Random random = new Random();
            int n = Math.abs((new Random()).nextInt()) % 10 + 2;
            System.out.println(n);
            String[] patterns = new String[n];
            char[] alphabet = { 'A', 'C', 'G', 'T'};
            for (int i = 0; i < patterns.length; i++)
            {
                String pattern = generateRandomString(alphabet, 4);
                patterns[i] = pattern;
                System.out.print(patterns[i] + " ");
            }
            System.out.println("");
            ArrayBasedTrie t1 = new ArrayBasedTrie();
            String[] res1 = t1.buildTrie(patterns);
            Arrays.sort(res1);
            HashTableTrie t2 = new HashTableTrie();
            List<Map<Character, Integer>> edges = t2.buildTrie(patterns);
            String[] res2 = t2.toArray(edges);
            Arrays.sort(res2);
            for (int i = 0; i < res1.length; i++)
            {
                if (!res1[i].equals(res2[i]))
                {
                    System.out.println("Wrong answer: " + res1[i] + " " + res2[i]);
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
