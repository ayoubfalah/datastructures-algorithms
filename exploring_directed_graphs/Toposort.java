import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Toposort 
{
    /**
     * 
     * @param adj The adjacency list of a directed acyclic graph G:=(V,E), with
     *            card(V) = n and card(E) = m
     * @precondition 1 <= n <= 10^5
     *               0 <= m <= 10^5
     *               G is acyclic
     * @return a topological ordering of the vertices in V
     */
    private static ArrayList<Integer> toposort(ArrayList<Integer>[] adj) 
    {
        int used[] = new int[adj.length];
        ArrayList<Integer> order = new ArrayList();
        for (int v = 0; v < adj.length; v++) 
        {
            if (used[v] != 1) 
            {
                dfs(adj, used, order, v);                
            }            
        }
        Collections.reverse(order);
        return order;
    }

    private static void dfs(ArrayList<Integer>[] adj, int[] used, ArrayList<Integer> order, int s) 
    {
        used[s] = 1;
        for (int t : adj[s]) 
        {
            if (used[t] != 1)
            {
                dfs(adj, used, order, t);                
            }            
        }
        order.add(s);
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
        }
        ArrayList<Integer> order = toposort(adj);
        for (int x : order) {
            System.out.print((x + 1) + " ");
        }
    }
}

