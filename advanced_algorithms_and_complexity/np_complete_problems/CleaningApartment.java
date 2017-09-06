import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class CleaningApartment {
    private final InputReader reader;
    private final OutputWriter writer;

    public CleaningApartment(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new CleaningApartment(reader, writer).run();
        writer.writer.flush();
    }

    class Edge {
        int from;
        int to;
    }

    class ConvertHampathToSat {
        int numVertices;
        Edge[] edges;

        ConvertHampathToSat(int n, int m) {
            numVertices = n;
            edges = new Edge[m];
            for (int i = 0; i < m; ++i) {
                edges[i] = new Edge();
            }
        }

        void printEquisatisfiableSatFormula() 
        {
            List<List<Integer>> expression = new ArrayList();
            for (int vertex = 1; vertex <= numVertices; vertex++)
            {
                List<List<Integer>> clauses = appearsExactlyOnce(vertex);
                appendTo(expression, clauses);
            }
            
            for (int position = 0; position < numVertices; position++)
            {
                List<List<Integer>> clauses = occupiesExactlyOne(position);
                appendTo(expression, clauses);
            }
            
            boolean[][] adjMatrix = toAdjacencyMatrix(edges);
            for (int u = 1; u < numVertices; u++) 
            {
                for (int v = u+1; v <= numVertices; v++)
                {
                    if (!adjMatrix[u-1][v-1])
                    {
                        List<List<Integer>> clauses = cannotBeAdjacentInTheHamPath(u, v);
                        appendTo(expression, clauses);                        
                    }                    
                }                
            }
            
            int numOfClauses = expression.size();
            int numOfVariables = numVertices * numVertices;
            writer.printf("%d %d\n", numOfClauses, numOfVariables);
            String expressionToString = expressionToString(expression);            
            writer.printf(expressionToString);
        }
        
        private List<List<Integer>> appearsExactlyOnce(int vertex) 
        {
            List<Integer> clause = new ArrayList();
            for (int i = 0; i < numVertices; i++) {
                int literal = vertex + i*numVertices;
                clause.add(literal);
            }            
            List<List<Integer>> clauses = exactlyOneOf(clause);
            return clauses;
        }
        
        private List<List<Integer>> occupiesExactlyOne(int position) 
        {
            List<Integer> clause = new ArrayList();
            for (int i = 1; i <= numVertices; i++) {
                int literal = i + position*numVertices;
                clause.add(literal);
            }            
            List<List<Integer>> clauses = exactlyOneOf(clause);
            return clauses;
        }

        private List<List<Integer>> exactlyOneOf(List<Integer> clause)
        {
            List<List<Integer>> clauses = new ArrayList();
            clauses.add(clause);
            int clauseSize = clause.size(); 
            for (int i = 0; i < (clauseSize - 1); i++) 
            {
                for (int j = (i + 1); j < clauseSize; j++) 
                {
                    clauses.add(Arrays.asList(-clause.get(i), -clause.get(j)));
                }                
            }
            return clauses;
        }

        private void appendTo(List<List<Integer>> expression, List<List<Integer>> clauses) 
        {
            for (List<Integer> clause : clauses) 
            {
                expression.add(clause);                
            }
        }

        private boolean[][] toAdjacencyMatrix(Edge[] edges)
        {
            boolean[][] adjMatrix = new boolean[numVertices][numVertices];
            for (Edge edge : edges) 
            {
                adjMatrix[edge.from-1][edge.to-1] = true;
                adjMatrix[edge.to-1][edge.from-1] = true; 
            }
            return adjMatrix;
        }

        private List<List<Integer>> cannotBeAdjacentInTheHamPath(int u, int v)
        {
            List<List<Integer>> clauses = new ArrayList();
            for (int k = 0; k < numVertices-1; k++)
            {
                List<Integer> clause = new ArrayList();
                clause.add(-(u + k*numVertices));
                clause.add(-(v + (k+1)*numVertices));
                clauses.add(clause);
                clause = new ArrayList();
                clause.add(-(v + k*numVertices));
                clause.add(-(u + (k+1)*numVertices));
                clauses.add(clause);                
            }
            return clauses;
        }

        private String expressionToString(List<List<Integer>> expression) 
        {
            StringBuilder expressionToString = new StringBuilder();
            for (List<Integer> clause : expression)
            {                
                for (int literal : clause) 
                {
                    expressionToString.append(literal).append(" ");                    
                }
                expressionToString.append("0\n");               
            }
            return expressionToString.toString();
        }
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();

        ConvertHampathToSat converter = new ConvertHampathToSat(n, m);
        for (int i = 0; i < m; ++i) {
            converter.edges[i].from = reader.nextInt();
            converter.edges[i].to = reader.nextInt();
        }

        converter.printEquisatisfiableSatFormula();
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
