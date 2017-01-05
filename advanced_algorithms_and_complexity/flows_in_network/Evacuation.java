package advanced_algorithms_and_complexity.flows_in_network;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.LinkedList;
import java.util.Queue;

public class Evacuation {
    private static FastScanner in;
    private static Integer[] prevEdgeIds; // edge ids

    public static void main(String[] args) throws IOException {
        
        in = new FastScanner();

        FlowGraph graph = readGraph();
        System.out.println(maxFlow(graph, 0, graph.size() - 1));
    }

    public static int maxFlow(FlowGraph graph, int from, int to)
    {
        int flow = 0;
        while (hasAugmentingPath(graph, from, to))
        {
            int minRisidualCapacity = Integer.MAX_VALUE;
            List<Edge> edges = graph.edges;
            // compute the minimum residual capacity
            Edge edge;
            for (int v = to; v > from; v = edge.from)
            {
                edge = edges.get(prevEdgeIds[v]);
                minRisidualCapacity = Math.min(minRisidualCapacity, (edge.capacity - edge.flow));                
            }
            
            // Increase/Decrease the flows along the augmenting paht
            for (int v = to; v > from; v = edges.get(prevEdgeIds[v]).from)
                graph.addFlow(prevEdgeIds[v], minRisidualCapacity);
            
            flow += minRisidualCapacity;            
        }
        return flow;
    }
    
    private static boolean hasAugmentingPath(FlowGraph graph, int from, int to)
    {
        int n = graph.size(); 
        boolean[] visited = new boolean[n];
        // for any v in V, prev[v] := the edge id of the edge that leads
        // to v in a BFS path
        prevEdgeIds = new Integer[n];
        
        Queue<Integer> q = new LinkedList();
        q.add(from);
        visited[from] = true;
        List<Edge> edges = graph.edges;
        while (!q.isEmpty())
        {
            Integer u = q.poll();
            List<Integer> edgeIds = graph.getIds(u);
            for (Integer edgeId : edgeIds) 
            {
                Edge edge = edges.get(edgeId);
                // The residualCapacity of edge
                int v = edge.to;
                if ((edge.capacity != edge.flow) && !visited[v]) 
                {
                    prevEdgeIds[v] = edgeId;
                    q.add(v);
                    visited[v] = true;                    
                }                
            }
        }        
        return visited[to];
    }

    static FlowGraph readGraph() throws IOException {
        int vertex_count = in.nextInt();
        int edge_count = in.nextInt();
        FlowGraph graph = new FlowGraph(vertex_count);

        for (int i = 0; i < edge_count; ++i) {
            int from = in.nextInt() - 1, to = in.nextInt() - 1, capacity = in.nextInt();
            graph.addEdge(from, to, capacity);
        }
        return graph;
    }

    static class Edge
    {
        int from, to, capacity, flow;

        public Edge(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = 0;
        }

        @Override
        public boolean equals(Object obj) 
        {
            Edge that = (Edge)obj;
            return (this.from == that.from && this.to == that.to) || 
                   (this.from == that.to && this.to == that.from);
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 47 * hash + this.from + this.to;
            return hash;
        }
    }

    /* This class implements a bit unusual scheme to store the graph edges, in order
     * to retrieve the backward edge for a given edge quickly. */
    static class FlowGraph {
        /* List of all - forward and backward - edges */
        private List<Edge> edges;

        /* These adjacency lists store only indices of edges from the edges list */
        private List<Integer>[] graph;

        public FlowGraph(int n) {
            this.graph = (ArrayList<Integer>[])new ArrayList[n];
            for (int i = 0; i < n; ++i)
                this.graph[i] = new ArrayList<>();
            this.edges = new ArrayList<>();
        }

        public void addEdge(int from, int to, int capacity) {
            /* Note that we first append a forward edge and then a backward edge,
             * so all forward edges are stored at even indices (starting from 0),
             * whereas backward edges are stored at odd indices. */
            Edge forwardEdge = new Edge(from, to, capacity);
            Edge backwardEdge = new Edge(to, from, 0);
            graph[from].add(edges.size());
            edges.add(forwardEdge);
            graph[to].add(edges.size());
            edges.add(backwardEdge);
        }

        public int size() {
            return graph.length;
        }

        public List<Integer> getIds(int from) {
            return graph[from];
        }

        public Edge getEdge(int id) {
            return edges.get(id);
        }

        public void addFlow(int id, int flow) {
            /* To get a backward edge for a true forward edge (i.e id is even), we should get id + 1
             * due to the described above scheme. On the other hand, when we have to get a "backward"
             * edge for a backward edge (i.e. get a forward edge for backward - id is odd), id - 1
             * should be taken.
             *
             * It turns out that id ^ 1 works for both cases. Think this through! */
            edges.get(id).flow += flow;
            edges.get(id ^ 1).flow -= flow;
        }

        int E() 
        {
            return edges.size();
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() throws FileNotFoundException {
            reader = new BufferedReader(
                    new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}