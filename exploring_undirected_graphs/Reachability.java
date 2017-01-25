package exploring_undirected_graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Reachability 
{
    /**
     * 
     * @param adj The adjacency list of an undirected graph G :=(V,E), with
     *            card(V) = n and card(E) = m
     * @param u A vertex in V
     * @param v A vertex in V
     * @precondition 2 <= n <= 10^3
     *               1 <= m <= 10^3
     *               for each (u,v) in {1, 2, ..., n}x{1, 2, ..., n}: u != v
     * @return Output 1 if there is a path between u and v and 0 otherwise
     */
    private static int reach(ArrayList<Integer>[] adj, int u, int v) 
    {
        // Let reachableVertices contains the set of all elements that are
        // reachable from u
        ArrayList<Integer> reachableVertices = new ArrayList();
        explore(adj, u, reachableVertices);
        // v is reachable from u iff v is in reachableVertices
        int responce = reachableVertices.contains(v)? 1 : 0;
        return responce;
    }
    
    private static void explore(ArrayList<Integer>[] adj, int u, ArrayList reachableVertices)
    {
        // Any vertex is reachable from itself
        reachableVertices.add(u);
        for (Integer vertex : adj[u]) 
        {
            if (!reachableVertices.contains(vertex))
                explore(adj, vertex, reachableVertices);            
        }
    }


    public static void main(String[] args) {
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
        int u = scanner.nextInt() - 1;
        int v = scanner.nextInt() - 1;
        System.out.println(reach(adj, u, v));
    }
}

