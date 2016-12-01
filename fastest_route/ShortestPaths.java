package fastest_route;

import java.util.*;

public class ShortestPaths {

    /**
     * 
     * @param adj The adjacency list of a directed graph G = (V,E), with 
     *            card(V) = n and card(E) = m
     * @param cost The weights of edges in E
     * @param s a vertex in V
     * @param distance For each vertex v in G the array distance contains the
     *        distance of v from s
     * @param reachable (For each vertex v in G) reachable[v] = 1 iff v is
     *        reachable from s
     * @param shortest (For each vertex v in G) shortest[v] = 1 iff there is a
     *        shortest path from s to v
     * @precondition 1 <= n <= 10^3
     *               0 <= m <= 10^4
     *               for any (u,v) in E: |weight((u,v))| <= 10^9
     * @return for any v in V output the following on a separate line:
     *         "*" if there is no path from s to v
     *         "-" if there is a path from s to v, but there is no shortest path
     *             from s to u. That is, the distance from s to v is -infinity
     *         The length of a shortest paht otherwise
     */
    private static void shortestPaths(ArrayList<Integer>[] adj,
            ArrayList<Integer>[] cost, int s,long[] distance,
            int[] reachable, int[] shortest)
    {
        reachable[s] = 1;
        distance[s] = 0;
        
        for (int k = 0; k < adj.length - 1; k++)
        {
            for (int u = 0; u < adj.length; u++) 
            {
                for (int j = 0; j < adj[u].size(); j++)
                {
                    int v = adj[u].get(j);
                    int w = cost[u].get(j);
                    if ((distance[u] != Long.MAX_VALUE) &&
                        (distance[v] > (distance[u] + w)) ) 
                    {
                        distance[v] = distance[u] + w;
                        reachable[v] = 1;
                    }                    
                }                
            }          
        }
        
        for (int k = 0; k < adj.length - 1; k++)
        {   // the (card(V))-th update
            for (int u = 0; u < adj.length; u++) 
            {
                for (int j = 0; j < adj[u].size(); j++)
                {
                    int v = adj[u].get(j);
                    int w = cost[u].get(j);
                    if ((distance[u] != Long.MAX_VALUE) && 
                        (distance[v] > distance[u] + w) )
                    {
                        distance[v] = distance[u] + w;
                        shortest[v] = 0;                        
                    }               
                }                
            }
        }
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
        int s = scanner.nextInt() - 1;
        long distance[] = new long[n];
        int reachable[] = new int[n];
        int shortest[] = new int[n];
        for (int i = 0; i < n; i++) {
            distance[i] = Long.MAX_VALUE;
            reachable[i] = 0;
            shortest[i] = 1;
        }
        shortestPaths(adj, cost, s, distance, reachable, shortest);
        for (int i = 0; i < n; i++) {
            if (reachable[i] == 0) {
                System.out.println('*');
            } else if (shortest[i] == 0) {
                System.out.println('-');
            } else {
                System.out.println(distance[i]);
            }
        }
    }

}

