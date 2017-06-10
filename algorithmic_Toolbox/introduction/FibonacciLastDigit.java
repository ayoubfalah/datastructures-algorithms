import java.util.*;

public class FibonacciLastDigit {
    private static int getFibonacciLastDigitNaive(int n) {
        if (n <= 1)
            return n;

        int previous = 0;
        int current  = 1;

        for (int i = 0; i < n - 1; ++i) {
            int tmp_previous = previous;
            previous = current;
            current = tmp_previous + current;
        }

        return current % 10;
    }
    
    /**
     * 
     * @param n a natural number
     * @preconstraint 0 <= n <= 10^7
     * @return the last digit of the n-th Fibonacci number
     */
    private static int getFibonacciLastDigit(int n)
    {
        // The sequence (f(n) mod 10) is periodic and the period length is 60
        int periodLength = 60;
        int k = n % periodLength;
        int lastDigit = lastDigitOfFib(k);
        return lastDigit;
    }
    
    private static int lastDigitOfFib(int n) 
    {
        if (n <= 1) return n;
        
        int current = 1;
        int last = 1;
        int temp = 0;
        
        for (int i = 2; i <= n; i++) 
        {
            current = (last + temp) % 10;
            temp = last;
            last = current;
        }
        return current;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int c = getFibonacciLastDigit(n);
        System.out.println(c);
    }
}

