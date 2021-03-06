import java.util.*;

public class PrimitiveCalculator {
    /**
     * @param n a natural number
     * @precondition 1 <= n <= 10^6
     * @return Let x be a natural number, given a primitive calculator that can 
     *         perform the following three operations:
     *         * multiply x by 3
     *         * multiply x by 2
     *         * add 1 to x
     *         the following subroutine computes the minimum number of operations
     *         needed to obtain n starting from 1.
     */
    private static List<Integer> optimal_sequence(int n) {
        List<Integer> sequence = new ArrayList<Integer>();
        while (n >= 1) {
            sequence.add(n);
            if (n % 3 == 0) {
                n /= 3;
            } else if (n % 2 == 0) {
                n /= 2;
            } else {
                n -= 1;
            }
        }
        Collections.reverse(sequence);
        return sequence;
    }
    
    /**
     * @param n a natural number
     * @precondition 1 <= n <= 10^6
     * @return Let x be a natural number, given a primitive calculator that can 
     *         perform the following three operations:
     *         * multiply x by 3
     *         * multiply x by 2
     *         * add 1 to x
     *         the following subroutine computes the minimum number of operations
     *         needed to obtain n starting from 1.
     */
    private static int[] operationsMinNumber(int n)
    {
        int[] operationsMinNumber = new int[n+1];
        
        for (int i = 2; i <= n; i++)
        {
            operationsMinNumber[i] = operationsMinNumber[i-1] + 1;
            for (int d = 2; d <= 3; d++)
            {
                if ((i % d == 0) && (operationsMinNumber[i/d] + 1 < operationsMinNumber[i]))
                {
                    operationsMinNumber[i] = operationsMinNumber[i/d] + 1;                    
                }
            }            
        }
        return operationsMinNumber;        
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> sequence = optimal_sequence(n);
        System.out.println(sequence.size() - 1);
        for (Integer x : sequence) {
            System.out.print(x + " ");
        }
    }
}

