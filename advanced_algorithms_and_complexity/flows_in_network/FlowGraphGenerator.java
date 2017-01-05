package advanced_algorithms_and_complexity.flows_in_network;

import advanced_algorithms_and_complexity.flows_in_network.Evacuation.Edge;
import advanced_algorithms_and_complexity.flows_in_network.Evacuation.FlowGraph;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author ayoubfalah
 */
public class FlowGraphGenerator 
{
    /**
     * Let G=(V, E) be a flow graph
     * @param n the number of vertices in V
     * @param m the number of edges in E
     * @param minCapacity for any e in E: c(e) >= minCapacity 
     * @param maxCapacity for any e in E: c(e) <= maxCapacity
     * @return a flow graph
     */
    public static FlowGraph generate(int n, int m, 
            int minCapacity, int maxCapacity) 
    {
        FlowGraph graph = new FlowGraph(n);
        Set<Edge> edges = new HashSet();        
        while (graph.E() < 2*m)
        {
            int from = Math.abs((new Random()).nextInt()) % (n - 1);
            int to = 1 + Math.abs((new Random()).nextInt()) % (n - 1);
            int capacity = minCapacity +
                    Math.abs((new Random()).nextInt()) % maxCapacity;
            Edge edge = new Edge(from, to, capacity);
            if ((from != to) && (!edges.contains(edge))) 
            {
                edges.add(edge);
                graph.addEdge(from, to, capacity);               
            }
        }
        return graph;
    }
}
