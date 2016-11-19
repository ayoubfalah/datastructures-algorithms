import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class HashTableTrie {
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

    List<Map<Character, Integer>> buildTrie(String[] patterns) 
    {
        List<Map<Character, Integer>> adj = new ArrayList();
        
        Map<Character, Integer> root = new HashMap<>();
        char currentSymbol = patterns[0].charAt(0);
        int newNodeIndex = 0;
        root.put(currentSymbol, ++newNodeIndex);
        adj.add(root);
        adj.add(new HashMap<>());
        
        for (String pattern : patterns) 
        {
            int currentNodeIndex = 0;
            Map<Character, Integer> currentNode = adj.get(currentNodeIndex);
            for (int i = 0; i < pattern.length(); i++) 
            {
                currentSymbol = pattern.charAt(i);
                if (currentNode.containsKey(currentSymbol)) 
                {
                    currentNodeIndex = currentNode.get(currentSymbol);
                    currentNode = adj.get(currentNodeIndex);
                }else
                {
                    Map newNode = currentNode;
                    newNode.put(currentSymbol, ++newNodeIndex);
                    adj.add(new HashMap<>());
                    currentNode = adj.get(newNodeIndex);
                }            
            }            
        }

        return adj;
    }

    static public void main(String[] args) throws IOException {
        new HashTableTrie().run();
    }

    public void print(List<Map<Character, Integer>> trie) {
        for (int i = 0; i < trie.size(); ++i) {
            Map<Character, Integer> node = trie.get(i);
            for (Map.Entry<Character, Integer> entry : node.entrySet()) {
                System.out.println(i + "->" + entry.getValue() + ":" + entry.getKey());
            }
        }
    }
    
    public String[] toArray(List<Map<Character, Integer>> trie) {
        ArrayList<String> edges = new ArrayList();
        for (int i = 0; i < trie.size(); ++i) {
            Map<Character, Integer> node = trie.get(i);
            for (Map.Entry<Character, Integer> entry : node.entrySet()) {
                String edge = i + "->" + entry.getValue() + ":" + entry.getKey();
                edges.add(edge);
            }
        }
        return edges.toArray(new String[edges.size()]);
    }
    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        int patternsCount = scanner.nextInt();
        String[] patterns = new String[patternsCount];
        for (int i = 0; i < patternsCount; ++i) {
            patterns[i] = scanner.next();
        }
        List<Map<Character, Integer>> trie = buildTrie(patterns);
        print(trie);
    }
}
