import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class StronglyConnected 
{
    /**
     * 
     * @param adj The adjacency list of a directed graph G=(V,E), with
     *            card(V) = n and card(E)=m
     * @precondition 1 <= n <= 10^4
     *               0 <= m <= 10^4
     * @return The number of strongly connected components
     */
    private static int numberOfStronglyConnectedComponents(ArrayList<Integer>[] adj) 
    {
        ArrayList<Integer>[] adjTransposed = transpose(adj);
        ArrayList<Integer> post = dfs(adjTransposed);
        int result = 0;
        boolean[] visited = new boolean[adj.length];
        for (int i = post.size() - 1; i >= 0; i--)
        {
            int v = post.get(i);
            if (!visited[v])
            {
                explore(adj, visited, post, v);
                result++;                
            }
        }
        return result;
    }
    
    /**
     * 
     * @param adj The adjacency list of a directed graph G=(V,E), with
     *            card(V) = n and card(E)=m
     * @precondition 1 <= n <= 10^4
     *               0 <= m <= 10^4
     * @return The adjacency list of the transposed graph G=(V,E^T)of G. That is
     *         E^T := {(v,u) in V^2 | (u,v) in E}
     */
    private static ArrayList<Integer>[] transpose(ArrayList<Integer>[] adj) 
    {
        ArrayList<Integer>[] adjTransposed = new ArrayList[adj.length];
        Arrays.fill(adjTransposed, new ArrayList());        
        for (int v = 0; v < adj.length; v++)
        {
            ArrayList<Integer> neighbors = adj[v];
            for (Integer neighbor : neighbors) 
            {                
                adjTransposed[neighbor].add(v);
            }            
        }
        return adjTransposed;
    }
    
    private static ArrayList<Integer> dfs(ArrayList<Integer>[] adj)
    {
        int n = adj.length;
        boolean[] visited = new boolean[n];
        ArrayList<Integer> post = new ArrayList();
        
        for (int v = 0; v < n; v++)
        {
            if (!visited[v])
            {
               explore(adj, visited, post, v);
            }            
        }
        return post;
    }
    
    private static void explore(ArrayList<Integer>[] adj, boolean[] visited,
            ArrayList<Integer> post, int x)
    {
        // Any vertex is reachable from itself
        visited[x] = true;
        for (Integer neighbor : adj[x]) 
        {
            if (!visited[neighbor])
                explore(adj, visited, post,neighbor);            
        }
        post.add(x);
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
        System.out.println(numberOfStronglyConnectedComponents(adj));
    }
}