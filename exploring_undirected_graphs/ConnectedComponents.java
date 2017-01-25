package exploring_undirected_graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ConnectedComponents 
{
    /**
     * 
     * @param adj The adjacency list of an undirected graph G :=(V,E), with
     *            card(V) = n and card(E) = m
     * @precondition 1 <= n <= 10^3
     *               0 <= m <= 10^3
     * @return The number of connected components of G
     */
    private static int numberOfComponents(ArrayList<Integer>[] adj) 
    {
        int result = 0;
        boolean[] visited = new boolean[adj.length];        
        for (int v = 0; v < adj.length; v++)
        {
            if (!visited[v])
            {
               result++;
               explore(adj, visited, v); 
            }            
        }
        return result;
    }
    
    private static void explore(ArrayList<Integer>[] adj, boolean[] visited, int x)
    {
        // Any vertex is reachable from itself
        visited[x] = true;
        for (Integer neighbor : adj[x]) 
        {
            if (!visited[neighbor])
                explore(adj, visited, neighbor);            
        }
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
            adj[y - 1].add(x - 1);
        }
        System.out.println(numberOfComponents(adj));
    }
}

