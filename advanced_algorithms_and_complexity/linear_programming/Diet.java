package advanced_algorithms_and_complexity.linear_programming;

import java.io.*;
import java.util.*;

//class Equation {
//    Equation(double a[][], double b[]) {
//        this.a = a;
//        this.b = b;
//    }
//
//    double a[][];
//    double b[];
//}
//
//class Position {
//    Position(int column, int row) {
//        this.column = column;
//        this.row = row;
//    }
//
//    int column;
//    int row;
//}

public class Diet 
{
    public static final double BIG_NUMBER = 1e9;
    public static final boolean SOLVABLE = false;

    private static boolean isOriginOptimal(double[] c) 
    {
        boolean isOriginOptimal = true;
        for (int i = 0; i < c.length; i++)
            if (Double.compare(c[i], 0.0) > 0)
            {
                isOriginOptimal = false;
                break;                
            }
        return isOriginOptimal;
    }

    BufferedReader br;
    PrintWriter out;
    StringTokenizer st;
    boolean eof;

    static int solveDietProblem(int n, int m, double A[][], double[] b, double[] c, double[] x)
    {
        boolean isOriginOptimal = isOriginOptimal(c);
        if (isOriginOptimal)
        {
            Arrays.fill(x, 0);
            return 0;            
        }
            
        Arrays.fill(x, 1);
        // Take each subset of size m
        List<List<Integer>> subsets = computeSubsets(m, n);
        List<double[]> solutions = new ArrayList();
        for (List E : subsets) 
        {
            // Build a system of linear equations based on E
            Equation equation = buildSystemOfLinearEquations(E, A, b);
            boolean hasSolution = solveEquation(equation);
            // The system is not solvable
            if (!hasSolution) continue;
            double[] solution = equation.b;
            // Check whether the solution satisfies all the other inequalities
            boolean valid = checkSolution(A, b, solution);
            if (valid) solutions.add(solution);            
        }
        if (solutions.isEmpty()) return -1;
        // solution contains 10^9
        for (double[] solution : solutions)
            for (int i = 0; i < solution.length; i++) 
            {
                if (Math.abs(solution[i] - BIG_NUMBER) <= 1.0E-3)
                {
                    //solution[i] = BIG_NUMBER;
                    //System.arraycopy(solution, 0, x, 0, x.length);
                    return 1;
                }
                
            }
        selectOptimalSolution(solutions, c, x);        
        return 0;
    }

    private static List<List<Integer>> computeSubsets(int m, int n)
    {
        int[] a = new int[m+n];
        for (int i = 0; i < m+n; i++)
            a[i] = i;
        List<List<Integer>> subsets = new ArrayList();
        subset(a, m, 0, new ArrayList(), subsets);
        return subsets;
    }
    
    static boolean solveEquation(Equation equation) {
        double a[][] = equation.a;
        double b[] = equation.b;
        int size = a.length;
        boolean[] used_columns = new boolean[size];
        boolean[] used_rows = new boolean[size];
        for (int step = 0; step < size; ++step) {
            Position pivot_element = selectPivotElement(a, used_rows, used_columns);
            swapLines(a, b, used_rows, pivot_element);
            processPivotElement(a, b, pivot_element);
            markPivotElementUsed(pivot_element, used_rows, used_columns);
        }
        if (Double.valueOf(b[0]).isInfinite() || Double.valueOf(b[0]).isNaN())
            return SOLVABLE;
        return !SOLVABLE;
//        if (Double.valueOf(b[0]).isNaN())
//            return 1;
//        if (Double.valueOf(b[0]).isInfinite())
//            return -1;
//        return 0;
    }
    
    static Position selectPivotElement(double a[][], boolean used_raws[],
            boolean used_columns[])
    {
        int m = a.length;
        Position pivot_element = new Position(0, 0);
        while (used_raws[pivot_element.row])
            ++pivot_element.row;
        while (used_columns[pivot_element.column])
            ++pivot_element.column;
        if (a[pivot_element.row][pivot_element.column] == 0)
            while (pivot_element.row < m-1 && a[pivot_element.row][pivot_element.column] == 0)
                pivot_element.row++;
        return pivot_element;
    }

