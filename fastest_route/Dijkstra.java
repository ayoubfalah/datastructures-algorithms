package fastest_route;

import java.util.*;

public class Dijkstra 
{
    /**
     * 
     * @param adj The adjacency list of a directed graph G=(V, E), with 
     *            card(V) = n and card(E) = m
     * @param cost The weights of edges in E
     * @param s a vertex in V
     * @param t a vertex in V
     * @precondition 1 <= n <= 10^3
     *               0 <= m <= 10^5
     *               for any (s,t) in {1, 2, ..., n}x{1, 2, ..., n}: s != t
     *               for any (u,v) in E: 0 <= weight((u,v)) <= 10^3
     * @return The minimum weight of a path from s to t, or -1 if there is no
     *         path between s and t
     */
    private static int distance(ArrayList<Integer>[] adj,
            ArrayList<Integer>[] cost, int s, int t) 
    {
        int[] dist = new int[adj.length];
        for (int v = 0; v < dist.length; v++) 
        {
            dist[v] = Integer.MAX_VALUE;            
        }        
        dist[s] = 0;
        Queue<Integer> q = new PriorityQueue();
        q.add(s);
        while (!q.isEmpty())
        {
            int u = q.remove();
            for (int i = 0; i < adj[u].size(); i++) 
            {
                int v = adj[u].get(i), w = cost[u].get(i);
                if (dist[v] > dist[u] + w) 
                {
                    dist[v] = dist[u] + w;
                    q.add(v);
                }                
            }
        }
        int result = (dist[t] == Integer.MAX_VALUE)? -1 : dist[t];
        return result;
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
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, cost, x, y));
    }
}

