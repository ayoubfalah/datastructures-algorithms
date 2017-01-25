import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Acyclicity 
{
    /**
     * 
     * @param adj The adjacency list of a directed graph G :=(V,E), with
     *            card(V) = n and card(E) = m
     * @precondition 1 <= n <= 10^3
     *               0 <= m <= 10^3
     * @return 1 iff G contains a cycle and 0 otherwise
     */
    private static int acyclic(ArrayList<Integer>[] adj) 
    {
        boolean result;
        boolean[] visited = new boolean[adj.length];
        boolean[] inProcessing = new boolean[adj.length];
        
        for (int v = 0; v < adj.length; v++)
        {
            if (!visited[v])
            {
               result = acyclic(adj, visited, inProcessing, v);
                if (result)
                    return 1;
            }            
        }
        return 0;
    }
    
    private static boolean acyclic(ArrayList<Integer>[] adj, boolean[] visited, boolean[] inProcessing, int v)
    {
        // Any vertex is reachable from itself
        visited[v] = true;
        inProcessing[v] = true;
        for (Integer neighbor : adj[v]) 
        {
            if (visited[neighbor] && inProcessing[neighbor])
                return true;
            if (!visited[neighbor])
                if (acyclic(adj, visited, inProcessing, neighbor))
                    return true;            
        }
        inProcessing[v] = false;
        return false;
    }

    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        Arrays.fill(adj, new ArrayList());
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
        }
        System.out.println(acyclic(adj));
    }
}

