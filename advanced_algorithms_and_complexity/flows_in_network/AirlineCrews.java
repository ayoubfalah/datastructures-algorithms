package advanced_algorithms_and_complexity.flows_in_network;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class AirlineCrews {
    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new AirlineCrews().solve();
    }

    /**
     * Finding maximum matching in a bipartite graph to assign 
     * airline crews to flights.
     * @throws IOException 
     */
    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        boolean[][] bipartiteGraph = readData();
        int[] matching = findMatching(bipartiteGraph);
        writeResponse(matching);
        out.close();
    }

    boolean[][] readData() throws IOException {
        int numLeft = in.nextInt();
        int numRight = in.nextInt();
        boolean[][] adjMatrix = new boolean[numLeft][numRight];
        for (int i = 0; i < numLeft; ++i)
            for (int j = 0; j < numRight; ++j)
                adjMatrix[i][j] = (in.nextInt() == 1);
        return adjMatrix;
    }

    private int[] findMatching(boolean[][] bipartiteGraph) {
        int m = bipartiteGraph.length; // The number of flights
        int n = bipartiteGraph[0].length; // The number of crews
        // For any i in [0, m[: matching[i] is the crew number that is assigned to
        // the flight number i
        int[] matching = new int[m];
        // Initially, no crew is assigned to a flight
        Arrays.fill(matching, -1);        
        for (int crew = 0; crew < n; crew++)
        {
            boolean[] visited = new boolean[m];
            canMatch(crew, bipartiteGraph, visited, matching);         
        }        
        return matching;
    }
    
    private boolean canMatch(int crew, boolean[][] bipartiteGraph, 
            boolean[] visited, int[] matching) 
    {
        int m = matching.length;
        boolean isMatched = false;
        for (int flight = 0; flight < m; flight++) 
        {
            boolean flightIsAdjToCrew = bipartiteGraph[flight][crew];
            if (flightIsAdjToCrew && !visited[flight])
            {
                visited[flight] = true;
                boolean flightIsMatched = matching[flight] >= 0; 
                if (!flightIsMatched || 
                    canMatch(matching[flight], bipartiteGraph, visited, matching))
                {
                    matching[flight] = crew; // Match flight to crew
                    isMatched = true;
                    break;
                }                
            }
        }
        return isMatched;
    }

    private void writeResponse(int[] matching) {
        for (int i = 0; i < matching.length; ++i) {
            if (i > 0) {
                out.print(" ");
            }
            if (matching[i] == -1) {
                out.print("-1");
            } else {
                out.print(matching[i] + 1);
            }
        }
        out.println();
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
