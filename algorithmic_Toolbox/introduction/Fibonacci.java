import java.util.Arrays;
import java.util.Scanner;

public class Fibonacci {
  private static long fib1(int n) {
    if (n <= 1)
      return n;

    return fib1(n - 1) + fib1(n - 2);
  }
  
  private static long fib2(int n)
  {
    long mem[] = new long[n+1];
    Arrays.fill(mem, -1);
    return fib2(n, mem);      
  }
  
  private static long fib2(int n, long[] mem)
  {
    if (n <= 1) return n;
    if (mem[n] != -1) return mem[n];
    return fib2(n - 1, mem) + fib2(n - 2, mem);      
  }
  
  /**
   * 
   * @param n a natural number
   * @preconstraint 0 <= n <= 45
   * @return find the n-th Fibonacci number
   */
  private static long fib3(int n)
  {
    if (n <= 1) return n;
    long current = 1;
    long last = 1;
    long penultimate = 0;
    for (int i = 2; i <= n; i++) 
    {
        current = last + penultimate;
        penultimate = last;
        last = current;
    }
    return current;      
  }

  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();

    System.out.println(fib3(n));
  }
}
