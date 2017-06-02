package advanced_algorithms_and_complexity.linear_programming;

import java.io.IOException;
import java.util.Scanner;

class Equation {
    Equation(double a[][], double b[]) {
        this.a = a;
        this.b = b;
    }

    double a[][];
    double b[];
}

class Position {
    Position(int column, int row) {
        this.column = column;
        this.row = row;
    }

    int column;
    int row;
}

class EnergyValues 
{
    public static final boolean SOLVABLE = false;
    
    static Equation ReadEquation() throws IOException {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();

        double a[][] = new double[size][size];
        double b[] = new double[size];
        for (int raw = 0; raw < size; ++raw) {
            for (int column = 0; column < size; ++column)
                a[raw][column] = scanner.nextInt();
            b[raw] = scanner.nextInt();
        }
        return new Equation(a, b);
    }

    /**
     * @param a an nx(n+1) matrix
     * @param used_raws for any i in {0, 1, ..., (n-1)}: used_raws[i] is true,
     *                  iff used_raws[i] is used as pivot.
     * @param used_columns for any j in {0, 1, ..., (m-1)}: used_column[j] is 
     *                     true, iff used_column[j] is used as pivot
     * @return a pivot element
     */
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

    static void PrintColumn(double column[]) {
        int size = column.length;
        for (int raw = 0; raw < size; ++raw)
            System.out.printf("%.6f\n", column[raw]);
    }

    public static void main(String[] args) throws IOException {
        Equation ls = ReadEquation();
        //double[] solution = solveEquation(ls);
//        PrintColumn(solution);
//        int status = solveEquation(ls);
//        switch (status) {
//            case 1:
//                System.out.println("Infinity");
//                break;
//            case -1:
//                System.out.println("No solution");
//                break;
//            default:
//                PrintColumn(ls.b);
//                break;
//        }
;
    }
}
