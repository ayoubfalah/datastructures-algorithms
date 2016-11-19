package pattern_matching;

import java.util.ArrayList;

/**
 *
 * @author ayoubfalah
 */
public class ArrayBasedTrie 
{
    public static void main(String[] args) 
    {
        String[] patterns = {"AGTG", "CATT", "GCAC", "CGCT", "GATG"};
        String[] out = new ArrayBasedTrie().buildTrie(patterns);
        for (int i = 0; i < out.length; i++) 
        {
            System.out.println(out[i]);            
        }       
    }

    public String[] buildTrie(String[] patterns)
    {
        Trie trie = new Trie();
        for (String pattern : patterns) {
            trie.add(pattern);            
        }
        return trie.outputTrie();
    }

    private static class Trie 
    {
        public Node root;
        public ArrayList<Node> adj; // The adjacency list
        Node newNode;
        public Trie() 
        {
            root = new Node('\0', null);
            adj = new ArrayList<>();
            adj.add(root);
        }

        private void add(String pattern)
        {
            Node currentNode = root;
            for (int i = 0; i < pattern.length(); i++) 
            {
                char currentSymbol = pattern.charAt(i);
                int c = -1;
                switch(currentSymbol) 
                {
                    case 'A': c = 0; break;
                    case 'C': c = 1; break;
                    case 'G': c = 2; break;
                    case 'T': c = 3; break;
                    default: return;
                }
                
                if(currentNode.children[c] == null)
                {
                    currentNode.children[c] = new Node(currentSymbol,currentNode);
                    adj.add(currentNode.children[c]);
                }
                currentNode = currentNode.children[c];                
            }
        }

        private String[] outputTrie()
        {
            String[] out = new String[adj.size() -1];
            for (int i = 0; i < out.length; i++)
            {
                Node curr = adj.get(i+1);
                out[i] = "" + curr.parent.num + "->" + curr.num + ":" + curr.letter;
            }
            return out;
        }
    }

    private static class Node {

        public static int numNodes = 0;
        private char letter;
        private int num;
        private Node parent;
        private Node[] children = new Node[4]; // A, C, G, T

        private Node(char l, Node p) 
        {
            letter = l;
            parent = p;
            num = numNodes++;
        }
    }
}