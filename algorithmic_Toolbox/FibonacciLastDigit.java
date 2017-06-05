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
    
    private static int getFibonacciLastDigit(int n)
    {
        int k = n % 60;
        int lastDigit = lastDigitOfFib(k);
        return lastDigit;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int c = getFibonacciLastDigit(n);
        System.out.println(c);
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
}

