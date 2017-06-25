package algorithmic_Toolbox.divide_and_conquer;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author ayoubfalah
 */
public class StressTester 
{
    public static void main(String[] args) 
    {
        label: while(true)
            {int n = Math.abs(new Random().nextInt() % 10) + 1;
            System.out.println(n);
            int[] A = generateA(n);

            System.out.print("A := ");
            System.out.println(Arrays.toString(A));

            int res1 = MajorityElement.hasMajorityElement(A);
            int res2 = MajorityElement.hasMajorityElementFaster(A);
            if (res1 != res2)
            {
                System.out.println("Wrong answer: " + res1 + " " + res2);
                break label;                   
            }
            System.out.println("OK");
            }
        
    }

    private static int[] generateA(int n) 
    {
        int[] A = new int[n];
        Random random = new Random();
        for (int i = 0; i <= n-1; i++)
            A[i] = Math.abs(random.nextInt()) % 10;
        return A;
    }
}
