package minimum_spanning_trees;

import java.text.DecimalFormat;
import java.util.PriorityQueue;
import java.util.Scanner;

public class ConnectingPoints 
{
    private static class DisjointSets 
    {
        int[] pi;
        int[] rank;

        public DisjointSets (int n) {
            pi = new int[n];
            rank = new int[n];
        }

        public void makeSet(int x) {
            pi[x] = x;
            rank[x] = 1;
        }

        public int find(int x)
        {
            while (pi[x] != x)               
                x = pi[x];
            return x;
        }

        public void union(int x, int y) 
        {
            int r_x = find(x);
            int r_y = find(y);
            if (r_x == r_y) return;
            if (rank[r_x] > rank[r_y])
                pi[r_y] = r_x;
            else
            {
                pi[r_x] = r_y;
                if (rank[r_x] == rank[r_y])
                    rank[r_y] = rank[r_y] + 1;
            }
        }
    }

    private static class Segment implements Comparable<Segment>
    {
        int u;
        int v;
        double w;
        
        public Segment (int u, int v, double w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(Segment that) 
        {
            return Double.compare(this.w, that.w);
        }        
    }
    
    /**
     * 
     * @param x for any x_i in x: x_i is the x-coordinate of a point on the plane
     *          P_i
     * @param y for any y_i in x: y_i is the y-coordinate of a point on the plane
     *          P_i
     * @precondition 1 <= n <= 200
     *               for any x_i in x, for any y_i in y: |x_i| <= 10^3 and 
     *               |y_i| <= 10^3
     *               for any i, j in {1, 2, ..., n}: i != j => P_i != P_j
     *               for any i, j, k in {1, 2, ..., n}: i != j and i != k and
     *               i != k => P_i, P_j and P_k are collinear
     * @return 
     */
    private static double minimumDistance(int[] x, int[] y) {
        double result = 0.;
        DisjointSets ds = new DisjointSets(x.length);
        PriorityQueue<Segment> q = new PriorityQueue<>();
        for (int i = 0; i < x.length; i++) ds.makeSet(i);
        for (int u = 0; u < x.length; u++) {
            for (int v = u + 1; v < x.length; v++) {
                q.add(new Segment(u, v, distance(u, v, x, y)));
            }
        }
        while (!q.isEmpty()) {
            Segment minWeightedSegment = q.remove();
            if (ds.find(minWeightedSegment.u) != ds.find(minWeightedSegment.v))
            {
                ds.union(minWeightedSegment.u, minWeightedSegment.v);
                result += minWeightedSegment.w;
            }
        }
        return result;
    }
    
    private static double distance(int i, int j, int[] x, int[] y) 
    {
        double result = Math.sqrt((x[j] - x[i])*(x[j] - x[i]) + 
                                   (y[j] - y[i])*(y[j] - y[i]));
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.0000000");
        System.out.println(decimalFormat.format(minimumDistance(x, y)));
    }
}