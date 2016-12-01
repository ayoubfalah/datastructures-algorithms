package shortest_paths_1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BFS 
{
    /**
     * 
     * @param adj The adjacency list of an undirected graph G=(V,E), wiht
     *            card(V) = n and card(E) = m
     * @param s a vertex in V
     * @param t a vertex in V
     * @preconstraint 2 <= n <= 10^5
     *                0 <= m <= 10^5
     *               for each (s,t) in {1, 2, ..., n}x{1, 2, ..., n}: s != t
     * @return The length of the shortest path between s and t. That is, the
     *         minimum number of edges in a path from s to t. If t is not reachable
     *         from s then distance(ArrayList, int, int) returns -1
     */
    private static int distance(ArrayList<Integer>[] adj, int s, int t) 
    {
        int[] dist = new int[adj.length];
        for (int i = 0; i < dist.length; i++) 
        {
            dist[i] = Integer.MAX_VALUE;            
        }
        
        dist[s] = 0;        
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
                }                
            }
        } 
        int result = (dist[t] == Integer.MAX_VALUE)? -1: dist[t];
        return result;
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
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, x, y));
    }
}

