package shortest_paths_1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Bipartite 
{
    /**
     * 
     * @param adj The adjacency list of an undirected graph G=(V,E), with 
     *            card(V) = n and card(E) = m
     * @precondition 1 <= n <= 10^5
     *               0 <= m <= 10^5
     * @return 1 if G is bipartite and 0 otherwise
     */
    private static int bipartite(ArrayList<Integer>[] adj) 
    {
        int[] dist = new int[adj.length];
        for (int i = 0; i < dist.length; i++) 
        {
            dist[i] = Integer.MAX_VALUE;            
        }
        int[] colors = new int[dist.length];
        for (int i = 0; i < adj.length; i++) 
        {
            if (dist[i] == Integer.MAX_VALUE) 
            {
                dfs(adj, i, dist, colors);
            }            
        }
        
        // Check whether the endpoints of each edge are different
        for (int u = 0; u < adj.length; u++) 
        {
            for (int v : adj[u]) 
            {
                if (colors[u] == colors[v])
                    return 0;                
            }            
        }
        return 1;
    }
    
    private static void dfs(ArrayList<Integer>[] adj, int s, 
            int[] dist, int[] colors) 
    {        
        dist[s] = 0;
        colors[s] = 1;
        
        Queue<Integer> q = new LinkedList();
        q.add(s);
        while (!q.isEmpty())
        {
            Integer u = q.poll();
            for (Integer v : adj[u])
            {
                if (dist[v] == Integer.MAX_VALUE) 
                {
                    q.add(v);
                    dist[v] = dist[u] + 1;
                    colors[v] = 1 - colors[u];                    
                }               
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        System.out.println(bipartite(adj));
    }
}

