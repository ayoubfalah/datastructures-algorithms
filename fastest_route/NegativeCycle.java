package fastest_route;

import java.util.ArrayList;
import java.util.Scanner;

public class NegativeCycle 
{
    /**
     * 
     * @param adj The adjacency list of a directed graph G = (V,E), with
     *            card(V) = n and card(E) = m
     * @param cost The weights of the edges in E
     * @precondition 1 <= n <= 10^3
     *               0 <= m <= 10^4
     *               for any (u,v) in E: |weight((u,v))| <= 10^3
     * @return 1 if the graph contains a cycle of negative weight and 0 otherwise
     */
    private static int negativeCycle(ArrayList<Integer>[] adj,
            ArrayList<Integer>[] cost)
    {
        int[] dist = new int[adj.length];
        for (int v = 1; v < dist.length; v++) 
        {
            dist[v] = Integer.MAX_VALUE;            
        }
        for (int k = 0; k < adj.length - 1; k++)
        {
            for (int u = 0; u < adj.length; u++) 
            {
                for (int j = 0; j < adj[u].size(); j++)
                {
                    int v = adj[u].get(j);
                    int w = cost[u].get(j);
                    if (dist[v] > ((double)dist[u]) + w) 
                    {
                        dist[v] = dist[u] + w;
                    }                    
                }                
            }          
        }
        
        // the (card(V))-th update
        for (int u = 0; u < adj.length; u++) 
        {
            for (int j = 0; j < adj[u].size(); j++)
            {
                int v = adj[u].get(j);
                int w = cost[u].get(j);
                if (dist[v] > ((double)dist[u]) + w)
                    return 1;                  
            }                
        }        
        return 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        System.out.println(negativeCycle(adj, cost));
    }
}

