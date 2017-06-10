import java.util.*;

public class FibonacciPartialSum {
    private static long getFibonacciPartialSumNaive(long from, long to) {
        if (to <= 1)
            return to;

        long previous = 0;
        long current  = 1;

        for (long i = 0; i < from - 1; ++i) {
            long tmp_previous = previous;
            previous = current;
            current = tmp_previous + current;
        }

        long sum = current;

        for (long i = 0; i < to - from; ++i) {
            long tmp_previous = previous;
            previous = current;
            current = tmp_previous + current;
            sum += current;
        }

        return sum % 10;
    }
    
    private static long fibPartialSum(long m, long n)
    {
        long lastDigitOfPartialSum = fibSum(n) - fibSum(m-1);
        if (lastDigitOfPartialSum < 0) return lastDigitOfPartialSum + 10;
        return lastDigitOfPartialSum;        
    }
    
    private static long fibSum(long n)
    {
        // f(0) + f(1) + ... + f(n) = f(n+2) - 1
        n += 2;
        // For any natural number n: The sequecne l(n) = (f(n) mod 10) is periodic
        // and the period length is 60
        int periodLength = 60;
        int k = (int)(n % periodLength);
        
        int f = lastDigitOfFib(k);
        
        f = f - 1;
        if (f < 0) return f + 10;
        return f;
    }
    
    private static int lastDigitOfFib(long n) 
    {
        if (n <= 1) return (int)n;
        
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
        long from = scanner.nextLong();
        long to = scanner.nextLong();
        System.out.println(fibPartialSum(from, to));
    }
}

