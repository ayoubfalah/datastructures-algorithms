import java.util.*;

public class DotProduct 
{
    /**
     * 
     * @param a A sequence a1, a2, ..., an, where ai is the profit per click of
     *          the i-th ad
     * @param b A sequence b1, b2, ..., bn, wehre bi is the average number of 
     *          clicks per day of the i-th slot.
     * @return The maximum dot product of a and b
     */
    private static long maxDotProduct(final int[] a, final int[] b) 
    {        
        Arrays.sort(a);        
        Arrays.sort(b);
        
        long sum = 0;
        for (int i = 0; i < a.length; i++) { sum += ((long)a[i] * b[i]); }
        return sum;
    }

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            b[i] = scanner.nextInt();
        }
        System.out.println(maxDotProduct(a, b));
    }
}

