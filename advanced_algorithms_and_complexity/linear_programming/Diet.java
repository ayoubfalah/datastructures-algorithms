package advanced_algorithms_and_complexity.linear_programming;

import java.io.*;
import java.util.*;

public class Diet {

    BufferedReader br;
    PrintWriter out;
    StringTokenizer st;
    boolean eof;

    static int solveDietProblem(int n, int m, double A[][], double[] b, double[] c, double[] x)
    {
        Arrays.fill(x, 1);
        // Take each subset of size m
        Set<Set> subsets = computeSubsets(m, n);
        ArrayList<double[]> solutions = new ArrayList();
        for (Set E : subsets) 
        {
            // Build a system of linear equations based on E
            Equation equation = buildSystemOfLinearEquations(E, A, b);
            double[] solution = EnergyValues.solveEquation(equation);
            // Check whether the solution satisfies all the other inequalities
            boolean valid = checkSolution(A, b, solution);
            if (valid) solutions.add(solution);
            
        }
        selectOptimalSolution(solutions, c, x);
        
        return 0;
    }
    
    private static boolean checkSolution(double A[][], double b[], double solution[])
    {
        for (int i = 0; i < solution.length; i++)
            if (solution[i] < 0) return false;
        for (int i = 0; i < A.length; i++) 
        {
            double sum = 0;
            for (int j = 0; j < A[0].length; j++)
                sum += A[i][j]*solution[j];
            if (sum > b[i]) return false;                
        }
        return true;
    }

    private static void selectOptimalSolution(ArrayList<double[]> solutions, double[] c, double[] x) {
        double max = Double.NEGATIVE_INFINITY;
        for (double[] solution : solutions) 
        {
            double sum = 0.0;
            for (int i = 0; i < solution.length; i++)
                sum += solution[i] * c[i];            
            if (sum >= max)
            {
                int from = 0;
                int length = solution.length;
                System.arraycopy(solution, from, x, from, length);
                max = sum;
            }
        }
    }

    private static Equation buildSystemOfLinearEquations(Set E, double[][] A, double[] b)
    {
        int m = A[0].length;
        int n = A.length;
        
        double[][] A_ = new double[m][m];
        double[] b_ = new double[m];
        Iterator iter = E.iterator();
        int k = 0;
        while (iter.hasNext())
        {
            Integer equationIndex = (Integer)iter.next();
            if (equationIndex < n)
            {
                System.arraycopy(A[equationIndex], 0, A_[k], 0, m);
                b_[k++] = b[equationIndex];
            }else
            {
                // create a new array
                double[] temp = new double[m];
                // Set the m-th element to 1
                temp[equationIndex % n] = 1;
                A_[k++] = temp;
            }
        }
        // Solve the system of linear equations
        Equation equation = new Equation(A_, b_);
        return equation;
    }
    
    private static Set<Set> computeSubsets(int m, int n) {
        Integer[] X = new Integer[m + n];
        for (int i = 0; i < X.length; i++)
            X[i] = i;
        Set<Set> subsets = computeSubsets(X, m);
        return subsets;
    }
    
    public static Set<Set> computeSubsets(Object[] X, int k)
    {
        int n = X.length;
        int m = (int)binomialCoefficient(n, k);
        Set Y = new HashSet();
        while (Y.size() < m)
        {
            HashSet e = new HashSet();
            while (e.size() < k) 
            {
                int index = Math.abs(new Random().nextInt()) % n;
                e.add(X[index]);                    
            }
            Y.add(e);
        }
        return Y;
    }
    
    private static float binomialCoefficient(int n, int k)
    {
        if( (n == 0) && (k == 0)) return 1.0f;
        if ((n < 0) || (k < 0)) return 0.0f;
        return  binomialCoefficient(n - 1, k) + binomialCoefficient(n - 1, k - 1);
    }

    void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        double[][] A = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                A[i][j] = nextInt();
            }
        }
        double[] b = new double[n];
        for (int i = 0; i < n; i++) {
            b[i] = nextInt();
        }
        double[] c = new double[m];
        for (int i = 0; i < m; i++) {
            c[i] = nextInt();
        }
        double[] ansx = new double[m];
        int anst = solveDietProblem(n, m, A, b, c, ansx);
        if (anst == -1) {
            out.printf("No solution\n");
            return;
        }
        if (anst == 0) {
            out.printf("Bounded solution\n");
            for (int i = 0; i < m; i++) {
                out.printf("%.18f%c", ansx[i], i + 1 == m ? '\n' : ' ');
            }
            return;
        }
        if (anst == 1) {
            out.printf("Infinity\n");
        }
    }

    Diet() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
        solve();
        out.close();
    }

    public static void main(String[] args) throws IOException {
        new Diet();
    }

    String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                eof = true;
                return null;
            }
        }
        return st.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }
}
