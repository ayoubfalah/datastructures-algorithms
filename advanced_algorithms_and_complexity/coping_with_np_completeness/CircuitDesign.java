package advanced_algorithms_and_complexity.coping_with_np_completeness;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

public class CircuitDesign {

    private final InputReader reader;
    private final OutputWriter writer;

    public CircuitDesign(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args)
    {
        new Thread(null, new Runnable() {
            public void run() {
                InputReader reader = new InputReader(System.in);
                OutputWriter writer = new OutputWriter(System.out);
                new CircuitDesign(reader, writer).run();
                writer.writer.flush();
            }
        }, "1", 1 << 26).start();
    }

    class Clause {

        int firstVar;
        int secondVar;
    }

    class TwoSatisfiability {

        int numVars;
        Clause[] clauses;

        TwoSatisfiability(int n, int m) {
            numVars = n;
            clauses = new Clause[m];
            for (int i = 0; i < m; ++i) {
                clauses[i] = new Clause();
            }
        }

        boolean isSatisfiableNaive(int[] result) {
            // This solution tries all possible 2^n variable assignments.
            for (int mask = 0; mask < (1 << numVars); ++mask) {
                for (int i = 0; i < numVars; ++i) {
                    result[i] = (mask >> i) & 1;
                }

                boolean formulaIsSatisfied = true;

                for (Clause clause : clauses) {
                    boolean clauseIsSatisfied = false;
                    if ((result[Math.abs(clause.firstVar) - 1] == 1) == (clause.firstVar < 0)) {
                        clauseIsSatisfied = true;
                    }
                    if ((result[Math.abs(clause.secondVar) - 1] == 1) == (clause.secondVar < 0)) {
                        clauseIsSatisfied = true;
                    }
                    if (!clauseIsSatisfied) {
                        formulaIsSatisfied = false;
                        break;
                    }
                }

                if (formulaIsSatisfied) {
                    return true;
                }
            }
            return false;
        }

        boolean isSatisfiableFaster(int[] result) {
            List<Integer>[] adj = constructImplicationGraph(clauses);
            List<Set> sccs = findStronglyConnectedComponents(adj);
            boolean isSAT = isSAT(sccs);
            if (!isSAT) return isSAT;
            findTruthAssignment(sccs, result);
            return isSAT;
        }

        private List<Integer>[] constructImplicationGraph(Clause[] clauses) {
            List<Integer>[] adj = new ArrayList[2 * numVars];
            for (int i = 0; i < adj.length; i++) {
                adj[i] = new ArrayList();
            }
            // Constructing the edges
            for (Clause clause : clauses) {
                int u = index(clause.firstVar);
                int v = index(clause.secondVar);
                adj[not(u)].add(v);
                adj[not(v)].add(u);
            }
            return adj;
        }

        /**
         *
         * @param x a literal
         * @return a vertex that represents the position of x in the adjacency
         * list.
         */
        private int index(int x) {
            int v;
            if (x > 0) {
                v = x - 1;
            } else {
                v = 2 * numVars + x;
            }
            return v;
        }

        private int not(int v) {
            return (2 * numVars - 1) - v;
        }

        /**
         *
         * @param adj The adjacency list of a directed graph G=(V,E), with
         * card(V) = n and card(E)=m
         * @precondition 1 <= n <= 10^4 0 <= m <= 10^4 
         * @return The strongly connected components of G
         */
        public List<Set> findStronglyConnectedComponents(List<Integer>[] adj) {
            List<Integer>[] adjTransposed = transpose(adj);
            List<Integer> post = dfs(adjTransposed);
            boolean[] visited = new boolean[adj.length];
            List<Set> sccs = new ArrayList();
            for (int i = post.size() - 1; i >= 0; i--) {
                int v = post.get(i);
                Set scc = new HashSet();
                if (!visited[v]) {
                    explore(adj, visited, post, v, scc);
                    sccs.add(scc);
                }
            }
            return sccs;
        }

        /**
         *
         * @param adj The adjacency list of a directed graph G=(V,E), with
         * card(V) = n and card(E)=m
         * @precondition 1 <= n <= 10^4 0 <= m <= 10^4 
         * @return The adjacency list of the transposed graph G=(V,E^T)of G. 
         *         Thatis E^T := {(v,u) in V^2 | (u,v) in E}
         */
        private List<Integer>[] transpose(List<Integer>[] adj) {
            List<Integer>[] adjTransposed = new ArrayList[adj.length];
            for (int i = 0; i < adj.length; i++) {
                adjTransposed[i] = new ArrayList();
            }
            for (int v = 0; v < adj.length; v++) {
                List<Integer> neighbors = adj[v];
                for (Integer neighbor : neighbors) {
                    adjTransposed[neighbor].add(v);
                }
            }
            return adjTransposed;
        }

        private List<Integer> dfs(List<Integer>[] adj) {
            int n = adj.length;
            boolean[] visited = new boolean[n];
            ArrayList<Integer> post = new ArrayList();

            for (int v = 0; v < n; v++) {
                if (!visited[v]) {
                    explore(adj, visited, post, v);
                }
            }
            return post;
        }

        private void explore(List<Integer>[] adj, boolean[] visited,
                List<Integer> post, int v) {
            // Any vertex is reachable from itself
            visited[v] = true;
            for (Integer w : adj[v]) {
                if (!visited[w]) {
                    explore(adj, visited, post, w);
                }
            }
            post.add(v);
        }

        private void explore(List<Integer>[] adj, boolean[] visited,
                List<Integer> post, int v, Set scc) {
            // Any vertex is reachable from itself
            visited[v] = true;
            scc.add(v);
            for (Integer w : adj[v]) {
                if (!visited[w]) {
                    explore(adj, visited, post, w, scc);
                }
            }
            post.add(v);
        }
        
        private boolean isSAT(List<Set> sccs)
        {
            for (Set<Integer> scc : sccs) {
                for (Integer v : scc) if (scc.contains(not(v))) return false;
            }
            return true;
        }
        
        private void findTruthAssignment(List<Set> sccs, int[] result)
        {
            Arrays.fill(result, -1);
            for (Set<Integer> scc : sccs) {
                for (int v : scc) 
                {
                    if (v < result.length && result[v] == -1) 
                        result[v] = 0;
                    else if (v >= result.length && result[not(v)] == -1) 
                        result[not(v)] = 1;
                }
            }
        }
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();

        TwoSatisfiability twoSat = new TwoSatisfiability(n, m);
        for (int i = 0; i < m; ++i) {
            twoSat.clauses[i].firstVar = reader.nextInt();
            twoSat.clauses[i].secondVar = reader.nextInt();
        }
        int result[] = new int[n];
        if (twoSat.isSatisfiableFaster(result)) {
            writer.printf("SATISFIABLE\n");
            for (int i = 1; i <= n; ++i) {
                if (result[i - 1] == 1) {
                    writer.printf("%d", -i);
                } else {
                    writer.printf("%d", i);
                }
                if (i < n) {
                    writer.printf(" ");
                } else {
                    writer.printf("\n");
                }
            }
        } else {
            writer.printf("UNSATISFIABLE\n");
        }
    }

    static class InputReader {

        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class OutputWriter {

        public PrintWriter writer;

        OutputWriter(OutputStream stream) {
            writer = new PrintWriter(stream);
        }

        public void printf(String format, Object... args) {
            writer.print(String.format(Locale.ENGLISH, format, args));
        }
    }
}