    static void swapLines(double a[][], double b[], boolean used_raws[],
            Position pivot_element) 
    {
        int size = a.length;

        for (int column = 0; column < size; ++column) {
            double tmpa = a[pivot_element.column][column];
            a[pivot_element.column][column] = a[pivot_element.row][column];
            a[pivot_element.row][column] = tmpa;
        }

        double tmpb = b[pivot_element.column];
        b[pivot_element.column] = b[pivot_element.row];
        b[pivot_element.row] = tmpb;

        boolean tmpu = used_raws[pivot_element.column];
        used_raws[pivot_element.column] = used_raws[pivot_element.row];
        used_raws[pivot_element.row] = tmpu;

        pivot_element.row = pivot_element.column;
    }

    static void processPivotElement(double a[][], double b[], Position pivot_element) 
    {
        int m = a.length;
        int n = a[0].length;
        
        // scale the pivot element to 1
        if (a[pivot_element.row][pivot_element.column] != 1)
            scale(pivot_element, a, b);

        // Bring each element upside the main diagonal to 0 
        for (int u = pivot_element.row - 1; u >= 0; u--)
        {
            double k = a[u][pivot_element.column];
            for (int j = 0; j < n; j++) 
            {
               a[u][j] = a[u][j] - k*a[pivot_element.row][j];          
            }
            b[u] = b[u] - k*b[pivot_element.row];                
        }
        
        // Bring each element downside the main diagonal to 0 
        for (int l = pivot_element.row + 1; l < m; l++)
        {
            double k = a[l][pivot_element.column];
            for (int j = 0; j < n; j++) 
            {
               a[l][j] = a[l][j] - k*a[pivot_element.row][j];          
            }
            b[l] = b[l] - k*b[pivot_element.row];                
        }
    }

    private static void scale(Position pivot_element, double[][] a, double[] b)
    {
        int n = a[0].length;
        double pivot = a[pivot_element.row][pivot_element.column];
        b[pivot_element.row] = b[pivot_element.row] / pivot;
        for (int j = n-1; j >= pivot_element.column; j--)
            a[pivot_element.row][j] = a[pivot_element.row][j] / pivot;
    }

    static void markPivotElementUsed(Position pivot_element, boolean used_raws[], boolean used_columns[]) {
        used_raws[pivot_element.row] = true;
        used_columns[pivot_element.column] = true;
    }
    
    private static boolean checkSolution(double A[][], double b[], double solution[])
    {
        for (int i = 0; i < solution.length; i++)
            if (solution[i] + 1e-3 < 0) return false;
        for (int i = 0; i < A.length; i++) 
        {
            double sum = 0;
            for (int j = 0; j < A[0].length; j++)
                sum += A[i][j]*solution[j];
            if (sum > b[i] + 1e-3) return false;                
        }
        return true;
    }

    private static void selectOptimalSolution(List<double[]> solutions, double[] c, double[] x) {
        double max = Double.NEGATIVE_INFINITY;
        for (double[] solution : solutions) 
        {
            double sum = 0.0;
            for (int i = 0; i < solution.length; i++)
                sum += solution[i] * c[i];
            //System.out.println("f(" + solution[0] + ", " + solution[1] + ") = " + sum);           
            if (sum > max)
            {
                int from = 0;
                int length = solution.length;
                System.arraycopy(solution, from, x, from, length);
                max = sum;
            }
        }
    }

    private static Equation buildSystemOfLinearEquations(List E, double[][] A, double[] b)
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
            }else if (equationIndex < n + m) 
            {
                // create a new array
                double[] temp = new double[m];
                // Set the m-th element to 1
                temp[equationIndex - n] = 1;
                System.arraycopy(temp, 0, A_[k++], 0, m);
            }else
            {
                Arrays.fill(A_[m-1], 1);
                b_[m-1] = BIG_NUMBER;                
            }
        }
        // Solve the system of linear equations
        Equation equation = new Equation(A_, b_);
        return equation;
    }
    
    private static void subset(int[] a, int left, int index, List<Integer> list, List<List<Integer>> subsets)
    {
        if (left == 0) {
            subsets.add(new ArrayList(list));
            return;
        }
        for (int i = index; i < a.length; i++)
        {
            list.add(a[i]);
            subset(a, left-1, index+1, list, subsets);
            list.remove(list.size()-1);
        }
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
