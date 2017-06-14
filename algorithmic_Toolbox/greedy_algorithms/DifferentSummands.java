import java.util.*;

public class DifferentSummands {

    /**
     *
     * @param m a natural number
     * @preconstraint 1 <= m <= 10^9
     * @return the maximum set of summands {a_1, a_2, ..., a_k} such that 
     *        m = a_1 + a_2 + ... + a_k
     */
    private static List<Integer> optimalSummands(int m)
    {
        List<Integer> summands = new ArrayList();
        int n = 1;
        while (true)
        {
            if (m <= 2 * n) {
                summands.add(m);
                break;
            } else {
                summands.add(n);
                m = m - n;
                n++;
            }
        }
        return summands;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> summands = optimalSummands(n);
        System.out.println(summands.size());
        for (Integer summand : summands) {
            System.out.print(summand + " ");
        }
    }
}
